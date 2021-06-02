package io.swagger.api;

import io.swagger.dto.UsersPageDTO;
import io.swagger.model.Address;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.repositories.AddressRepository;
import io.swagger.repositories.UserRepository;
import io.swagger.services.AddressService;
import io.swagger.services.BankaccountService;
import io.swagger.services.TransactionService;
import io.swagger.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersApiController.class)
public class UsersApiControllerTest {

    private static final String firstName = "James";
    private static final String lastName = "Dean";
    private static final String userName = "james123";
    private static final String email = "jamesdean@mail.com";
    private static final String phoneNumber = "0612345678";
    private ArrayList<Role> roles = new ArrayList<>();
    private static final Address address = new Address();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User testUser;
    private String jwt;

    public User createCustomer() {
        roles.add(Role.ROLE_CUSTOMER);
        User u = new User(userName, firstName, lastName, email, phoneNumber, address, roles);

        return userService.addUser(u);
    }

    @Test
    public void getAllUsers() throws Exception {
        testUser = createCustomer();

        UsersPageDTO allUsers = new UsersPageDTO(50L, 1,
                Stream.of(testUser).map(User::toUserDTO).collect(Collectors.toList()));
        System.out.println(allUsers);
        given(userService.getAllUsers(50, 1)).willReturn(allUsers);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}