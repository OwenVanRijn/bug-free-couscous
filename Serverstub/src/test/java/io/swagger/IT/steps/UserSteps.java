package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

    private UserDTO testUser;

    private int responseCode;
    private String responseMessage;

    private World world;

    public UserSteps() {
    }

    public UserSteps(World world){
        this.world = world;
    }

    private World world;

    public UserSteps(World world){
        this.world = world;
    }

    @And("I get all users")
    public void iGetAllUsers() throws URISyntaxException {
        URI uri = new URI(baseUserUrl);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UDTOResponseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, UserDTO.class);
        responseCode = UDTOResponseEntity.getStatusCodeValue();
    }

    @And("I get one User object")
    public void iGetOneUserObject() throws Exception {
        if (UDTOResponseEntity.getBody() == null)
            throw new Exception("Couldn't get User data");
        testUser = UDTOResponseEntity.getBody();
    }

    @And("I update own email")
    public void iOwnUpdateInformation() throws URISyntaxException {
        URI uri = new URI(baseUserUrl);
        CustomerEditUserDTO editUser = new CustomerEditUserDTO();
        editUser.setAddress(new AddressPutDTO().city("test").country("test").houseNumber(20).postalcode("test")
        .street("test"));
        editUser.setEmail("newtest@mail.com");
        editUser.setPhoneNumber("0612345678");
        HttpEntity<CustomerEditUserDTO> entity = new HttpEntity<>(editUser, headers);
        UDTOResponseEntity = restTemplate.exchange(uri, HttpMethod.PUT, entity, UserDTO.class);
        responseCode = UDTOResponseEntity.getStatusCodeValue();
    }

    @And("I get updated User email")
    public void iGetUpdatedUserInformation() {
       assert UDTOResponseEntity.getBody().getEmail().equals("newtest@mail.com");
    }

    @And("I update own {string} incorrectly")
    public void iUpdateOwnIncorrectly(String field) throws URISyntaxException {
        URI uri = new URI(baseUserUrl);
        CustomerEditUserDTO editUser = new CustomerEditUserDTO();
        if(field.equals("phoneNumber")) {
            editUser.setPhoneNumber("06152436er");
            editUser.setEmail("newtest@mail.com");
        } else if (field.equals("email")){
            editUser.setEmail("invalidmail.nl");
            editUser.setPhoneNumber("0612345678");
        }
        editUser.setAddress(new AddressPutDTO().city("test").country("test").houseNumber(20).postalcode("test")
                .street("test"));
        HttpEntity<CustomerEditUserDTO> entity = new HttpEntity<>(editUser, headers);
        try {
            UDTOResponseEntity = restTemplate.exchange(uri, HttpMethod.PUT, entity, UserDTO.class);
            responseCode = UDTOResponseEntity.getStatusCodeValue();
        } catch (HttpClientErrorException e) {
            responseCode = e.getRawStatusCode();
            responseMessage = e.getResponseBodyAsString();
            responseMessage = new HttpClientErrorMessageParser(responseMessage).getMessage();
        }
    }

    @And("Http message equals {string}")
    public void httpMessageEquals(String errorMessage) throws Exception {
        if (!responseMessage.equals("\"" + errorMessage + "\"")) {
            throw new Exception("Http message does not match");
        }
    }
}
