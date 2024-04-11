package com.xoriant.bdd.steps;

import com.xoriant.bdd.utils.FeatureUtils;
import com.xoriant.model.Expense;
import com.xoriant.model.ExpenseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteExpenseTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Expense> response;
    private ExpenseDto expenseDto;
    Long id;

    @Given("the user provides expense id {string} for delete api")
    public void theUserProvidesExpenseIdForDeleteApi(String str) {
        id = Long.parseLong(str);
    }

    @When("the user sends a DELETE request to {string} api")
    public void the_user_sends_a_delete_request_to_api(String path) {
        response = restTemplate.exchange(
                FeatureUtils.URL + path + id, HttpMethod.DELETE,null,Expense.class
        );

    }

    @Then("the user gets the response with status code {int} for DELETE api")
    public void the_user_gets_the_response_with_status_code_for_delete_api(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }
}
