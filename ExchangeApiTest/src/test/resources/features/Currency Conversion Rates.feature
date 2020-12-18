@testOnly
Feature: Latest Foreign Exchange Rates with Symbols and Base
  I want to validate the Latest foreign exchange rates API with symbols, base and various other parameters.

  @currentDate
  Scenario Outline: I want to validate the response for the latest foreign exchange reference rates api
    Given a valid request is created with uri <endPoint>
    When the api returns a valid response "200"
    And the base currency is <currency>
    Then response time is less than "4000" ms
    And the exchange rate date should be current date
    And the exchange quotes should match <exchangeRates>

    Examples: 
      | endPoint                      | currency | exchangeRates               |
      | "latest"                      | "EUR"    | "CurrentDate_Base_EUR.json" |
      | "latest?symbols=USD,GBP,INR"  | "EUR"    | "CurrentDate_Base_EUR.json" |
      | "latest?base=USD"             | "USD"    | "CurrentDate_Base_USD.json" |
      | "latest?base=USD&symbols=GBP" | "USD"    | "CurrentDate_Base_USD.json" |
      | "2030-01-12"                  | "EUR"    | "CurrentDate_Base_EUR.json" |

  @One
  Scenario: I want to validate that the service is able to handle the request when passing the wrong api uri
    Given a valid request is created with uri "/api"
    When the api returns a valid response "400"
    Then the "error" response is "time data 'api' does not match format '%Y-%m-%d'"

  @specificDate
  Scenario Outline: I want to validate the response for the specific date foreign exchange reference rates api
    Given a valid request is created with uri <endPoint>
    When the api returns a valid response "200"
    And the base currency is <currency>
    Then response time is less than "4000" ms
    And the exchange rate date should be <date>
    And the exchange quotes should match <exchangeRates>

    Examples: 
      | endPoint                          | currency | exchangeRates                      | date         |
      | "2010-01-12"                      | "EUR"    | "PastDate2010-01-12_Base_EUR.json" | "2010-01-12" |
      | "2010-01-12?symbols=USD,GBP"      | "EUR"    | "PastDate2010-01-12_Base_EUR.json" | "2010-01-12" |
      | "2010-01-12?base=USD"             | "USD"    | "PastDate2010-01-12_Base_USD.json" | "2010-01-12" |
      | "2010-01-12?base=USD&symbols=GBP" | "USD"    | "PastDate2010-01-12_Base_USD.json" | "2010-01-12" |
