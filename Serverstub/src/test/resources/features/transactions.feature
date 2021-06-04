
# Test testing environment: 2 accounts, user1 and user2, both have a bankaccount
Feature: Customer transaction tests
  Scenario: Login and get transactions
    When i log in with username "user2" and password "user2"
    And get all transactions
    Then i get http code 200
    And i get 1 transaction result

  Scenario: Verify user1's bank account
    When i log in with username "user1" and password "user1"
    Then i store their 1st bank account
    And confirm that the stored bank account has 150.00 euro stored
    Then i clear all stored bank accounts
    Then i store their 2nd bank account
    And confirm that the stored bank account has 50.00 euro stored

  Scenario: Verify user2's bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    Then confirm that the stored bank account has 50.00 euro stored

  Scenario: Attempt to create a transaction
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And reverse the stored bank accounts
    Then i create a transaction worth 5.00 euro
    Then i get http code 200
    Then i clear all stored bank accounts
    And i store their 1st bank account
    Then confirm that the stored bank account has 45.00 euro stored

  Scenario: Attempt to put an invalid amount in a transaction
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And reverse the stored bank accounts
    Then i create a transaction worth -5.00 euro
    Then i get http code 400

  Scenario: Confirm that the newly added transaction exists on the other side
    When i log in with username "user1" and password "user1"
    And get all transactions
    Then i get http code 200
    And i get 2 transaction results

  Scenario: Send some money to a savings account
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    And i store their 2nd bank account
    Then i create a transaction worth 5.00 euro
    Then i get http code 200
    Then i clear all stored bank accounts
    And i store their 1st bank account
    Then confirm that the stored bank account has 150.00 euro stored

  Scenario: Try to store money on someone else's saving account
    When i log in with username "user1" and password "user1"
    And i store their 2nd bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And reverse the stored bank accounts
    Then i create a transaction worth 5.00 euro
    Then i get http code 401

  Scenario: Filter transactions on iban
    When i log in with username "user1" and password "user1"
    And i store their 2nd bank account
    And i get filtered transactions on iban
    Then i get http code 200
    And i get 1 transaction result
    
  Scenario: Filter on invalid iban
    When i log in with username "user1" and password "user1"
    And i store an invalid iban
    And i get filtered transactions on iban
    Then i get http code 200
    And i get 0 transaction results
    
  Scenario: Sending money to yourself
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    And i store their 1st bank account
    And i create a transaction worth 5.00 euro
    Then i get http code 400
    And Http message equals "Source and Dest cannot be the same"

  Scenario: Sending more money then you currently have
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And reverse the stored bank accounts
    And i create a transaction worth 500.00 euro
    Then i get http code 400
    And Http message equals "Bank accounts cannot go under the limit"

  Scenario: sending money from an invalid iban
    When i log in with username "user2" and password "user2"
    And i store an invalid iban
    And i store their 1st bank account
    And i create a transaction worth 5.00 euro
    Then i get http code 401
    # And Http message equals "You do not own the from bankaccount" # this test does not give a response??

  Scenario: sending money to an invalid iban
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And i store an invalid iban
    And i create a transaction worth 5.00 euro
    Then i get http code 400
    And Http message equals "IBAN to not found!"