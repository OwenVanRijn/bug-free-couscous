package io.swagger.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    private User user;
    private Address address;

    @Before
    public void createUser() {
        user = new User();
        user.firstName("James").lastName("Dean").phoneNumber("0612345678")
                .address(address).email("jamesdean@mail.com");
        user.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        user.setUsername("customer");
        user.setPassword("welkom");
    }

    @Before
    public void createAddress() {
        address = new Address();
        address.city("Amsterdam").country("Netherlands")
                .houseNumber(24).postalcode("1234FG").street("Long Street");
    }

    @Test
    public void createUserShouldNotBeNull () {
        assertNotNull(user);
    }

    @Test(expected = ResponseStatusException.class)
    public void emailNotContainingAtWillThrowIllegalArgumentException () {
        user.setEmail("testmail.nl");
    }

    @Test(expected = ResponseStatusException.class)
    public void emailNotContainingDotWillThrowIllegalArgumentException () {
        user.setEmail("testmail@nl");
    }

    @Test(expected = ResponseStatusException.class)
    public void phoneNumberContainingOtherCharactersThanNumbersWillThrowIllegalArgumentException () {
        user.setPhoneNumber("1234ff333");
    }

    @Test
    public void toUserDTOShouldNotBeNull () {
        assertNotNull(user.toUserDTO());
    }
}