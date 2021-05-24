package io.swagger.services;

import io.swagger.dto.TransactionDTO;
import io.swagger.dto.TransactionPostDTO;
import io.swagger.dto.TransactionsPageDTO;
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
        Optional<BankAccount> from = null;
        Optional<BankAccount> to = bankaccountService.getBankaccountByIBANSafe(t.getIbANTo());

        if (t.getIbANFrom() != null){
            from = bankaccountService.getBankaccountByIBANSafe(t.getIbANFrom());
        }

        // TODO: should an IBAN that we do not know be valid? Transfers from/to other banks?

        if (from != null){
            if (from.isPresent()){
                BankAccount b = from.get();
                b.removeAmount(t.getAmount());
                bankaccountService.saveBankAccount(b);
            }
        }

        if (to.isPresent()){
            BankAccount b = to.get();
            b.addAmount(t.getAmount());
            bankaccountService.saveBankAccount(b);
        }



        // TODO: add deposit/withdraw

    }

    public void createTransaction(TransactionPostDTO tpd, User performingUser) throws Exception {
        User.RoleEnum role = User.RoleEnum.CUSTOMER; // TODO: change to be based on user

        if (!performingUser.getBankAccounts()
                .stream()
                .anyMatch(x -> x.getIBAN().equals(tpd.getIbANFrom()) || x.getIBAN().equals(tpd.getIbANTo()))) {
            throw new Exception("Unauthorized");
        }

        // TODO: handle transaction subtraction/addition later

        Transaction t = new Transaction();
        Double amount = (tpd.getAmount() * 100);
        t.ibANTo(tpd.getIbANTo())
                .ibANFrom(tpd.getIbANFrom())
                .amount(amount.longValue());

        processTransaction(t);

        transactionRepository.save(t);
    }
}
