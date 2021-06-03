Feature: User tests
  Scenario: Login is status OK
    When I login with username "user2" and password "user2"
    Then I get http status 200