Feature: Transaction tests
  Scenario: Login and get transactions
    When i log in with username "user2" and password "user2"
    And get all transactions
    Then i get http code 200