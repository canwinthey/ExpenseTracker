package com.xoriant.bdd.steps;

import com.xoriant.bdd.utils.FeatureUtils;
import com.xoriant.model.Expense;
import com.xoriant.model.ExpenseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateValidExpenseTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Expense> response;
    private ExpenseDto expenseDto;

    //"<costCode>", "<description>", "<expenseDate>", "<stateId>", "<locationId>", "<currency>", "<amount>", "<categoryId>"
    @Given("User provides {long}, {string}, {string}, {long}, {long}, {string}, {string}, {long}")
    public void userProvides(Long costCode, String description, String date, Long stateId, Long locationId, String currency, String amount, Long categoryId) {
        expenseDto = new ExpenseDto();
        expenseDto.setCostCode(costCode);
        expenseDto.setDescription(description);
        expenseDto.setExpenseDate(LocalDate.parse(date));
        expenseDto.setStateId(stateId);
        expenseDto.setLocationId(locationId);
        expenseDto.setCurrency(currency);
        expenseDto.setAmount(new BigDecimal(amount));
        expenseDto.setCategoryId(categoryId);
    }

    @When("the user sends a POST request to {string} for create expense api")
    public void theUserSendsAPOSTRequestToForCreateExpenseApi(String path) {
        response = this.restTemplate.postForEntity(FeatureUtils.URL + path, expenseDto, Expense.class);
    }

    @Then("the response status code of create expense should be {int}")
    public void the_response_status_code_of_create_expense_should_be(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @And("the response body should contain fields {string}, {string}, {string} and {string}")
    public void theResponseBodyShouldContainFieldsAnd(String currencyAmtInUSD, String category, String state, String location) {
        System.out.println(currencyAmtInUSD + " == " + category + " == " + state + " == " + location);
        Expense expense = response.getBody();
        assertEquals(getDoubleValue(currencyAmtInUSD), getDoubleValue(expense.getCurrencyAmtInUSD().toString()));
        assertEquals(category, expense.getCategory());
        assertEquals(state, expense.getState());
        assertEquals(location, expense.getLocation());
    }

    public double getDoubleValue(String strAmount){
        return Double.parseDouble(String.format("%.2f", Double.parseDouble(strAmount))); // Round to 2 decimal places
    }
}
