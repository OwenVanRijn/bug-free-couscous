package io.swagger.IT.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.dto.BankaccountDTO;
import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;
import io.swagger.model.DepositOrWithdraw;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

public class BankaccountSteps {

    private final String baseBankaccountUrl = "http://localhost:8080/api/Bankaccount";
    private final World world;
    private List<BankaccountDTO> bankAccountList;
    private BankaccountDTO BDTO;
    private BankAccount bankAccount;

    public BankaccountSteps(World world) {
        this.world = world;
    }


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
        } else if (type.equals("savings"))
        {
            newBankaccount.name("savings account").type(BankAccount.AccountTypeEnum.SAVINGS);
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

    @And("I get an updated bankaccount with name {string} and type {string}")
    public void iGetAnUpdatedBankaccountWithNameAndTypeSavings(String name, String type) throws Exception {
        if (!BDTO.getName().equals(name))
        {
            throw new Exception("bankaccount name did not update");
        }
        if (type.equals("savings")){
            if (!BDTO.getAccountType().equals(BankAccount.AccountTypeEnum.SAVINGS)) {
                throw new Exception("bankaccount type did not update");
            }
        } else if (type.equals("current")) {
            if (!BDTO.getAccountType().equals(BankAccount.AccountTypeEnum.CURRENT)) {
                throw new Exception("bankaccount type did not update");
            }
        }
    }

    @Then("I delete bankaccount with Iban {string}")
    public void iDeleteBankaccountWithIban(String Iban) throws Exception {
        world.deleteRequest(baseBankaccountUrl + "/" + Iban, BankaccountDTO.class, Iban);
    }

    @When("I make a {string} of {double} to {string} account")
    public void iMakeADepositOfToNewAccount(String type, double amount, String accountType) throws Exception {
        DepositOrWithdraw deposit = new DepositOrWithdraw();
        if (accountType.equals("new")) {
            deposit.amount(amount).IBAN(BDTO.getIban());
        } else if (accountType.equals("unknown"))
        {
            deposit.amount(amount).IBAN("NL01INHO0000002");
        }
        if (type.equals("deposit")) {
            deposit.type(DepositOrWithdraw.TypeEnum.DEPOSIT);
        } else if (type.equals("withdraw")) {
            deposit.type(DepositOrWithdraw.TypeEnum.WITHDRAW);
        }
        world.putRequest(baseBankaccountUrl, DepositOrWithdraw.class, deposit);
    }

    @And("The amount of new account is {double}")
    public void theAmountOfNewAccountIs(double amount) throws Exception {
        if (BDTO.getAmount() != amount)
        {
            throw new Exception("Amount did not get updated");
        }
    }

    @Then("I get bankaccount by generated Iban")
    public void iGetBankaccountByGeneratedIban() throws Exception {
        world.getRequest(baseBankaccountUrl + "/" + BDTO.getIban(), BankaccountDTO.class);
    }

    @And("I search for own bankaccounts")
    public void iSearchForOwnBankaccounts() throws Exception {
        world.getRequest(baseBankaccountUrl, List.class);
    }

    @And("I get a list with bankaccounts")
    public void iGetAListWithBankaccounts() throws Exception {
        bankAccountList = (List<BankaccountDTO>) world.getLastResponse().getBody();
        if (bankAccountList.isEmpty()){
            throw new Exception("Unable to get bankaccounts");
        }
    }

    @When("I edit the name of an account to {string} and the type to current with the {string} Iban")
    public void iEditTheNameOfAnAccountToWithTheIban(String name, String type) throws Exception {
        CreateBankaccountDTO editBankaccount = new CreateBankaccountDTO();
        editBankaccount.name(name);
        editBankaccount.type(BankAccount.AccountTypeEnum.CURRENT);
        if (type.equals("right")){
            world.putRequest(baseBankaccountUrl + "/NL09INHO0722376752", BankaccountDTO.class, editBankaccount);
        } else if (type.equals("wrong")){
            world.putRequest(baseBankaccountUrl + "/" + BDTO.getIban(), BankaccountDTO.class, editBankaccount);
        }
    }


//
//    @And("I get one BankaccountDTO list")
//    public void iGetOneBankaccountDTOList() throws Exception {
//        bankAccountList = (List<BankaccountDTO>) world.getLastResponse().getBody();
//        if (bankAccountList == null)
//            throw new Exception("Couldn't get customer bankaccounts");
//    }
}
