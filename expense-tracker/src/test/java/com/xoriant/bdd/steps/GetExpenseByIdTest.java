package com.xoriant.bdd.steps;

import com.xoriant.bdd.utils.FeatureUtils;
import com.xoriant.model.Expense;
import com.xoriant.model.ExpenseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetExpenseByIdTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Expense> response;
    private ExpenseDto expenseDto;
    Long id;

    @Given("the user provides expense id {string}")
    public void theUserProvidesExpenseId(String str) {
        try {
            id = Long.parseLong(str);
        } catch (NumberFormatException ex){
            System.out.println(ex.getMessage());
        }
    }

    @When("the user sends a GET request to {string} api")
    public void the_user_sends_a_get_request_to_api(String path) {
        response = this.restTemplate.getForEntity(FeatureUtils.URL + path + id, Expense.class);
        System.out.println(response.getBody().toString());
    }

    @Then("the user gets the response with status code {int} for api")
    public void the_user_gets_the_response_with_status_code_for_api(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }


}
