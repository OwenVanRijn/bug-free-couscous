package io.swagger.IT.steps;

import io.cucumber.java.bs.I;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.swagger.dto.BankaccountDTO;
import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankaccountSteps {

    private final String baseBankaccountUrl = "http://localhost:8080/api/Bankaccount";
    private final World world;
    private List<BankaccountDTO> bankAccountList = new ArrayList<>();
    private BankaccountDTO BDTO;

    public BankaccountSteps(World world) {
        this.world = world;
    }

//    @And("I get customers bankaccounts")
//    public void iGetCustomersBankaccounts() throws Exception {
//        world.getRequest(baseBankaccountUrl, bankAccountList);
//    }

    @And("I get bankaccount by Iban {string}")
    public void iGetBankaccountByIban(String iban) throws Exception {
        world.getRequest(baseBankaccountUrl + "/" + iban, BankaccountDTO.class);
    }

    @And("I get one Bankaccount object")
    public void iGetOneBankaccountObject() throws Exception {
        BDTO = (BankaccountDTO) world.getLastResponse().getBody();
        if (BDTO == null)
            throw new Exception("Couldn't get Bankaccount");
    }

    @And("I create new Bankaccount {string}")
    public void iCreateNewBankaccount(String type) throws Exception {
        CreateBankaccountDTO newBankaccount = new CreateBankaccountDTO();
        if (type.equals("correct"))
        {
            newBankaccount.name("Daily spending account").type(BankAccount.AccountTypeEnum.CURRENT);
        } else if (type.equals("wrong"))
        {
            newBankaccount.name("Wrong account").type(null);
        }
        world.postRequest(baseBankaccountUrl, BankaccountDTO.class, newBankaccount);
    }

    @And("I update bankaccount with Iban {string} name to {string} and type to {string}")
    public void iUpdateBankaccountWithIbanNameToAndTypeTo(String Iban, String name, String type) throws Exception {
        CreateBankaccountDTO editBankaccount = new CreateBankaccountDTO();
        editBankaccount.name(name);
        if (type.equals("savings"))
            editBankaccount.type(BankAccount.AccountTypeEnum.SAVINGS);

        world.putRequest(baseBankaccountUrl + "/" + Iban, BankaccountDTO.class, editBankaccount);
    }

    @And("I get an updated bankaccount with name {string} and type savings")
    public void iGetAnUpdatedBankaccountWithNameAndTypeSavings(String name) throws Exception {
        if (!BDTO.getName().equals(name))
        {
            throw new Exception("bankaccount name did not update");
        }
        if (!BDTO.getAccountType().equals(BankAccount.AccountTypeEnum.SAVINGS))
        {
            throw new Exception("bankaccount type did not update");
        }
    }

    @Then("I delete bankaccount with Iban {string}")
    public void iDeleteBankaccountWithIban(String Iban) throws Exception {
        world.deleteRequest(baseBankaccountUrl + "/" + Iban, BankaccountDTO.class, Iban);
    }

//
//    @And("I get one BankaccountDTO list")
//    public void iGetOneBankaccountDTOList() throws Exception {
//        bankAccountList = (List<BankaccountDTO>) world.getLastResponse().getBody();
//        if (bankAccountList == null)
//            throw new Exception("Couldn't get customer bankaccounts");
//    }
}
