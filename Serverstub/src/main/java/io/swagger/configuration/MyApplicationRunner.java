package io.swagger.configuration;

import io.swagger.model.Address;
import io.swagger.model.BankAccount;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.repositories.AddressRepository;
import io.swagger.repositories.BankAccountRepository;
import io.swagger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address address = initAddress();

        initCustomerUser(address);
        initBankAccount();
    }

    private Address initAddress() {
        Address address = new Address();
        address.city("Amsterdam").country("Netherlands")
                .houseNumber(24).postalcode("1234FG").street("Long Street");

        return addressRepository.save(address);
    }

    private void initCustomerUser(Address address) {
        User customer = new User();
        customer.firstName("James").lastName("Dean").phoneNumber("0612345678")
                .address(address).email("jamesdean@mail.com");
        customer.setRoles(Arrays.asList(Role.ROLE_CUSTOMER));

        customer.setUsername("niek");
        customer.setPassword(passwordEncoder.encode("welkom"));

        userService.addUser(customer);
    }
    private BankAccount initBankAccount(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.accountType(BankAccount.AccountTypeEnum.CURRENT).IBAN("NL91ABNA0417164300")
                .amount(1500.00).name("payment account");

        return bankAccountRepository.save(bankAccount);

    }
}
