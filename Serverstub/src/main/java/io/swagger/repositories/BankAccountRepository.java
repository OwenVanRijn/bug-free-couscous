package io.swagger.repositories;

import io.swagger.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    Optional<BankAccount>findBankaccountByIBAN(String IBAN);

    Optional<BankAccount>deleteByIBAN(String IBAN);
}
