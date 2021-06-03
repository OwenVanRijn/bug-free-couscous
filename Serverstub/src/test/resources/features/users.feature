Feature: User tests
  Scenario: Login is status OK
    When i log in with username "user2" and password "user2"
    Then i get http code 200