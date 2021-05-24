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
    @Query("SELECT t FROM Transaction t WHERE t.ibANFrom in ?1 OR t.ibANTo in ?1 OR t.id in ?2")
    Page<Transaction> getTransactions(List<String> ibans, List<Long> ids, Pageable pageable);

    @Query("SELECT t FROM Transaction t")
    Page<Transaction> getTransaction(Pageable pageable);

    // TODO: add queries for customers to only query their stuff
}
