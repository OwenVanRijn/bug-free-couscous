package io.swagger.services;

import io.swagger.model.BankAccount;
import io.swagger.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankaccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public BankaccountService(){}

    public List<BankAccount> getAllBankaccounts() { return (List<BankAccount>) bankAccountRepository.findAll(); }


    public BankAccount getBankaccountByIBAN(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN).get(); }

    public Optional<BankAccount> getBankaccountByIBANSafe(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN); }

    public void saveBankAccount(BankAccount b){
        bankAccountRepository.save(b);
    }
}
