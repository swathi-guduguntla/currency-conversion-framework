package stepdefinitions;

import io.cucumber.java.en.*;
import models.ConversionRequest;
import org.junit.Assert;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.ApiClient.getRate;

public class CurrencySteps {
    List<ConversionRequest> requests;
    Map<ConversionRequest, Double> results = new HashMap<>();
    String errorMessage;
    @Given("I load currency test data")
    public void loadData() throws Exception {
        requests = FileReaderUtil.readJson("testdata/currencyData.json");
    }
    @Given("I load currency test data from {string}")
    public void loadData(String fileName) throws Exception {
        TestDataReader reader = DataReaderFactory.getReader(fileName);
        requests = reader.read("testdata/" + fileName);
    }
    @Given("I have conversion request from {string} to {string} with amount {string}")
    public void setRequest(String from, String to, String amount) {

        if (requests == null) {
            requests = new ArrayList<>();
        }
        ConversionRequest req =
                new ConversionRequest(from, to, Double.parseDouble(amount));
        requests.add(req);
        System.out.println("===== request ===== "+ requests.size() + "    " + requests.get(0));
    }
    @When("I perform currency conversions")
    public void performConversions() {
        for (ConversionRequest req : requests) {
            double rate = getRate(req.getFrom(), req.getTo());
            double converted = ConversionUtil.convert(req.getAmount(), rate);
            System.out.println("Rate and converted is :   " + rate + "     " + converted);
            results.put(req, converted);
        }
    }
    @Then("I validate all conversion results within tolerance")
    public void validateResults() {
        Assert.assertFalse("No results found - API might have failed", results.isEmpty());
        for (ConversionRequest req : results.keySet()) {
            double rate = ApiClient.getRate(req.getFrom(), req.getTo());
            double expected = req.getAmount() * rate;

            double actual = results.get(req);
            if (!ValidationUtil.isWithinTolerance(expected, actual)) {
                throw new AssertionError("Validation failed for " + req.getFrom() + " -> " + req.getTo());
            }
        }
    }
    @When("I perform currency conversion")
    public void performConversion() {
        try {
            ConversionRequest req = requests.get(0);

            double rate = getRate(req.getFrom(), req.getTo());
            double converted = ConversionUtil.convert(req.getAmount(), rate);
            System.out.println("Rate and converted is :   " + rate + "     " + converted);

            results.put(req, converted);

        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }
    @Then("the conversion should fail with {string}")
    public void validateFailure(String expectedError) {

        Assert.assertNotNull(errorMessage, "Expected failure but got success");

        if (expectedError.equals("INVALID_CURRENCY")) {
            Assert.assertTrue(errorMessage.contains("Invalid currency"));
        } else if (expectedError.equals("INVALID_AMOUNT")) {
            Assert.assertTrue(errorMessage.contains("amount"));
        }
    }

}
