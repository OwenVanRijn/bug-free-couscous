package io.swagger.services;

import io.swagger.dto.BankaccountDTO;
import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.dto.TransactionDTO;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.NotFoundException;
import io.swagger.exceptions.RestException;
import io.swagger.exceptions.UnauthorisedException;
import io.swagger.model.*;
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
import java.util.ArrayList;
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

    @Autowired TransactionService transactionService;

    @Autowired
    private IbanHelper ibanHelper;

    public BankaccountService(){}

    public Optional<BankAccount> getBankaccountByIBANSafe(String IBAN) { return bankAccountRepository.findBankaccountByIBAN(IBAN); }

    public void saveBankAccount(BankAccount b){
        bankAccountRepository.save(b);}

    public void deleteByIBAN(String IBAN) throws RestException
    {
        if (!getBankaccountByIBANSafe(IBAN).isPresent()) { throw new NotFoundException("IBAN not found!"); }
        bankAccountRepository.deleteByIBAN(IBAN);
    }

    public List<BankaccountDTO> getBankaccountsCustomer() throws RestException
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        List<BankAccount> bankAccounts = user.getBankAccounts();
        List<BankaccountDTO> BDTOS = new ArrayList<>();
        for (BankAccount account: bankAccounts) {
            BankaccountDTO BDTO = new BankaccountDTO(account);
            BDTOS.add(BDTO);
        }
        if (BDTOS.isEmpty()){
            throw new NotFoundException("No Bankaccounts found on your account");
        }
        return BDTOS;
    }
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
        limit.setType(LimitType.BANKACCOUNT_MIN);
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
    public BankaccountDTO editAccount(CreateBankaccountDTO editBankaccount, String IBAN) throws RestException
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BankAccount bankAccount = getBankaccountByIBANSafe(IBAN).get();
        if (auth.getAuthorities().contains(Role.ROLE_EMPLOYEE)) {
            bankAccount = editAccountEmployee(bankAccount, editBankaccount);
            BankaccountDTO BDTO = new BankaccountDTO(bankAccount);
            return BDTO;
        } else {
            boolean success = editAccountCustomer(bankAccount, editBankaccount, auth);
            if (success) {
                BankaccountDTO BDTO = new BankaccountDTO(bankAccount);
                return BDTO;
            }
        }
        throw new UnauthorisedException("You are only allowed to edit your own account!");
    }
    public BankAccount editAccountEmployee(BankAccount bankAccount, CreateBankaccountDTO update)
    {
        bankAccount.name(update.getName()).accountType(update.getAccountType());
        saveBankAccount(bankAccount);
        return bankAccount;
    }
    public boolean editAccountCustomer(BankAccount bankAccount, CreateBankaccountDTO editBankaccount, Authentication auth)
    {
        boolean success = false;
        User user = userService.getUserByUsername(auth.getName());
        List<BankAccount> userBankaccounts = user.getBankAccounts();
        for (BankAccount account: userBankaccounts) {
            if (account.getIBAN().equals(bankAccount.getIBAN()))
            {
                bankAccount.name(editBankaccount.getName()).accountType(editBankaccount.getAccountType());
                saveBankAccount(bankAccount);
                success = true;
            }
        }
        return success;
    }
    public TransactionDTO DepositOrWithdraw(DepositOrWithdraw body) throws RestException
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = userService.getUserByUsername(auth.getName());
        Transaction t = new Transaction();
        t.ibANFrom("NL01INHO0000000001").ibANTo(body.getIBAN()).amount(body.getAmount().longValue()).performedBy(u);
        if (!getBankaccountByIBANSafe(body.getIBAN()).isPresent()) {
            throw new BadRequestException("Invalid Iban!");
        }
        if (isAccountTypeSavings(body.getIBAN()))
        {
            throw new BadRequestException("You cannot Deposit or Withdraw to a savings account!");
        }
        if (body.getAmount() < 0)
        {
            throw new BadRequestException("Amount cannot go under 0!");
        }
        if (body.getType() == DepositOrWithdraw.TypeEnum.DEPOSIT){
            t.type(Transaction.TypeEnum.DEPOSIT);
        } else if (body.getType() == DepositOrWithdraw.TypeEnum.WITHDRAW) {
            t.type(Transaction.TypeEnum.WITHDRAW);
        }
        TransactionDTO transactionDTO= new TransactionDTO(t);
        transactionService.DepositOrWithdraw(t);
        return transactionDTO;
    }
}

