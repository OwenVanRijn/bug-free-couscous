package io.swagger.configuration;

import io.swagger.model.*;
import io.swagger.repositories.AddressRepository;
import io.swagger.repositories.BankAccountRepository;
import io.swagger.repositories.LimitRepository;
import io.swagger.repositories.TransactionRepository;
import io.swagger.services.IbanHelper;
import io.swagger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    LimitRepository limitRepository;

    @Autowired
    IbanHelper ibanHelper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address address = initAddress();
        Limit l = initBankaccountLimit();
        BankAccount current = initBankAccountCustomer(l, "current");
        BankAccount savings = initBankAccountCustomer(l, "savings");


        User customer = initCustomerUser(address, current, savings);
        User employee = initEmployeeUser(address, initBankAccountCustomer(null, "current"));
        User owner = initOwnerUser(address);
        initMoreUsers(address);

        initBankAccount(l, owner);
        initTransactions(customer);
        setupTransactionTestEnviroment();
    }

    private Address initAddress() {
        Address address = new Address();
        address.city("Amsterdam").country("Netherlands")
                .houseNumber(24).postalcode("1234FG").street("Long Street");

        return addressRepository.save(address);
    }

    private User initCustomerUser(Address address, BankAccount b, BankAccount savings) {
        User customer = new User();
        customer.firstName("James").lastName("Dean").phoneNumber("0612345678")
                .address(address).email("jamesdean@mail.com").addBankAccountsItem(b);
        customer.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        customer.setUsername("customer");
        customer.setPassword(passwordEncoder.encode("welkom"));

        userService.addUser(customer);
        b.setOwner(customer);
        savings.setOwner(customer);
        bankAccountRepository.save(b);
        bankAccountRepository.save(savings);
        return customer;
    }

    private User initEmployeeUser(Address address, BankAccount b) {
        User employee = new User();
        employee.firstName("Aubrey").lastName("Graham").phoneNumber("0612345678")
                .address(address).email("aubreygraham@mail.com");
        employee.setRoles(Arrays.asList(Role.ROLE_CUSTOMER, Role.ROLE_EMPLOYEE));

        employee.setUsername("employee");
        employee.setPassword(passwordEncoder.encode("welkom"));

        employee.addBankAccountsItem(b);

        userService.addUser(employee);
        bankAccountRepository.save(b);
        return employee;
    }

    private User initOwnerUser(Address address) {
        User employee = new User();
        employee.firstName("Aubrey").lastName("Graham").phoneNumber("0612345678")
                .address(address).email("aubreygraham@mail.com");
        employee.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        employee.setUsername("employee");
        employee.setPassword(passwordEncoder.encode("welkom"));

        userService.addUser(employee);
        return employee;
    }

    private void initMoreUsers(Address address) {
        for (int i = 0; i < 46; i++){
            User u = new User();
            u.firstName("Test").lastName("Person").phoneNumber("0612345678")
                    .address(address).email("test@mail.com").role(Collections.singletonList(Role.ROLE_CUSTOMER))
                    .username("tester" + i).password(passwordEncoder.encode("welkom"));
            userService.addUser(u);
        }
    }
    private Limit initBankaccountLimit(){
        Limit limit = new Limit();
        limit.limit(00.00);
        return limitRepository.save(limit);
    }
    private BankAccount initBankAccount(Limit limit, User owner){
        BankAccount bankAccount = new BankAccount();
        bankAccount.accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN("NL01INHO0000000001")
                .amount(1500.00).name("The Bank account").balanceMin(limit);

        owner.addBankAccountsItem(bankAccount);
        userService.addUser(owner);

        return bankAccountRepository.save(bankAccount);

    }
    private BankAccount initBankAccountCustomer(Limit limit, String type){
        BankAccount bankAccount = new BankAccount();
        bankAccount.balanceMin(limit).amount(300.00);
        if (type.equals("current")){
            bankAccount.accountType(BankAccount.AccountTypeEnum.CURRENT).name("James Daily Account").IBAN(IbanHelper.generateIban());
        } else if (type.equals("savings")){
            bankAccount.accountType(BankAccount.AccountTypeEnum.SAVINGS).name("James savings").IBAN("NL09INHO0722376752");
        };

        return bankAccountRepository.save(bankAccount);
    }
    private void initTransactions(User customer){
        for (int i = 0; i < 100; i++){
            Transaction t = new Transaction();
            t.type(Transaction.TypeEnum.TRANSACTION)
            .amount(ThreadLocalRandom.current().nextLong(100, 1000))
            .ibANFrom("NL01INHO0000000001")
            .ibANTo(IbanHelper.generateIban())
            .performedBy(customer);

            transactionRepository.save(t);
        }
    }

    private void setupTransactionTestEnviroment(){
        Address a = initAddress();
        User user1 = new User().firstName("User1").lastName("User").phoneNumber("0612345678").address(a).email("user1@user.nl").username("user1").password(passwordEncoder.encode("user1")).role(Collections.singletonList(Role.ROLE_CUSTOMER));
        User user2 = new User().firstName("User2").lastName("User").phoneNumber("0612345678").address(a).email("user2@user.nl").username("user2").password(passwordEncoder.encode("user2")).role(Collections.singletonList(Role.ROLE_CUSTOMER));

        BankAccount accountUser1 = new BankAccount().accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN(ibanHelper.generateUnusedIban()).amount(150.00).name("User1 Bank");
        BankAccount accountUser2 = new BankAccount().accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN(ibanHelper.generateUnusedIban()).amount(50.00).name("User2 Bank");
        BankAccount accountUser1_2nd = new BankAccount().accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN(ibanHelper.generateUnusedIban()).amount(50.00).name("User1 2nd Bank");
        BankAccount accountUser2_2nd = new BankAccount().accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN(ibanHelper.generateUnusedIban()).amount(100.00).name("User2 2nd Bank");
        BankAccount accountUser1Saving = new BankAccount().accountType(BankAccount.AccountTypeEnum.SAVINGS).IBAN(ibanHelper.generateUnusedIban()).amount(50.00).name("User1 Saving");
        BankAccount accountUser2Saving = new BankAccount().accountType(BankAccount.AccountTypeEnum.SAVINGS).IBAN(ibanHelper.generateUnusedIban()).amount(50.00).name("User2 Saving");
        user1.addBankAccountsItem(accountUser1);
        user1.addBankAccountsItem(accountUser1_2nd);
        user1.addBankAccountsItem(accountUser1Saving);
        user2.addBankAccountsItem(accountUser2);
        user2.addBankAccountsItem(accountUser2_2nd);
        user2.addBankAccountsItem(accountUser2Saving);

        addressRepository.save(a);

        userService.addUser(user1);
        userService.addUser(user2);

        bankAccountRepository.save(accountUser1);
        bankAccountRepository.save(accountUser2);
        bankAccountRepository.save(accountUser1Saving);
        bankAccountRepository.save(accountUser2_2nd);
        bankAccountRepository.save(accountUser2Saving);
        bankAccountRepository.save(accountUser1_2nd);

        // Order user1: bankAccount 1, bankAccountSaving, bankAccount 2
        // Order user2: bankAccount 1, bankAccount 2, bankAccountSaving

        Transaction t = new Transaction().type(Transaction.TypeEnum.TRANSACTION).amount(100L).ibANFrom(accountUser1.getIBAN()).ibANTo(accountUser2.getIBAN()).performedBy(user1);
        transactionRepository.save(t);
    }
}
