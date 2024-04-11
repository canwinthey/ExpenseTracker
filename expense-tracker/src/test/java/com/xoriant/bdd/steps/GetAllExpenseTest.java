package com.xoriant.bdd.steps;

import com.xoriant.bdd.utils.FeatureUtils;
import com.xoriant.model.Expense;
import com.xoriant.model.ExpenseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAllExpenseTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<List<Expense>> response;
    private ExpenseDto expenseDto;
    Long id;

    @Given("the user does not provide any expense id")
    public void the_user_does_not_provide_any_expense_id() {
    }

    @When("the user sends a GET request to {string}")
    public void the_user_sends_a_get_request_to(String path) {
        response = restTemplate.exchange(
                FeatureUtils.URL + path, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Expense>>() {}
        );
        System.out.println("hello");
    }

    @Then("the user gets the response with status code {int} and list of expenses")
    public void the_user_gets_the_response_with_status_code_and_list_of_expenses(Integer statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
