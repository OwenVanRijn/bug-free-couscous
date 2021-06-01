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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
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
    TransactionRepository transactionRepository;

    @Autowired
    LimitRepository limitRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address address = initAddress();

        User u = initCustomerUser(address);
        Limit l = initBankaccountLimit();
        initBankAccount(l);
        initTransactions(u);
    }

    private Address initAddress() {
        Address address = new Address();
        address.city("Amsterdam").country("Netherlands")
                .houseNumber(24).postalcode("1234FG").street("Long Street");

        return addressRepository.save(address);
    }

    private User initCustomerUser(Address address) {
        User customer = new User();
        customer.firstName("James").lastName("Dean").role(User.RoleEnum.CUSTOMER)
                .phoneNumber("0612345678").address(address).email("jamesdean@mail.com");

        userService.addUser(customer);
        return customer;
    }
    private Limit initBankaccountLimit(){
        Limit limit = new Limit();
        limit.limit(00.00);
        return limitRepository.save(limit);
    }
    private BankAccount initBankAccount(Limit limit){
        BankAccount bankAccount = new BankAccount();
        bankAccount.accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN("NL01INHO0000000001")
                .amount(1500.00).name("The Bank account").balanceMin(limit);

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
}
