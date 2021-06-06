#noinspection SpellCheckingInspection
Feature: User tests
  Scenario: Login is status OK
    When i log in with username "user2" and password "user2"
    Then i get http code 200

  Scenario: Login failed is status UNAUTHORIZED
    When i log in with username "tester123" and password "wrongpassword" and store the result
    Then i get http code 401

#customer tests

  Scenario: Customer gets own information
    When i log in with username "customer" and password "welkom"
    And I get own information
    Then i get http code 200
    And I get one UserDTO object

  Scenario: Customer edits own email correctly
    When i log in with username "customer" and password "welkom"
    And I update own email to "newemail@mail.com"
    Then i get http code 200
    And I get one UserDTO object
    And I get updated User "email", "newemail@mail.com"

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

  Scenario: Customer tries to use employee function
    When i log in with username "customer" and password "welkom"
    And I get all users
    Then i get http code 403

#employee tests

  Scenario: Employee gets own information
    When i log in with username "employee" and password "welkom"
    And I get own information
    Then i get http code 200
    And I get one UserDTO object

  Scenario: Employee gets all users
    When i log in with username "employee" and password "welkom"
    And I get all users
    Then i get http code 200
    And I get 50 User objects

  Scenario: Employee gets all users with limited amount
    When i log in with username "employee" and password "welkom"
    And I get all users with limit 25
    Then i get http code 200
    And I get 25 User objects with 2 page(s)

  Scenario: Employee creates new User
    When i log in with username "employee" and password "welkom"
    And I create new User "correct"
    Then i get http code 201
    And I get one UserDTO object

  Scenario: Employee confirms that User has been added
    When i log in with username "employee" and password "welkom"
    And I get all users
    Then i get http code 200
    And I get 51 User objects

  Scenario: Employee creates new User with missing fields
    When i log in with username "employee" and password "welkom"
    And I create new User "incorrect"
    Then i get http code 400

  Scenario: Employee gets single User
    When i log in with username "employee" and password "welkom"
    And I get a single user by id 7
    Then i get http code 200
    And I get one User object

  Scenario: Employee gets single User with invalid id
    When i log in with username "employee" and password "welkom"
    And I get a single user by id 999
    Then i get http code 404

  Scenario: Employee deletes user by id
    When i log in with username "employee" and password "welkom"
    And I get a single user by id 10
    Then i get http code 200
    And I get one User object
    Then I delete single user by id 10
    And i get http code 204
    Then I get a single user by id 10
    And i get http code 404

  Scenario: Employee deletes user by invalid id
    When i log in with username "employee" and password "welkom"
    And I delete single user by id 999
    Then i get http code 404

  Scenario: Employee edits customers firstname
    When i log in with username "employee" and password "welkom"
    And I update customer with id 15 firstname to "Niek"
    Then i get http code 200
    And I get one UserDTO object
    And I get updated User "firstname", "Niek"
