Feature: User tests
  Scenario: Login is status OK
    When i log in with username "user2" and password "user2"
    Then i get http code 200

  Scenario: Login failed is status FORBIDDEN
    When I login with username "tester123" and password "welcome123"
    Then I get http status 403

  Scenario: Customer gets own information
    When I login with username "customer" and password "welkom"
    And I get all users
    Then I get http status 200
    And I get one User object

  Scenario: Customer edits own email correctly
    When I login with username "customer" and password "welkom"
    And I update own email
    Then I get http status 200
    And I get one User object
    And I get updated User email
    
  Scenario: Customer edits phone number incorrectly
    When I login with username "customer" and password "welkom"
    And I update own "phoneNumber" incorrectly
    Then I get http status 422
    And Http message equals "Not a valid phone number"
  
  Scenario: Customer edits email incorrectly
    When I login with username "customer" and password "welkom"
    And I update own "email" incorrectly
    Then I get http status 422
    And Http message equals "Not a valid email address"
   