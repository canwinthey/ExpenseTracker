@tracker
Feature: Expense Tracker
  @@expenses
  Scenario: Create expense with valid input
    Given the user provides valid expense details
    When the user sends a POST request to "/expenses" for Expense
    Then the response status code of valid create expense should be 201
    And the response body should contain fields currencyAmtInUSD, category, state and location

  Scenario: Create expense with invalid input
    Given the user provides invalid expense details
    When the user sends a POST request to "/expenses" for ErrorMessage
    Then the response status code of invalid create expense should be 406
    And the response body should contain ErrorMessage object

  Scenario Outline: Create valid expense
    Given User provides <costCode>, "<description>", "<expenseDate>", <stateId>, <locationId>, "<currency>", "<amount>", <categoryId>
    When the user sends a POST request to "/expenses" for create expense api
    Then the response status code of create expense should be 201
    And the response body should contain fields "<currencyAmtInUSD>", "<category>", "<state>" and "<location>"
    Examples:
      | costCode | description  | expenseDate | stateId | locationId | currency | amount | categoryId | currencyAmtInUSD | category     | state      | location |
      | 100      | Travel to CA | 2024-04-11  | 1       | 2          | INR      | 5000   | 1          | 60.0             | Travel       | California | San Francisco |
      | 200      | Movie @PVR   | 2024-04-10  | 5       | 20         | INR      | 4000   | 2          | 48.0             | Entertainment| Georgia   | Atlanta |
      | 300      | Food @RMall  | 2024-04-09  | 9       | 39         | INR      | 6000   | 3          | 72.0             | Food         | Illinois   | Chicago |

  Scenario Outline: Create Invalid expense
    Given User provides <costCode>, <stateId>, <locationId>, <categoryId>
    When the user sends a POST request to "/expenses" for create expense api with invalid input
    Then the response status code of invalid create expense will be 406
    And the response body has fields <statusCode> and "<message>"
    Examples:
      | costCode | stateId | locationId | categoryId | statusCode | message                                                                                          |
      | 100      | 1       | 2          | 1          | 406        |Allowed values for costCode: 100 - Category(Travel), Location(Los Angeles, San Francisco)         |
      | 100      | 2       | 8          | 2          | 406        |Allowed values for costCode: 100 - Category(Entertainment),   Location(Philadelphia, Pittsburgh)  |
      | 100      | 3       | 13         | 3          | 406        |Allowed values for costCode: 100 - Category(Food),   Location(Houston, San Antonio)               |
      | 200      | 4       | 18         | 1          | 406        |Allowed values for costCode: 200 - Category(Travel), Location(Denver, Colorado Springs)           |
      | 200      | 5       | 23         | 2          | 406        |Allowed values for costCode: 200 - Category(Entertainment), Location(Atlanta, Columbus)           |
      | 200      | 6       | 29         | 3          | 406        |Allowed values for costCode: 200 - Category(Food), Location(Charlotte, Raleigh)                   |
      | 300      | 7       | 34         | 1          | 406        |Allowed values for costCode: 300 - Category(Travel), Location(Phoenix, Tucson)                    |
      | 300      | 8       | 38         | 2          | 406        |Allowed values for costCode: 300 - Category(Entertainment), Location(Huntsville, Birmingham)      |
      | 300      | 9       | 42         | 3          | 406        |Allowed values for costCode: 300 - Category(Food), Location(Chicago, Aurora)                      |
      | 400      | 10      | 47         | 1          | 406        |Allowed values for costCode: 400 - Category(Travel), Location(Jacksonville, Miami)                |
      | 400      | 11      | 52         | 2          | 406        |Allowed values for costCode: 400 - Category(Entertainment), Location(New York City, Buffalo)      |
      | 400      | 12      | 57         | 3          | 406        |Allowed values for costCode: 400 - Category(Food), Location(Columbus, Cleveland)                  |

  Scenario Outline: Get expense by id
    Given the user provides expense id "<id>"
    When the user sends a GET request to "/expenses/" api
    Then the user gets the response with status code <status> for api
    Examples:
      | id    | status |
      | 1     | 200    |
      | 100   | 404    |

  Scenario: Get all expenses
    Given the user does not provide any expense id
    When the user sends a GET request to "/expenses/"
    Then the user gets the response with status code 200 and list of expenses

  Scenario Outline: Delete expense by id
    Given the user provides expense id "<id>" for delete api
    When the user sends a DELETE request to "/expenses/" api
    Then the user gets the response with status code <status> for DELETE api
    Examples:
      | id    | status |
      | 1     | 200    |
      | 100   | 404    |
