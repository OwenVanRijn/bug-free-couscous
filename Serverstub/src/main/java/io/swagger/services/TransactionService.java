package io.swagger.services;

import io.swagger.dto.TransactionPostDTO;
import io.swagger.dto.TransactionPutDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.NotFoundException;
import io.swagger.exceptions.RestException;
import io.swagger.exceptions.UnauthorisedException;
import io.swagger.model.BankAccount;
import io.swagger.model.Limit;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankaccountService bankaccountService;

    public TransactionsPageDTO getTransactions(List<Long> ids, List<String> ibans, Integer limit, Integer page, User performingUser) {
        page--; // Page should start at 1
        Pageable p = PageRequest.of(page, limit);
        Page<Transaction> t;
        if (performingUser == null){
            if (ids == null && ibans == null)
                t = transactionRepository.getTransactions(p);
            else
                t = transactionRepository.getTransactions(ibans, ids, p);
        }
        else {
            if (ids == null && ibans == null)
                t = transactionRepository.getTransactions(performingUser.getId(), p);
            else
                t = transactionRepository.getTransactions(ibans, ids, performingUser.getId(), p);
        }
        // TODO: add user filtering

        return new TransactionsPageDTO(
                t.getTotalElements(),
                t.getTotalPages(),
                t.get().map(x -> x.toTransactionDTO()).collect(Collectors.toList())
        );
    }

    private void verifyLimit(BankAccount b) throws RestException {
        Long totalCount = transactionRepository.countTransactionsByUser(b.getOwner().getId());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24);
        Long dayCount = transactionRepository.countTransactionsByUserBeforeDate(b.getOwner().getId(), cal.getTime());

        if (b.getOwner().getDailyLimit().getMax() <= dayCount)
            throw new BadRequestException("From account has reached the daily transaction limit");

        if (b.getOwner().getGlobalLimit().getMax() <= totalCount)
            throw new BadRequestException("From account has reached the transaction limit");
    }

    // TODO: add force option, only customers should have checked limits
    private void processTransaction(Transaction t) throws RestException {
        // TODO: should we add banks that are outside our presence? should an IBAN that we do not know be valid? Transfers from/to other banks?

        t.validate();

        BankAccount from = null;
        BankAccount to = null;

        Optional<BankAccount> fromOp = bankaccountService.getBankaccountByIBANSafe(t.getIbanFrom());
        Optional<BankAccount> toOp = bankaccountService.getBankaccountByIBANSafe(t.getIbanTo());

        if (fromOp.isPresent()) {
            from = fromOp.get();
            if (t.getType() == Transaction.TypeEnum.TRANSACTION){
                verifyLimit(from);
            }
        }

        else if (t.getType() == Transaction.TypeEnum.TRANSACTION){
            //System.out.println("[WARN] From transaction is outside our control!");
            throw new BadRequestException("IBAN from not found!");
        }

        if (toOp.isPresent()){
            to = toOp.get();
            if (t.getType() == Transaction.TypeEnum.TRANSACTION){
                verifyLimit(to);
            }
        }
        else {
            //System.out.println("[WARN] to transaction is outside our control!");
            throw new BadRequestException("IBAN to not found!");
        }

        if (t.getType() == Transaction.TypeEnum.TRANSACTION){
            int savingCount = ((from.getAccountType() == BankAccount.AccountTypeEnum.SAVINGS) ? 1 : 0) + ((to.getAccountType() == BankAccount.AccountTypeEnum.SAVINGS) ? 1 : 0);

            if (savingCount == 1){
                if (!from.getOwner().getId().equals(to.getOwner().getId()))
                    throw new UnauthorisedException("Trying to transfer to/from save account while not being the owner of it");
            }
            else if (savingCount == 2){
                throw new BadRequestException("You cannot transfer from saving to saving account");
            }

            from.removeAmount(t.getAmount());
            bankaccountService.saveBankAccount(from);
        }

        if (t.getType() == Transaction.TypeEnum.WITHDRAW)
            to.removeAmount(t.getAmount());
        else
            to.addAmount(t.getAmount());

        bankaccountService.saveBankAccount(to);

        System.out.printf("[%s] Executed transaction %s to %s with amount %.2f\n", t.getType().toString(), t.getIbanFrom(), t.getIbanTo(), t.getAmountAsDecimal());
    }

    public void createTransaction(TransactionPostDTO tpd, User performingUser) throws RestException {
        // TODO: add IBAN validation
        if (!(performingUser.getRole().contains(Role.ROLE_EMPLOYEE) && !tpd.getIbanFrom().equals("NL01INHO0000000001")) && performingUser.getBankAccounts()
                .stream()
                .noneMatch(x -> x.getIBAN().equals(tpd.getIbanFrom()))) {
            throw new UnauthorisedException("You do not own the from bankaccount");
        }

        Transaction t = tpd.toTransaction(performingUser);
        processTransaction(t);
        transactionRepository.save(t);
    }

    private void RollbackTransactionDiff (List<Transaction> atomic) throws RestException {
        atomic.forEach(x -> x.swapIban()); // Reverse all transactions
        for (Transaction t : atomic)
            processTransaction(t);
    }

    private Transaction applyTransactionDiff(TransactionPostDTO newValue, Transaction oldValue) throws RestException {
        Transaction finishedDiff = oldValue.copy();
        List<Transaction> atomic = new ArrayList<>();

        newValue.toTransaction(new User()); // to validate the dto

        if (!newValue.getIbanFrom().equals(oldValue.getIbanFrom())){
            Transaction t = new Transaction();
            t.type(Transaction.TypeEnum.TRANSACTION)
                    .ibANFrom(newValue.getIbanFrom())
                    .ibANTo(oldValue.getIbanFrom())
                    .amount(oldValue.getAmount())
                    .performedBy(new User());

            finishedDiff.ibANFrom(newValue.getIbanFrom());
            processTransaction(t);

            atomic.add(t);
        }

        if (!newValue.getIbanTo().equals(oldValue.getIbanTo())){
            Transaction t = new Transaction();
            t.type(Transaction.TypeEnum.TRANSACTION)
                    .ibANFrom(oldValue.getIbanTo())
                    .ibANTo(newValue.getIbanTo())
                    .amount(oldValue.getAmount())
                    .performedBy(new User());

            finishedDiff.ibANTo(newValue.getIbanTo());
            try {
                processTransaction(t);
            }
            catch (RestException e){
                RollbackTransactionDiff(atomic);
                throw e;
            }

            atomic.add(t);
        }

        if (!newValue.getAmountLong().equals(oldValue.getAmount())){
            Long diff = newValue.getAmountLong() - oldValue.getAmount();

            Transaction t = finishedDiff.copy();

            if (diff < 0) {
                t.swapIban();
                diff *= -1;
            }

            t.amount(diff);
            try {
                processTransaction(t);
            }
            catch (RestException e){
                RollbackTransactionDiff(atomic);
                throw e;
            }
            finishedDiff.amount(newValue.getAmountLong());
        }

        return finishedDiff;
    }

    public void editTransaction(TransactionPutDTO tpd, Long transactionId) throws RestException {
        Optional<Transaction> tOp = transactionRepository.findById(transactionId);
        if (!tOp.isPresent())
            throw new NotFoundException("Id not found");

        tpd.isEmpty();

        Transaction t = tOp.get();

        tpd.fillEmpty(t);

        transactionRepository.save(applyTransactionDiff(tpd.toPostDto(), t));
    }

    public void deleteTransaction(Long transactionId) throws RestException {
        Optional<Transaction> tOp = transactionRepository.findById(transactionId);
        if (!tOp.isPresent())
            throw new NotFoundException("Id not found");

        Transaction t = tOp.get();
        Transaction copyT = t.copy();
        copyT.swapIban();

        processTransaction(copyT); // Reverse the money that was sent in the transaction
        transactionRepository.delete(t);
    }
    public void DepositOrWithdraw(Transaction t) throws RestException {
        processTransaction(t);
    }
}
