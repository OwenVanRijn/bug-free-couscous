Feature: Bankaccount tests
  Scenario: Login with customer gives status 200
    When i log in with username "customer" and password "welkom"
    Then i get http code 200

  Scenario: Login with employee gives status 200
    When i log in with username "employee" and password "welkom"
    Then i get http code 200

  Scenario: Employee gets single bankaccount
    When i log in with username "employee" and password "welkom"
    And I get bankaccount by Iban "NL01INHO0000000001"
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
    And I update bankaccount with Iban "NL01INHO0000000001" name to "updated bankaccount name" and type to "savings"
    Then i get http code 200
    And I get one Bankaccount object
    And I get an updated bankaccount with name "updated bankaccount name" and type savings

  Scenario: Employee edits bankaccount to wrong account type
    When i log in with username "employee" and password "welkom"
    And I update bankaccount with Iban "NL01INHO0000000001" name to "updated bankaccount name" and type to "wrong"
    Then i get http code 500

  Scenario: Employee deletes bankaccount
    When i log in with username "employee" and password "welkom"
    And I get bankaccount by Iban "NL01INHO0000000001"
    Then i get http code 200
    And I get one Bankaccount object
    Then I delete bankaccount with Iban "NL01INHO0000000001"
    And i get http code 200
    Then I get bankaccount by Iban "NL01INHO0000000001"
    And i get http code 404
    And Http message equals "IBAN not found!"

  Scenario: Employee deletes unknown Iban
    When i log in with username "employee" and password "welkom"
    And I delete bankaccount with Iban "NL01INHO0000001"
    Then i get http code 404
    And Http message equals "IBAN not found!"

#  Scenario: Customer gets own bankaccounts
#    When i log in with username "customer" and password "welkom"
#    And I get customers bankaccounts
#    Then i get http code 200
#    And I get one BankaccountDTO list
