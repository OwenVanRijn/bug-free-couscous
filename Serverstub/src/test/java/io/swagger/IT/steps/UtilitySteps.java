package io.swagger.IT.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class UtilitySteps {
    private World world;

    public UtilitySteps(World world){
        this.world = world;
    }

    @Then("i get http code {int}")
    public void iGetHttpStatus(int arg0) throws Exception {
        world.matchLastResponse(arg0);
    }

    @And("Http message equals {string}")
    public void httpMessageEquals(String errorMessage) throws Exception {
        System.out.println(errorMessage);
        world.matchLastResponseErrorMsg(errorMessage);
    }
 }
