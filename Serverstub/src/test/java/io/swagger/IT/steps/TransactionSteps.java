package io.swagger.IT.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import io.swagger.dto.TransactionDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.dto.UserDTO;
import io.swagger.model.BankAccount;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TransactionSteps {
    private final String baseTransactionUrl = "http://localhost:8080/api/transaction";
    private final String baseUserUrl = "http://localhost:8080/api/users";

    private final World world;

    public TransactionSteps(World world){
        this.world = world;
    }

    private List<BankAccount> bankAccountList = new ArrayList<>();

    @And("get all transactions")
    public void getAllTransactions() throws Exception {
        world.getRequest(baseTransactionUrl, TransactionsPageDTO.class);
    }

    @And("i get {int} transaction result(s)")
    public void iGetTransactionResult(int arg0) throws Exception {
        if (((ResponseEntity<TransactionsPageDTO>)world.getLastResponse()).getBody().getTotalCount() != arg0)
            throw new Exception("Transaction counts are not equal");
    }

    @And("i store their {int}(st)(th)(rd)(nd) bank account")
    public void iStoreTheirBankAccount(int arg0) throws Exception {
        ResponseEntity<UserDTO> response = world.getRequest(baseUserUrl, UserDTO.class);

        world.matchLastResponse(200);

        bankAccountList.add(response.getBody().getBankAccounts().get(arg0 - 1));
    }

    @Then("confirm that the stored bank account has {double} euro stored")
    public void confirmThatTheStoredBankAccountHasEuroStored(double arg0) throws Exception {
        if (bankAccountList.get(0).getAmountDecimal() != arg0)
            throw new Exception("Bank decimal amounts are not equal");
    }

    @Then("i create a transaction worth {double} euro")
    public void iCreateATransactionWorthEuro(double euro) throws Exception {
        assert (bankAccountList.size() >= 2);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("IBAN_from", bankAccountList.get(0).getIBAN());
        jsonObject.put("IBAN_to", bankAccountList.get(1).getIBAN());
        jsonObject.put("amount", euro);

        world.postRequest(baseTransactionUrl, String.class, jsonObject.toString());
    }

    @Then("i clear all stored bank accounts")
    public void iClearAllStoredBankAccounts() {
        bankAccountList = new ArrayList<>();
    }

    @And("i get filtered transactions on iban")
    public void iGetFilteredTransactionsOnIban() throws Exception {
        world.getRequest(
                UriComponentsBuilder.fromHttpUrl(baseTransactionUrl).queryParam("IBAN", bankAccountList.get(0).getIBAN()).toUriString(),
                TransactionsPageDTO.class
        );
    }
}
