package io.swagger.IT.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonObject;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import io.swagger.dto.TransactionPostDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.dto.UserDTO;
import io.swagger.model.BankAccount;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TransactionSteps {
    private final String baseTransactionUrl = "http://localhost:8080/api/transaction";
    private final String baseLoginUrl = "http://localhost:8080/api/login";
    private final String baseUserUrl = "http://localhost:8080/api/users";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<TransactionsPageDTO> TPDTOresponseEntity;
    private int responseCode;

    private List<BankAccount> bankAccountList = new ArrayList<>();

    @When("i log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String arg0, String arg1) throws Exception {
        URI uri = new URI(baseLoginUrl);
        LoginPostDTO login = new LoginPostDTO(arg0, arg1);

        HttpEntity<LoginPostDTO> entity = new HttpEntity<>(login, headers);
        ResponseEntity<LoginPostResponseDTO> loginResponse = restTemplate.postForEntity(uri, entity, LoginPostResponseDTO.class);

        assert (loginResponse.getStatusCode() == HttpStatus.OK);
        headers.remove("Authorization");
        headers.add("Authorization", "Bearer " + loginResponse.getBody().getToken());
    }

    @And("get all transactions")
    public void getAllTransactions() throws Exception {
        URI uri = new URI(baseTransactionUrl);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        TPDTOresponseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, TransactionsPageDTO.class);
        responseCode = TPDTOresponseEntity.getStatusCodeValue();
    }

    @Then("i get http code {int}")
    public void iGetHttpCode(int arg0) {
        assert (responseCode == arg0);
    }

    @And("i get {int} transaction result(s)")
    public void iGetTransactionResult(int arg0) throws Exception {
        if (TPDTOresponseEntity.getBody().getTotalCount() != arg0)
            throw new Exception("Transaction counts are not equal");
    }

    @And("i store their {int}(st)(th)(rd)(nd) bank account")
    public void iStoreTheirBankAccount(int arg0) throws Exception {
        URI uri = new URI(baseUserUrl);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<UserDTO> userResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, UserDTO.class);
        assert (userResponse.getStatusCode() == HttpStatus.OK);

        bankAccountList.add(userResponse.getBody().getBankAccounts().get(arg0 - 1));
    }

    @Then("confirm that the stored bank account has {double} euro stored")
    public void confirmThatTheStoredBankAccountHasEuroStored(double arg0) {
        assert (bankAccountList.get(0).getAmountDecimal() == arg0);
    }

    @Then("i create a transaction worth {double} euro")
    public void iCreateATransactionWorthEuro(double euro) throws Exception {
        assert (bankAccountList.size() >= 2);
        URI uri = new URI(baseTransactionUrl);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("IBAN_from", bankAccountList.get(0).getIBAN());
        jsonObject.put("IBAN_to", bankAccountList.get(1).getIBAN());
        jsonObject.put("amount", euro);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        try {
            ResponseEntity<String> transactionResponse = restTemplate.postForEntity(uri, entity, String.class);
            responseCode = transactionResponse.getStatusCodeValue();
        } catch (HttpClientErrorException e){
            responseCode = e.getRawStatusCode();
        }
    }

    @Then("i clear all stored bank accounts")
    public void iClearAllStoredBankAccounts() {
        bankAccountList = new ArrayList<>();
    }

    @And("i get filtered transactions on iban")
    public void iGetFilteredTransactionsOnIban() throws Exception {
        URI uri = new URI(UriComponentsBuilder.fromHttpUrl(baseTransactionUrl).queryParam("IBAN", bankAccountList.get(0).getIBAN()).toUriString());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        TPDTOresponseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, TransactionsPageDTO.class);
        responseCode = TPDTOresponseEntity.getStatusCodeValue();
    }
}
