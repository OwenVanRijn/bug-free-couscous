package io.swagger.services;

import io.swagger.model.BankAccount;
import io.swagger.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BankaccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public BankaccountService(){}

    public List<BankAccount> getAllBankaccounts() { return (List<BankAccount>) bankAccountRepository.findAll(); }


    public BankAccount getBankaccountByIBAN(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN).get(); }


    public Optional<BankAccount> getBankaccountByIBANSafe(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN); }

    public void saveBankAccount(BankAccount b){
        bankAccountRepository.save(b);}

    public void deleteByIBAN(String IBAN) {bankAccountRepository.deleteByIBAN(IBAN);}
    }
