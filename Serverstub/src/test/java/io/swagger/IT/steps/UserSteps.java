package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonObject;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import io.swagger.dto.*;
import io.swagger.model.Address;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.models.Response;
import io.swagger.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.annotation.Target;
import java.net.URI;
import java.net.URISyntaxException;

public class UserSteps {
    private final String baseLoginUrl = "http://localhost:8080/api/login";
    private final String baseUserUrl = "http://localhost:8080/api/users";

    private UserDTO newUser;
    private UserDTO oldUser;

    @Autowired
    private UserService userService;

    private World world;

    public UserSteps() {
    }

    public UserSteps(World world) {
        this.world = world;
    }

    @And("I get all users")
    public void iGetAllUsers() throws Exception {
        world.getRequest(baseUserUrl, UsersPageDTO.class);
    }

    @And("I get own information")
    public void iGetOwnInformation() throws Exception {
        world.getRequest(baseUserUrl, UserDTO.class);
    }

    @And("I get one UserDTO object")
    public void iGetOneUserDTOObject() throws Exception {
        newUser = (UserDTO) world.getLastResponse().getBody();
        if (newUser == null)
            throw new Exception("Couldn't get User data");
    }

    @And("I get all users with limit {int}")
    public void iGetAllUsersWithLimit(int limit) throws Exception {
        world.getRequest(baseUserUrl + "?limit=" + limit, UsersPageDTO.class);
    }

    @And("I update own email to {string}")
    public void iOwnUpdateInformation(String newEmail) throws Exception {
        CustomerEditUserDTO editUser = new CustomerEditUserDTO();
        editUser.setEmail(newEmail);

        world.putRequest(baseUserUrl, UserDTO.class, editUser);
    }

    @And("I get updated User {string}, {string}")
    public void iGetUpdatedUserInformation(String property, String newValue) throws Exception {
        if (property.equals("email")) {
            if (!newUser.getEmail().equals(newValue)) {
                throw new Exception("Email did not update");
            }
        } else if (property.equals("firstname")) {
            if (!newUser.getFirstName().equals(newValue)) {
                throw new Exception("First name did not update");
            }
        }
    }

    @And("I update own {string} incorrectly")
    public void iUpdateOwnIncorrectly(String field) throws Exception {
        CustomerEditUserDTO editUser = new CustomerEditUserDTO();
        if (field.equals("phoneNumber")) {
            editUser.setPhoneNumber("06152436er");//invalid phone number
            editUser.setEmail("newtest@mail.com");
        } else if (field.equals("email")) {
            editUser.setEmail("invalidmail.nl");//invalid email
            editUser.setPhoneNumber("0612345678");
        }
        editUser.setAddress(new AddressPutDTO().city("test").country("test").houseNumber(20).postalcode("test")
                .street("test"));

        world.putRequest(baseUserUrl, UserDTO.class, editUser);
    }

    @And("I get {int} User objects")
    public void iGetUserObjects(int userCount) throws Exception {
        Long count = ((ResponseEntity<UsersPageDTO>) world.getLastResponse()).getBody().getTotalCount();
        if (count != userCount)
            throw new Exception(String.format("User count %d does not match expected %d", count, userCount));
    }

    @And("I get {int} User objects with {int} page\\(s)")
    public void iGetUserObjects(int userCount, int pages) throws Exception {
        int count = ((ResponseEntity<UsersPageDTO>) world.getLastResponse()).getBody().getUsers().size();
        Integer pagesCount = ((ResponseEntity<UsersPageDTO>) world.getLastResponse()).getBody().getTotalPages();
        if (count != userCount)
            throw new Exception(String.format("User count %d does not match expected %d", count, userCount));
        if (pagesCount != pages)
            throw new Exception(String.format("Pages count %d does not match expected %d", pagesCount, pages));
    }

    @And("I create new User")
    public void iCreateNewUser() throws Exception {
        CreateUserDTO newUser = new CreateUserDTO();
        newUser.firstName("test").lastName("user").username("user123").password("welcome").email("mail@mail.com")
                .phoneNumber("0612345678").street("Big Street").houseNumber(20).postalcode("2025PX").city("Haarlem").country("NL");
        world.postRequest(baseUserUrl, UserDTO.class, newUser);
    }

    @And("I get a single user by id {int}")
    public void iGetASingleUserById(int id) throws Exception {
        world.getRequest(baseUserUrl + "/" + id, User.class);
    }

    @And("I get one User object")
    public void iGetOneUserObject() throws Exception {
        User user = (User) world.getLastResponse().getBody();
        if (user == null)
            throw new Exception("Couldn't get User data");
    }

    @Then("I delete single user by id {int}")
    public void iDeleteSingleUserById(int id) throws Exception {
        URI uri = new URI(baseUserUrl + "/" + id);
        world.deleteRequest(uri, User.class, id);
    }

    @And("I update customer with id {int} firstname to {string}")
    public void iUpdateCustomerWithIdFirstnameTo(int id, String newValue) throws Exception {
        EmployeeEditUserDTO editUser = new EmployeeEditUserDTO();
        editUser.setFirstName(newValue);

        world.putRequest(baseUserUrl + "/" + id, UserDTO.class, editUser);
    }
}
