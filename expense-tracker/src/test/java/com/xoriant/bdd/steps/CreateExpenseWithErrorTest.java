package com.xoriant.bdd.steps;

import com.xoriant.bdd.utils.FeatureUtils;
import com.xoriant.exception.ErrorMessage;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateExpenseWithErrorTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<ErrorMessage> response;
    private ExpenseDto expenseDto;

    // <costCode>, <stateId>, <locationId>, <categoryId>
    @Given("User provides {long}, {long}, {long}, {long}")
    public void user_provides(Long costCode, Long stateId, Long locationId, Long categoryId) {
        expenseDto = new ExpenseDto();
        expenseDto.setCostCode(costCode);
        expenseDto.setStateId(stateId);
        expenseDto.setLocationId(locationId);
        expenseDto.setCategoryId(categoryId);
    }

    @When("the user sends a POST request to {string} for create expense api with invalid input")
    public void the_user_sends_a_post_request_to_for_create_expense_api_with_invalid_input(String path) {
        response = this.restTemplate.postForEntity(FeatureUtils.URL + path, expenseDto, ErrorMessage.class);
    }

    @Then("the response status code of invalid create expense will be {int}")
    public void the_response_status_code_of_invalid_create_expense_will_be(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @And("the response body has fields {int} and {string}")
    public void the_response_body_has_fields_and(int statusCode, String message) {
        ErrorMessage errorMessage = response.getBody();
        assertEquals(statusCode, errorMessage.getStatusCode());
        assertEquals("uri=/expenses", errorMessage.getDescription());
        assertNotNull(errorMessage.getTimestamp());
        assertNotNull(errorMessage.getMessage());
    }
}
