package io.swagger.services;

import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;
import io.swagger.model.DepositOrWithdraw;
import io.swagger.model.Limit;
import io.swagger.model.User;
import io.swagger.repositories.BankAccountRepository;
import io.swagger.repositories.LimitRepository;
import io.swagger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BankaccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private LimitRepository limitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IbanHelper ibanHelper;

    public BankaccountService(){}

    public Optional<BankAccount> getBankaccountByIBANSafe(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN); }

    public void saveBankAccount(BankAccount b){
        bankAccountRepository.save(b);}

    public void deleteByIBAN(String IBAN) {bankAccountRepository.deleteByIBAN(IBAN);}

    public BankAccount createBankaccount(CreateBankaccountDTO bankaccountDTO){
        BankAccount bankAccount = convertToBankaccount(bankaccountDTO);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount convertToBankaccount(CreateBankaccountDTO bankaccountDTO)
    {
        User testUser = userRepository.getOne(2); // Only for test purposes, will be changed when working with accounts
        Limit limit = new Limit();
        limit.name("Absolute Limit").current(00.00).limit(00.00);
        limitRepository.save(limit);
        BankAccount bankAccount = new BankAccount();
        String Iban = ibanHelper.generateUnusedIban();
        bankAccount.name(bankaccountDTO.getName())
                .accountType(bankaccountDTO.getAccountType())
                .amount(0.00)
                .IBAN(Iban)
                .addLimitItem(limit)
                .setOwner(testUser);
        return bankAccount;
    }
}

