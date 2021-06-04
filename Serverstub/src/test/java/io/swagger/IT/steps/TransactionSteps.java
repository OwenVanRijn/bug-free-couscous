package io.swagger.IT.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.IT.steps.restModels.LoginPostDTO;
import io.swagger.IT.steps.restModels.LoginPostResponseDTO;
import io.swagger.dto.BankaccountDTO;
import io.swagger.dto.TransactionDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.dto.UserDTO;
import io.swagger.model.BankAccount;
import io.swagger.services.IbanHelper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TransactionSteps {
    private final String baseTransactionUrl = "http://localhost:8080/api/transaction";
    private final String baseUserUrl = "http://localhost:8080/api/user";

    private final World world;

    public TransactionSteps(World world){
        this.world = world;
    }

    private List<BankaccountDTO> bankAccountList = new ArrayList<>();
    private double storedBalance = 0;

    @And("get all transactions")
    public void getAllTransactions() throws Exception {
        world.getRequest(baseTransactionUrl, TransactionsPageDTO.class);
    }

    @And("i get {int} transaction result(s)")
    public void iGetTransactionResult(int arg0) throws Exception {
        Long count = ((ResponseEntity<TransactionsPageDTO>)world.getLastResponse()).getBody().getTotalCount();
        if (count != arg0)
            throw new Exception(String.format("Transaction count %d does not match expected %d", count, arg0));
    }

    @And("i store their {int}(st)(th)(rd)(nd) bank account")
    public void iStoreTheirBankAccount(int arg0) throws Exception {
        ResponseEntity<UserDTO> response = world.getRequest(baseUserUrl, UserDTO.class);

        world.matchLastResponse(200);

        bankAccountList.add(response.getBody().getBankAccounts().get(arg0 - 1));
    }

    @Then("confirm that the stored bank account has {double} euro stored")
    public void confirmThatTheStoredBankAccountHasEuroStored(double arg0) throws Exception {
        if (bankAccountList.get(0).getAmount() != arg0)
            throw new Exception(String.format("Bank decimal amount %.2f does not match expected %.2f", bankAccountList.get(0).getAmount(), arg0));
    }

    @Then("i create a transaction worth {double} euro")
    public void iCreateATransactionWorthEuro(double euro) throws Exception {
        assert (bankAccountList.size() >= 2);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("IBAN_from", bankAccountList.get(0).getIban());
        jsonObject.put("IBAN_to", bankAccountList.get(1).getIban());
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
                UriComponentsBuilder.fromHttpUrl(baseTransactionUrl).queryParam("IBAN", bankAccountList.get(0).getIban()).toUriString(),
                TransactionsPageDTO.class
        );
    }

    @And("i store an invalid iban")
    public void iStoreAnInvalidIban() {
        BankaccountDTO b = new BankaccountDTO();
        b.setIban(IbanHelper.generateIban());
        bankAccountList.add(b);
    }

    @And("reverse the stored bank accounts")
    public void reverseTheStoredBankAccounts() {
        Collections.reverse(bankAccountList);
    }

    @And("store the {int}(st)(th)(rd)(nd) bank account's balance")
    public void storeTheStBankAccountSBalance(int arg0) {
        storedBalance = bankAccountList.get(arg0 - 1).getAmount();
    }
}
