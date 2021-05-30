package io.swagger.services;

import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;
import io.swagger.model.DepositOrWithdraw;
import io.swagger.model.Limit;
import io.swagger.repositories.BankAccountRepository;
import io.swagger.repositories.LimitRepository;
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
            Limit limit = new Limit();
            limit.name("Absolute Limit").current(00.00).limit(00.00);
            limitRepository.save(limit);
            BankAccount bankAccount = new BankAccount();
            String Iban = ibanHelper.generateUnusedIban();
            bankAccount.name(bankaccountDTO.getName()) // add owner ID
                    .accountType(bankaccountDTO.getAccountType())
                    .amount(0.00)
                    .IBAN(Iban)
                    .addLimitItem(limit);
            return bankAccount;
        }
//    public BankAccount DepositOrWithdraw(DepositOrWithdraw body){
//        BankAccount bankAccount = new BankAccount();
//        if (body.getType() == DepositOrWithdraw.TypeEnum.DEPOSIT) {
//            bankAccount = getBankaccountByIBANSafe(body.getIBAN()).get();
//            Limit limit = bankAccount.getLimit().get(0);
//            bankAccount.amount(bankAccount.getAmount() + body.getAmount());
//            limit.current(bankAccount.getAmountDecimal());
//            saveBankAccount(bankAccount);
//            return bankAccount;
//        } else if (body.getType() == DepositOrWithdraw.TypeEnum.WITHDRAW) {
//            bankAccount = getBankaccountByIBANSafe(body.getIBAN()).get();
//            Limit limit = bankAccount.getLimit().get(0);
//            if (bankAccount.getAmount() - body.getAmount() >= limit.getMax()) {
//                bankAccount.amount(bankAccount.getAmount() - body.getAmount());
//                limit.current(bankAccount.getAmountDecimal());
//                saveBankAccount(bankAccount);
//                return bankAccount;
//            }
//        }
//        return bankAccount = null;
//    }
}

