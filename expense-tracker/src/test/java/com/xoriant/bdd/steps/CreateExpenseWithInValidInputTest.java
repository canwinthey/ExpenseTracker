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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateExpenseWithInValidInputTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<ErrorMessage> response;
    private ExpenseDto expenseDto;


    @Given("the user provides invalid expense details")
    public void the_user_provides_invalid_expense_details() {
        expenseDto = FeatureUtils.getMockExpenseDto("ExpenseDto-invalid.json");
    }

    @When("the user sends a POST request to {string} for ErrorMessage")
    public void the_user_sends_a_POST_request_to_for_ErrorMessage(String path) {
        response = this.restTemplate.postForEntity(FeatureUtils.URL + path, expenseDto, ErrorMessage.class);
    }

    @Then("the response status code of invalid create expense should be {int}")
    public void the_response_status_code_of_invalid_create_expense_should_be(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @And("the response body should contain ErrorMessage object")
    public void the_response_body_should_contain_ErrorMessage_object() {
        ErrorMessage errorMessage = response.getBody();
        assertEquals(406, errorMessage.getStatusCode());
        assertEquals("uri=/expenses", errorMessage.getDescription());
        assertNotNull(errorMessage.getTimestamp());
        assertNotNull(errorMessage.getMessage());
    }
}
