package io.swagger.services;

import io.swagger.dto.TransactionPostDTO;
import io.swagger.dto.TransactionPutDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.NotFoundException;
import io.swagger.exceptions.RestException;
import io.swagger.exceptions.UnauthorisedException;
import io.swagger.model.BankAccount;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    // iban from is nullable
    private void processTransaction(Transaction t) throws RestException {
        // TODO: should we add banks that are outside our presence?

        BankAccount from = null;
        BankAccount to = null;

        Optional<BankAccount> toOp = bankaccountService.getBankaccountByIBANSafe(t.getIbanTo());
        if (toOp.isPresent())
            to = toOp.get();
        else if (t.getType() == Transaction.TypeEnum.TRANSACTION){
            System.out.println("[WARN] From transaction is outside our control!");
            //throw new BadRequestException("IBAN from not found!");
        }

        if (t.getIbanFrom() != null){
            Optional<BankAccount> fromOp = bankaccountService.getBankaccountByIBANSafe(t.getIbanFrom());
            if (fromOp.isPresent())
                from = fromOp.get();
        }
        else {
            System.out.println("[WARN] to transaction is outside our control!");
            //throw new BadRequestException("IBAN to not found!");
        }

        // TODO: add validation of saving/current accounts

        if (t.getAmount() < 0)
            throw new BadRequestException("Invalid amount");

        // TODO: should an IBAN that we do not know be valid? Transfers from/to other banks?

        if (from != null){
            if (t.getType() == Transaction.TypeEnum.TRANSACTION){
                from.removeAmount(t.getAmount());
                bankaccountService.saveBankAccount(from);
            }
        }

        // TODO: this is clearly invalid if to is not present

        if (to != null){
            if (t.getType() != Transaction.TypeEnum.WITHDRAW){
                to.addAmount(t.getAmount());
            }
            else {
                to.removeAmount(t.getAmount());
            }

            bankaccountService.saveBankAccount(to);
        }

        System.out.printf("[%s] Executed transaction %s to %s with amount %.2f\n", t.getType().toString(), t.getIbanFrom(), t.getIbanTo(), t.getAmountAsDecimal());
    }

    public void createTransaction(TransactionPostDTO tpd, User performingUser) throws RestException {
        // TODO: add IBAN validation
        Role role = Role.ROLE_EMPLOYEE; // TODO: change to be based on user

        if (role == Role.ROLE_CUSTOMER && performingUser.getBankAccounts()
                .stream()
                .noneMatch(x -> x.getIBAN().equals(tpd.getIbanFrom()) || x.getIBAN().equals(tpd.getIbanTo()))) {
            throw new UnauthorisedException();
        }

        Transaction t = new Transaction();
        Double amount = (tpd.getAmount() * 100);
        t.ibANTo(tpd.getIbanTo())
                .ibANFrom(tpd.getIbanFrom())
                .amount(amount.longValue())
                .type(Transaction.TypeEnum.TRANSACTION)
                .performedBy(performingUser);

        processTransaction(t);

        transactionRepository.save(t);
    }

    private Transaction applyTransactionDiff(TransactionPostDTO newValue, Transaction oldValue) throws RestException {
        Transaction finishedDiff = oldValue.copy();

        if (newValue.getAmountLong() <= 0)
            throw new BadRequestException("Value cannot be 0 or below 0");

        if (!newValue.getIbanFrom().equals(oldValue.getIbanFrom())){
            Transaction t = new Transaction();
            t.type(Transaction.TypeEnum.TRANSACTION)
                    .ibANFrom(newValue.getIbanFrom())
                    .ibANTo(oldValue.getIbanFrom())
                    .amount(oldValue.getAmount());

            finishedDiff.ibANFrom(newValue.getIbanFrom());
            processTransaction(t);
        }

        if (!newValue.getIbanTo().equals(oldValue.getIbanTo())){
            Transaction t = new Transaction();
            t.type(Transaction.TypeEnum.TRANSACTION)
                    .ibANFrom(oldValue.getIbanTo())
                    .ibANTo(newValue.getIbanTo())
                    .amount(oldValue.getAmount());

            finishedDiff.ibANTo(newValue.getIbanTo());
            processTransaction(t);
        }

        if (!newValue.getAmountLong().equals(oldValue.getAmount())){
            Long diff = newValue.getAmountLong() - oldValue.getAmount();

            Transaction t = finishedDiff.copy();

            if (diff < 0) {
                t.swapIban();
                diff *= -1;
            }

            t.amount(diff);
            processTransaction(t);
            finishedDiff.amount(newValue.getAmountLong());
        }

        return finishedDiff;
    }

    public void editTransaction(TransactionPutDTO tpd, Long transactionId) throws RestException {
        Optional<Transaction> tOp = transactionRepository.findById(transactionId);
        if (!tOp.isPresent())
            throw new NotFoundException("Id not found");

        Transaction t = tOp.get();

        // TODO: validate IBANS

        if (tpd.getAmount() == null){
            tpd.setAmount(t.getAmountAsDecimal());
        }

        if (tpd.getIbanFrom() == null){
            tpd.setIbanFrom(t.getIbanFrom());
        }

        if (tpd.getIbanTo() == null){
            tpd.setIbanTo(t.getIbanTo());
        }

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
}
