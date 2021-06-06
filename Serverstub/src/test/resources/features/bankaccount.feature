Feature: Bankaccount tests
  #Employee

  Scenario: Login with customer gives status 200
    When i log in with username "customer" and password "welkom"
    Then i get http code 200

  Scenario: Login with employee gives status 200
    When i log in with username "employee" and password "welkom"
    Then i get http code 200

  Scenario: Employee gets single bankaccount
    When i log in with username "employee" and password "welkom"
    And I get bankaccount by Iban "NL09INHO0722376752"
    Then i get http code 200
    And I get one Bankaccount object

  Scenario: Employee searches for wrong Iban
    When i log in with username "employee" and password "welkom"
    And I get bankaccount by Iban "NL01INHO0000002"
    Then i get http code 404
    And Http message equals "IBAN not found!"

  Scenario: Employee creates a bankaccount
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "correct"
    Then i get http code 201
    And I get one Bankaccount object

  Scenario: Employee creates wrong bankaccount
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "wrong"
    Then i get http code 500

  Scenario: Employee edits bankaccount name and account type
    When i log in with username "employee" and password "welkom"
    And I update bankaccount with Iban "NL09INHO0722376752" name to "updated bankaccount name" and type to "savings"
    Then i get http code 200
    And I get one Bankaccount object
    And I get an updated bankaccount with name "updated bankaccount name" and type "savings"

  Scenario: Employee edits bankaccount to wrong account type
    When i log in with username "employee" and password "welkom"
    And I update bankaccount with Iban "NL09INHO0722376752" name to "updated bankaccount name" and type to "wrong"
    Then i get http code 500

  Scenario: Employee deletes bankaccount
    When i log in with username "employee" and password "welkom"
    And I get bankaccount by Iban "NL09INHO0722376752"
    Then i get http code 200
    And I get one Bankaccount object
    Then I delete bankaccount with Iban "NL09INHO0722376752"
    And i get http code 200
    Then I get bankaccount by Iban "NL09INHO0722376752"
    And i get http code 404
    And Http message equals "IBAN not found!"

  Scenario: Employee deletes unknown Iban
    When i log in with username "employee" and password "welkom"
    And I delete bankaccount with Iban "NL01INHO0000001"
    Then i get http code 404
    And Http message equals "IBAN not found!"

  Scenario: Employee creates a bankaccount and deposits money to it
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "correct"
    Then I get one Bankaccount object
    And i get http code 201
    When I make a "deposit" of 5000 to "new" account
    Then i get http code 200
    Then I get bankaccount by generated Iban
    And I get one Bankaccount object
    And The amount of new account is 50

  Scenario: Employee creates a bankaccount, deposits and withdraws money
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "correct"
    Then I get one Bankaccount object
    And i get http code 201
    When I make a "deposit" of 5000 to "new" account
    Then i get http code 200
    Then I get bankaccount by generated Iban
    And I get one Bankaccount object
    And The amount of new account is 50
    When I make a "withdraw" of 2500 to "new" account
    Then i get http code 200
    Then I get bankaccount by generated Iban
    And I get one Bankaccount object
    And The amount of new account is 25

  Scenario: Employee withdraws to much money and gets account under limit
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "correct"
    Then I get one Bankaccount object
    And i get http code 201
    When I make a "withdraw" of 2500 to "new" account
    Then i get http code 400
    And Http message equals "Bank accounts cannot go under the limit"

  Scenario: Employee deposits to unknown Iban
    When i log in with username "employee" and password "welkom"
    And I make a "deposit" of 5000 to "unknown" account
    Then i get http code 404
    And Http message equals "IBAN not found!"

  Scenario: Employee deposits to and withdraws from a savings account
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "savings"
    Then I get one Bankaccount object
    And i get http code 201
    When I make a "deposit" of 5000 to "new" account
    Then i get http code 400
    And Http message equals "You cannot Deposit or Withdraw to a savings account!"
    When I make a "withdraw" of 2500 to "new" account
    Then i get http code 400
    And Http message equals "You cannot Deposit or Withdraw to a savings account!"

  Scenario: Employee deposits and withdraws a negative amount of money
    When i log in with username "employee" and password "welkom"
    And I create new Bankaccount "correct"
    Then I get one Bankaccount object
    And i get http code 201
    When I make a "deposit" of -5000 to "new" account
    Then i get http code 400
    And Http message equals "Invalid amount"
    When I make a "withdraw" of -2500 to "new" account
    Then i get http code 400
    And Http message equals "Invalid amount"

  # Customer

  Scenario: Customer gets own bankaccounts
    When i log in with username "customer" and password "welkom"
    And I search for own bankaccounts
    Then i get http code 200
    And I get a list with bankaccounts

    Scenario: Customer edits their Bankaccount name and type
      When i log in with username "customer" and password "welkom"
      And I edit the name of an account to "updated bankaccount name" and the type to current with the "right" Iban
      Then i get http code 200
      And I get one Bankaccount object
      And I get an updated bankaccount with name "updated bankaccount name" and type "current"

    Scenario: Customer tries to edit someone else's account
      When i log in with username "employee" and password "welkom"
      And I create new Bankaccount "correct"
      Then I get one Bankaccount object
      And i get http code 201
      When i log in with username "customer" and password "welkom"
      And I edit the name of an account to "updated bankaccount name" and the type to current with the "wrong" Iban
      Then i get http code 401
