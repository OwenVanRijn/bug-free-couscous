package io.swagger.services;

import io.swagger.model.BankAccount;
import io.swagger.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankaccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public BankaccountService(){}

    public List<BankAccount> getAllBankaccounts() { return (List<BankAccount>) bankAccountRepository.findAll(); }

    public List<BankAccount> getBankaccountByIBAN(String IBAN) { return (List<BankAccount>) bankAccountRepository.findById(IBAN).get(); }
}
