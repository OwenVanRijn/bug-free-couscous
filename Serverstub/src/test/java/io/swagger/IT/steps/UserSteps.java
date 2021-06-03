package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import io.swagger.dto.*;
import io.swagger.model.Address;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.models.Response;
import io.swagger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Target;
import java.net.URI;
import java.net.URISyntaxException;

public class UserSteps {
    private final String baseLoginUrl = "http://localhost:8080/api/login";
    private final String baseUserUrl = "http://localhost:8080/api/users";
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<UsersPageDTO> UPDTOResponseEntity;
    private ResponseEntity<UserDTO> UDTOResponseEntity;
    private ResponseEntity<CustomerEditUserDTO> CEUDTOResponseEntity;
    private HttpHeaders headers = new HttpHeaders();
    private ObjectMapper mapper = new ObjectMapper();

    private UserDTO newUser;
    private UserDTO oldUser;

    @Autowired
    private UserService userService;

    private World world;

    public UserSteps() {
    }

    public UserSteps(World world){
        this.world = world;
    }

    @And("I get all users")
    public void iGetAllUsers() throws Exception {
        world.getRequest(baseUserUrl, UserDTO.class);
    }

    @And("I get one User object")
    public void iGetOneUserObject() throws Exception {
        if (world.getLastResponse().getBody() == null)
            throw new Exception("Couldn't get User data");
        newUser = (UserDTO) world.getLastResponse().getBody();
    }

    @And("I update own email")
    public void iOwnUpdateInformation() throws Exception {
        CustomerEditUserDTO editUser = new CustomerEditUserDTO();
        editUser.setAddress(new AddressPutDTO().city("test").country("test").houseNumber(20).postalcode("test")
        .street("test"));
        editUser.email("newtest@mail.com").phoneNumber("0612345678");

        world.putRequest(baseUserUrl, UserDTO.class, editUser);
    }

    @And("I get updated User email")
    public void iGetUpdatedUserInformation() throws Exception {
       if(!newUser.getEmail().equals("newtest@mail.com")){
            throw new Exception("Email did not update");
        }
    }

    @And("I update own {string} incorrectly")
    public void iUpdateOwnIncorrectly(String field) throws Exception {
        CustomerEditUserDTO editUser = new CustomerEditUserDTO();
        if(field.equals("phoneNumber")) {
            editUser.setPhoneNumber("06152436er");//invalid phone number
            editUser.setEmail("newtest@mail.com");
        } else if (field.equals("email")){
            editUser.setEmail("invalidmail.nl");//invalid email
            editUser.setPhoneNumber("0612345678");
        }
        editUser.setAddress(new AddressPutDTO().city("test").country("test").houseNumber(20).postalcode("test")
                .street("test"));

        world.putRequest(baseUserUrl, UserDTO.class, editUser);
    }
}
