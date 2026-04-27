@smoke
Feature: Currency Conversion Validation

  @success
  Scenario Outline: Success <tcNo> - Single conversion validation
    Given I have conversion request from "<from>" to "<to>" with amount "<amount>"
    When I perform currency conversion
    Then I validate all conversion results within tolerance
    Examples:
      | tcNo | from | to  | amount |
      | SC01 | USD  | EUR | 100    |
      | SC02 | GBP  | INR | 50     |

  @success
  Scenario: Validate multiple currency conversions within tolerance
    Given I load currency test data
    When I perform currency conversions
    Then I validate all conversion results within tolerance

  @success
  Scenario Outline: Success <tcNo> - <fileType> conversion scenario
    Given I load currency test data from "<fileName>"
    When I perform currency conversions
    Then I validate all conversion results within tolerance
    Examples:
      | tcNo | fileType | fileName          |
      | SC01 | JSON     | currencyData.json |
      | SC02 | CSV      | currencyData.json |
      | SC03 | YAML     | currencyData.json |

  @invalid
  Scenario Outline: Invalid <tcNo> - Invalid currency conversion
    Given I have conversion request from "<from>" to "<to>" with amount "<amount>"
    When I perform currency conversion
    Then the conversion should fail with "<errorType>"
    Examples:
      | tcNo | from | to  | amount | errorType        |
      | IV01 | XXX  | EUR | 100    | INVALID_CURRENCY |
      | IV02 | USD  | EUR | -100   | INVALID_AMOUNT   |
      | IV03 | USD  | EUR | 0      | INVALID_AMOUNT   |
      | IV04 | USD  | ABC | 100    | INVALID_CURRENCY |
      | IV05 | USD  | USD | 100    | SAME_CURRENCY    |