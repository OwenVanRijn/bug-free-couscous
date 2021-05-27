package io.swagger.services;

import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;
import io.swagger.model.Limit;
import io.swagger.repositories.BankAccountRepository;
import io.swagger.repositories.LimitRepository;
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

    @Autowired
    private LimitRepository limitRepository;

    public BankaccountService(){}

    public List<BankAccount> getAllBankaccounts() { return (List<BankAccount>) bankAccountRepository.findAll(); }


    public BankAccount getBankaccountByIBAN(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN).get(); }


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
        Limit limit = new Limit();
        limit.name("Absolute Limit").current(00.00).limit(00.00);
        limitRepository.save(limit);
        BankAccount bankAccount = new BankAccount();
        IbanHelper ibanHelper = new IbanHelper();
        String Iban = "NL01INHO0000000002"; // Change to iban generator
        bankAccount.name(bankaccountDTO.getName()) // add owner ID
                .accountType(bankaccountDTO.getAccountType())
                .amount(0.00)
                .IBAN(Iban)
                .addLimitItem(limit);
        return bankAccount;
    }
}

