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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseServiceTest {

    @Mock
    private ExpenseValidation expenseValidation;

    @Mock
    private ExpenseRepository expenseRepository;

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
        when(restService.getCurrencyExchange(any(), any())).thenReturn(exchangeValue);
        when(restService.getCategory(any())).thenReturn(new Category(1L, "Travel"));
        when(restService.getLocation(any())).thenReturn(
                new LocationDto(1L, 2L, "California", "San Jose"));
        when(expenseRepository.save(any())).thenReturn(expense);

        // Test
        Expense response = expenseService.createExpense(expenseDto);
        System.out.println(response.toString());
        verify(expenseRepository, times(1)).save(any());
        // Assertions
        assertEquals(new BigDecimal("60.000"), response.getCurrencyAmtInUSD());
    }

    @Test
    void testGetExpenses_Empty() {
        List<Expense> expenses = new ArrayList<>();
        when(expenseRepository.findAll()).thenReturn(expenses);

        ResponseEntity<List<Expense>> responseEntity = expenseService.getExpenses();

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    void testGetExpenses_Not_Empty() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense);
        when(expenseRepository.findAll()).thenReturn(expenses);

        ResponseEntity<List<Expense>> responseEntity = expenseService.getExpenses();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    void testDeleteExpense() {
        Long id = 1L;
        doNothing().when(expenseRepository).deleteById(id);

        ResponseEntity<Expense> responseEntity = expenseService.deleteExpense(id);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testGetExpense_Ok() {
        Long id = 1L;
        Optional<Expense> optionalExpense = Optional.of(expense);
        when(expenseRepository.findById(id)).thenReturn(optionalExpense);

        ResponseEntity<Expense> responseEntity = expenseService.getExpense(id);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testGetExpense_Not_Found() {
        Long id = 100L;
        Optional<Expense> optionalExpense = Optional.empty();
        when(expenseRepository.findById(id)).thenReturn(optionalExpense);

        ResponseEntity<Expense> responseEntity = expenseService.getExpense(id);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}
