package com.xoriant.tdd;

import com.xoriant.exception.ExpenseValidationException;
import com.xoriant.model.*;
import com.xoriant.repository.ExpenseRepository;
import com.xoriant.service.CurrencyExchangeServiceProxy;
import com.xoriant.service.ExpenseService;
import com.xoriant.service.RestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseServiceTest {

    @Mock
    private ExpenseValidation expenseValidation;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @Mock
    private RestService restService;

    @InjectMocks
    private ExpenseService expenseService;

    private ExpenseDto expenseDto;
    private Expense expense;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        expenseDto = new ExpenseDto();
        expenseDto.setCostCode(100L);
        expenseDto.setDescription("Lunch at RMall");
        expenseDto.setExpenseDate(LocalDate.now());
        expenseDto.setStateId(1L);
        expenseDto.setLocationId(2L);
        expenseDto.setCurrency("INR");
        expenseDto.setAmount(new BigDecimal("5000"));
        expenseDto.setCategoryId(1L);

        expense = new Expense();
        expense.setCostCode(100L);
        expense.setDescription("Lunch at RMall");
        expense.setExpenseDate(LocalDate.now());
        expense.setCurrency("INR");
        expense.setAmount(new BigDecimal("5000"));
        expense.setCurrencyAmtInUSD(new BigDecimal("60.000"));

    }

    @Test
    public void testCreateExpense_Success() throws URISyntaxException, ExpenseValidationException {
        // Mocking
        ExchangeValue exchangeValue = new ExchangeValue();
        exchangeValue.setConversionMultiple(new BigDecimal("0.012"));
        when(expenseValidation.validate(any())).thenReturn(true);
        when(currencyExchangeServiceProxy.retrieveExchangeValue(any(), any())).thenReturn(exchangeValue);
        when(restService.getCategory(any())).thenReturn(new Category(1L, "Travel"));
        when(restService.getLocation(any())).thenReturn(
                new LocationDto(1L, 2L, "California", "San Jose"));
        when(expenseRepository.save(any())).thenReturn(expense);

        // Test
        Expense response = expenseService.createExpense(expenseDto);
        System.out.println(response.toString());

        // Assertions
        assertEquals(new BigDecimal("60.000"), response.getCurrencyAmtInUSD());
    }

    // Add similar tests for update, retrieval, and deletion scenarios

    // Add tests for boundary, negative, and error scenarios
}
