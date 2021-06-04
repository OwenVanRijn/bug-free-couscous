Feature: employee transaction tests
  #
  #  Intentional path checks
  #
  Scenario: editing a transactions amount
    # Creating a transaction and storing the current bank balance
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And reverse the stored bank accounts
    And i create a transaction worth 5.00 euro
    And i clear all stored bank accounts
    And i store their 1st bank account
    And store the 1st stored bank account's balance

    # Editing the transaction
    When i log in with username "employee" and password "welkom"
    And i get the latest transaction
    And i edit the stored transaction amount to 10.00 euro
    Then i get http code 200

    # Checking if the edited transaction actually edited the balance
    When i log in with username "user2" and password "user2"
    And i clear all stored bank accounts
    And i store their 1st bank account
    Then the bank balance should have changed by -5.00 euro

  Scenario: delete last transaction
    # Storing the current bank balance
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And store the 1st stored bank account's balance
    And i clear all stored bank accounts

    # Deleting the transaction
    When i log in with username "employee" and password "welkom"
    And i get the latest transaction
    And i delete the stored transaction
    Then i get http code 200

    # Checking if the sent amount came back
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    Then the bank balance should have changed by 10 euro

  Scenario: Creating a transaction for other people as employee
    # Preparing for a transaction
    When i log in with username "user1" and password "user1"
    And i store their 1st bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And store the 2nd stored bank account's balance

    # Making the transaction
    When i log in with username "employee" and password "welkom"
    And i create a transaction worth 10 euro
    And i get http code 200
    And i clear all stored bank accounts
  
    # Verifying it's been added
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    Then the bank balance should have changed by 10 euro

  Scenario: Testing if an employee gets all transactions
    When i log in with username "employee" and password "welkom"
    And get all transactions
    Then i get 102 transaction results

  Scenario: clean up the last transaction
    When i log in with username "employee" and password "welkom"
    Then i delete the last 1 transaction

  #
  #  Error handling checks
  #

  Scenario: Testing if an empty PUT request fails
    When i log in with username "employee" and password "welkom"
    And i get the latest transaction
    And i send an edit request with no contents using the stored transaction
    Then i get http code 500
    And Http message equals "PutDTO is empty"

  Scenario: Testing if an invalid id on an edit request fails
    When i log in with username "employee" and password "welkom"
    And i create a request with not a valid id
    Then i get http code 404

  Scenario: Testing if an employee can send a transaction from the internal bank account
    When i log in with username "user2" and password "user2"
    And i store their 1st bank account
    And i store a bankaccount with iban "NL01INHO0000000001"
    And reverse the stored bank accounts
    When i log in with username "employee" and password "welkom"
    When i create a transaction worth 10 euro
    Then i get http code 401

  # TODO: scenario: editing a transactions from iban
  # TODO: scenario: editing a transactions to iban
  # TODO: scenario: editing a transactions from iban, to and amount