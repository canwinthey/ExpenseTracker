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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateExpenseWithValidInputTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Expense> response;
    private ExpenseDto expenseDto;

    @Given("the user provides valid expense details")
    public void the_user_provides_valid_expense_details() {
        expenseDto = FeatureUtils.getMockExpenseDto("ExpenseDto-valid.json");
    }

    @When("the user sends a POST request to {string} for Expense")
    public void the_user_sends_a_post_request_to_for_expense(String path) {
        response = this.restTemplate.postForEntity(FeatureUtils.URL + path, expenseDto, Expense.class);
        System.out.println("hello");
    }

    @Then("the response status code of valid create expense should be {int}")
    public void the_response_status_code_of_valid_create_expense_should_be(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @And("the response body should contain fields currencyAmtInUSD, category, state and location")
    public void the_response_body_should_contain_fields_currencyAmtInUSD_category_state_and_location() {
        assertEquals(60.0000, response.getBody().getCurrencyAmtInUSD().doubleValue());
        assertEquals("California", response.getBody().getState());
        assertEquals("San Francisco", response.getBody().getLocation());
        assertEquals("Travel", response.getBody().getCategory());
    }



}
