package io.swagger.IT.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonArray;
import io.cucumber.messages.internal.com.google.gson.JsonElement;
import io.cucumber.messages.internal.com.google.gson.JsonObject;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class TransactionSteps {
    private final String baseTransactionUrl = "http://localhost:8080/api/transaction";
    private final String baseLoginUrl = "http://localhost:8080/api/login";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<String> responseEntity;

    @When("i log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String arg0, String arg1) throws Exception {
        URI uri = new URI(baseLoginUrl);
        LoginPostDTO login = new LoginPostDTO(arg0, arg1);

        HttpEntity<LoginPostDTO> entity = new HttpEntity<>(login, headers);
        ResponseEntity<LoginPostResponseDTO> loginResponse = restTemplate.postForEntity(uri, entity, LoginPostResponseDTO.class);

        assert (loginResponse.getStatusCode() == HttpStatus.OK);
        headers.add("Authorization", "Bearer " + loginResponse.getBody().getToken());
    }

    @And("get all transactions")
    public void getAllTransactions() throws Exception {
        URI uri = new URI(baseTransactionUrl);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @Then("i get http code {int}")
    public void iGetHttpCode(int arg0) {
        assert (responseEntity.getStatusCodeValue() == arg0);
    }
}
