package io.swagger.configuration;

import io.swagger.model.Address;
import io.swagger.model.User;
import io.swagger.repositories.AddressRepository;
import io.swagger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("HET WERKT");

        Address address = initAddress();

        initCustomerUser(address);
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

        return userService.addUser(customer);
    }
}
