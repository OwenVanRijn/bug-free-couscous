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

        initUser(address);
    }

    private Address initAddress() {
        Address address = new Address();
        address.setCity("Amsterdam");
        address.setCountry("Netherlands");
        address.setHouseNumber(24);
        address.setPostalcode("1234PF");
        address.setStreet("Long Street");

        return addressRepository.save(address);
    }

    private User initUser(Address address) {
        User u = new User();
        u.setFirstName("James");
        u.setLastName("Dean");
        u.setRole(User.RoleEnum.CUSTOMER);
        u.setPhoneNumber("06123456798");
        u.setAddress(address);
        u.setEmail("jamesdean@mail.com");

        return userService.addUser(u);
    }
}
