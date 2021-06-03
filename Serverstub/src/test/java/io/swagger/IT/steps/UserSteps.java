package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class UserSteps {
    private final String baseLoginUrl = "http://localhost:8080/api/login";
    private final String baseUserUrl = "http://localhost:8080/api/users";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private ObjectMapper mapper = new ObjectMapper();
    private int responseCode;

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String arg0, String arg1) throws URISyntaxException, JsonProcessingException {
        URI uri = new URI(baseLoginUrl);
        LoginPostDTO login = new LoginPostDTO(arg0, arg1);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(login), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entity, String.class);

        responseCode = responseEntity.getStatusCodeValue();
    }

    @Then("I get http status {int}")
    public void iGetHttpStatus(int arg0) throws Exception {
        if (responseCode != arg0)
            throw new Exception("Http code does not match");
    }
}
