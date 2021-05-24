package io.swagger.services;

import io.swagger.dto.TransactionDTO;
import io.swagger.dto.TransactionPostDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.exceptions.UnauthorisedException;
import io.swagger.model.BankAccount;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private void processTransaction(Transaction t) throws Exception {
        BankAccount from = null;
        BankAccount to = null;

        Optional<BankAccount> toOp = bankaccountService.getBankaccountByIBANSafe(t.getIbANTo());
        if (toOp.isPresent())
            to = toOp.get();

        if (t.getIbANFrom() != null){
            Optional<BankAccount> fromOp = bankaccountService.getBankaccountByIBANSafe(t.getIbANFrom());
            if (fromOp.isPresent())
                from = fromOp.get();
        }

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
    }

    public void createTransaction(TransactionPostDTO tpd, User performingUser) throws Exception {
        // TODO: add IBAN validation
        User.RoleEnum role = User.RoleEnum.EMPLOYEE; // TODO: change to be based on user

        if (role == User.RoleEnum.CUSTOMER && performingUser.getBankAccounts()
                .stream()
                .noneMatch(x -> x.getIBAN().equals(tpd.getIbANFrom()) || x.getIBAN().equals(tpd.getIbANTo()))) {
            throw new UnauthorisedException();
        }

        Transaction t = new Transaction();
        Double amount = (tpd.getAmount() * 100);
        t.ibANTo(tpd.getIbANTo())
                .ibANFrom(tpd.getIbANFrom())
                .amount(amount.longValue())
                .type(Transaction.TypeEnum.TRANSACTION)
                .performedBy(performingUser);

        processTransaction(t);

        transactionRepository.save(t);
    }
}
