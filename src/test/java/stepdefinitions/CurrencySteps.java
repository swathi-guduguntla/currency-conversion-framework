package stepdefinitions;

import constants.ErrorMessages;
import constants.ErrorType;
import io.cucumber.java.en.*;
import models.ConversionRequest;
import org.junit.Assert;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencySteps {
    List<ConversionRequest> requests;
    Map<ConversionRequest, Double> results;
    Exception exception;
    private void resetState() {
        requests = new ArrayList<>();
        results = new HashMap<>();
        exception = null;
    }
    @Given("I load currency test data")
    public void loadData() throws Exception {
        resetState();
        requests = FileReaderUtil.readJson("testdata/currencyData.json");
    }
    @Given("I load currency test data from {string}")
    public void loadData(String fileName) throws Exception {
        resetState();
        TestDataReader reader = DataReaderFactory.getReader(fileName);
        requests = reader.read("testdata/" + fileName);
    }
    @Given("I have conversion request from {string} to {string} with amount {string}")
    public void setRequest(String from, String to, String amount) {
        resetState();
        ConversionRequest req =
                new ConversionRequest(from, to, Double.parseDouble(amount));
        requests.add(req);
        System.out.println("Request added: " + from + " -> " + to);
    }
    @When("I perform currency conversions")
    public void performConversions() {
        for (ConversionRequest req : requests) {
            try {
                double rate = ApiClient.getRate(req.getFrom(), req.getTo());
                double converted = ConversionUtil.convert(
                        req.getAmount(),
                        rate,
                        req.getFrom(),
                        req.getTo()
                );
                results.put(req, converted);
                System.out.println("Converted: " + req.getFrom() + " -> " + req.getTo()
                        + " = " + converted);
            } catch (Exception e) {
                exception = e;
                System.out.println("Error for " + req.getFrom() + ": " + e.getMessage());
            }
        }
    }
    @Then("I validate all conversion results within tolerance")
    public void validateResults() {
        Assert.assertFalse("No results found - API might have failed", results.isEmpty());
        for (ConversionRequest req : results.keySet()) {
            double rate = ApiClient.getRate(req.getFrom(), req.getTo());
            double expected = req.getAmount() * rate;
            double actual = results.get(req);
            boolean valid = ValidationUtil.isWithinTolerance(expected, actual);
            Assert.assertTrue(
                    "Validation failed for " + req.getFrom() + " -> " + req.getTo()
                            + " Expected: " + expected + " Actual: " + actual, valid
            );
        }
    }
    @When("I perform currency conversion")
    public void performConversion() {
        try {
            performConversions(); // reuse logic
        } catch (Exception e) {
            exception = e;
        }
    }
        @Then("the conversion should fail with {string}")
        public void validateFailure(String expectedError) {

            Assert.assertNotNull(String.valueOf(exception), "Expected failure but got success");
            ErrorType errorType = ErrorType.valueOf(expectedError);
            switch (errorType) {
                case INVALID_CURRENCY:
                    Assert.assertTrue(
                            exception.getMessage().contains(ErrorMessages.INVALID_CURRENCY)
                    );
                    break;
                case INVALID_AMOUNT:
                    Assert.assertTrue(
                            exception.getMessage().contains(ErrorMessages.INVALID_AMOUNT)
                    );
                    break;
                case SAME_CURRENCY:
                    Assert.assertTrue(
                            exception.getMessage().contains(ErrorMessages.SAME_CURRENCY)
                    );
                    break;
                default:
                    Assert.fail("Unknown error type: " + expectedError);
            }
        }
}
