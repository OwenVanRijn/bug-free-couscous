Feature: employee transaction tests
  Scenario: editing a transactions amount
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And reverse the stored bank accounts
    And i create a transaction worth 5.00 euro
    And i clear all stored bank accounts
    And i store their 1st bank account
    And store the 1st bank account's balance
    When i log in with username "employee" and password "welkom"
    And i get the latest transaction
    And i edit the stored transaction amount to 10.00 euro
    Then i get http code 200
    When i log in with username "user2" and password "user2"
    And i clear all stored bank accounts
    And i store their 1st bank account
    Then the bank balance should have changed by -5.00 euro

  Scenario: delete last transaction
    When i log in with username "employee" and password "welkom"
    And i get the latest transaction
    And i delete the stored transaction
    Then i get http code 200

  # TODO: scenario: editing a transactions from iban
  # TODO: scenario: editing a transactions to iban
  # TODO: scenario: editing a transactions from iban, to and amount
  # TODO: scenario: deleting all done transactions in this test