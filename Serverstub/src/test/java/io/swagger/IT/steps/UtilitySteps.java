package io.swagger.IT.steps;

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
}
