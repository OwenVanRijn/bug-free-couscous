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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired UserService userService;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = userService.getUserByUsername(auth.getName());
        Limit limit = new Limit();
        limit.setMax(0L);
        limitRepository.save(limit);
        BankAccount bankAccount = new BankAccount();
        String Iban = ibanHelper.generateUnusedIban();
        bankAccount.name(bankaccountDTO.getName())
                .accountType(bankaccountDTO.getAccountType())
                .amount(0.00)
                .IBAN(Iban)
                .balanceMin(limit)
                .setOwner(u);
        u.addBankAccountsItem(bankAccount);
        return bankAccount;
    }
    public boolean isAccountTypeSavings(String Iban)
    {
        BankAccount bankAccount = getBankaccountByIBANSafe(Iban).get();
        return bankAccount.getAccountType() != BankAccount.AccountTypeEnum.CURRENT;
    }
    public BankAccount editAccountEmployee(BankAccount bankAccount, CreateBankaccountDTO update)
    {
        bankAccount.name(update.getName()).accountType(update.getAccountType());
        saveBankAccount(bankAccount);
        return bankAccount;
    }
    public boolean editAccountCustomer(BankAccount bankAccount, CreateBankaccountDTO editBankaccount, Authentication auth)
    {
        boolean succes = false;
        User user = userService.getUserByUsername(auth.getName());
        List<BankAccount> userBankaccounts = user.getBankAccounts();
        for (BankAccount account: userBankaccounts) {
            if (account.getIBAN().equals(bankAccount.getIBAN()))
            {
                bankAccount.name(editBankaccount.getName()).accountType(editBankaccount.getAccountType());
                saveBankAccount(bankAccount);
                succes = true;
            }
        }
        return succes;
    }
}

