package io.swagger.repositories;

import io.swagger.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.ibanFrom in ?1 OR t.ibanTo in ?1 OR t.id in ?2 ORDER BY t.timestamp DESC")
    Page<Transaction> getTransactions(List<String> ibans, List<Long> ids, Pageable pageable);

    @Query("SELECT t FROM Transaction t ORDER BY t.timestamp DESC")
    Page<Transaction> getTransactions(Pageable pageable);

    @Query("SELECT t FROM User u, Transaction t JOIN u.bankAccounts b WHERE (t.ibanFrom in ?1 OR t.ibanTo in ?1 OR t.id in ?2) AND u.id = ?3 AND (b.IBAN = t.ibanFrom OR b.IBAN = t.ibanTo) ORDER BY t.timestamp DESC")
    Page<Transaction> getTransactions(List<String> ibans, List<Long> ids, Integer userId, Pageable pageable);

    @Query("SELECT t FROM User u, Transaction t JOIN u.bankAccounts b WHERE u.id = ?1 AND (b.IBAN = t.ibanFrom OR b.IBAN = t.ibanTo) ORDER BY t.timestamp DESC")
    Page<Transaction> getTransactions(Integer userId, Pageable pageable);
}
