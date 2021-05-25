package io.swagger.configuration;

import io.swagger.model.Address;
import io.swagger.model.BankAccount;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repositories.AddressRepository;
import io.swagger.repositories.BankAccountRepository;
import io.swagger.repositories.TransactionRepository;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address address = initAddress();

        User u = initCustomerUser(address);
        initBankAccount();
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
    private BankAccount initBankAccount(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN("NL91ABNA0417164300")
                .amount(1500.00).name("payment account");

        return bankAccountRepository.save(bankAccount);

    }

    private void initTransactions(User customer){
        for (int i = 0; i < 100; i++){
            Transaction t = new Transaction();
            t.type(Transaction.TypeEnum.TRANSACTION)
            .amount(ThreadLocalRandom.current().nextLong(100, 1000))
            .ibANFrom("NL01INHO0000000001")
            .ibANTo(String.format("NL%02dINHO0%09d", ThreadLocalRandom.current().nextInt(99), ThreadLocalRandom.current().nextInt(999999999)))
            .performedBy(customer);

            transactionRepository.save(t);
        }
    }
}
