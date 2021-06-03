#noinspection SpellCheckingInspection
Feature: User tests
  Scenario: Login is status OK
    When i log in with username "user2" and password "user2"
    Then i get http code 200

  Scenario: Login failed is status UNAUTHORIZED
    When i log in with username "tester123" and password "wrongpassword"
    Then i get http code 401

  Scenario: Customer gets own information
    When i log in with username "customer" and password "welkom"
    And I get all users
    Then i get http code 200
    And I get one User object

  Scenario: Customer edits own email correctly
    When i log in with username "customer" and password "welkom"
    And I update own email
    Then i get http code 200
    And I get one User object
    And I get updated User email
    
  Scenario: Customer edits phone number incorrectly
    When i log in with username "customer" and password "welkom"
    And I update own "phoneNumber" incorrectly
    Then i get http code 422
    And Http message equals "Not a valid phone number"
  
  Scenario: Customer edits email incorrectly
    When i log in with username "customer" and password "welkom"
    And I update own "email" incorrectly
    Then i get http code 422
    And Http message equals "Not a valid email address"
   