package io.swagger.IT.steps;

import io.cucumber.java.en.When;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class AuthenticationSteps {
    private final String baseLoginUrl = "http://localhost:8080/api/login";
    private RestTemplate restTemplate = new RestTemplate();

    private World world;

    public AuthenticationSteps(World world) {
        this.world = world;
    }

    @When("i log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String arg0, String arg1) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        URI uri = new URI(baseLoginUrl);
        LoginPostDTO login = new LoginPostDTO(arg0, arg1);

        HttpEntity<LoginPostDTO> entity = new HttpEntity<>(login, headers);
        ResponseEntity<LoginPostResponseDTO> loginResponse = restTemplate.postForEntity(uri, entity, LoginPostResponseDTO.class);

        assert (loginResponse.getStatusCode() == HttpStatus.OK);
        headers.remove("Authorization");
        headers.add("Authorization", "Bearer " + loginResponse.getBody().getToken());
        world.headers = headers;
    }
}
