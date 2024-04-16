package com.xoriant.tdd;

import com.xoriant.exception.ExpenseValidationException;
import com.xoriant.model.*;
import com.xoriant.service.RestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseValidationTest {
    @Mock
    private RestService restService;

    @InjectMocks
    private ExpenseValidation expenseValidation;

    private ExpenseDto expenseDto;
    private Category category;
    private LocationDto locationDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        expenseDto = new ExpenseDto();
        expenseDto.setCostCode(100L);
        expenseDto.setDescription("Lunch at RMall");
        expenseDto.setExpenseDate(LocalDate.now());
        expenseDto.setStateId(1L);
        expenseDto.setLocationId(1L);
        expenseDto.setCurrency("INR");
        expenseDto.setAmount(new BigDecimal("5000"));
        expenseDto.setCategoryId(1L);

        category = new Category();
        category.setId(1L);
        category.setName("Travel");

        locationDto = new LocationDto();
        locationDto.setStateId(1L);
        locationDto.setLocationId(1L);
        locationDto.setLocation("Los Angeles");
        locationDto.setState("California");
    }


    @Test
    void testValidate_Success() throws ExpenseValidationException {
        when(restService.getCategory(1L)).thenReturn(category);
        when(restService.getLocation(1L)).thenReturn(locationDto);
        assertTrue(expenseValidation.validate(expenseDto));
    }

    @Test
    void testValidate_Failure() throws ExpenseValidationException {
        LocationDto locationDto1 = new LocationDto();
        locationDto1.setStateId(1L);
        locationDto1.setLocationId(4L);
        locationDto1.setLocation("San Jose");
        locationDto1.setState("California");
        expenseDto.setLocationId(4L);
        when(restService.getCategory(1L)).thenReturn(category);
        when(restService.getLocation(4L)).thenReturn(locationDto1);
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("Allowed values for costCode: 100 - Category(Travel), Location(Los Angeles, San Francisco)", exception.getMessage());
        expenseDto.setLocationId(1L);
    }

    @Test
    void testValidate_InvalidCostCode() {
        expenseDto.setCostCode(500L);
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("CostCode should be (100, 200, 300, 400).", exception.getMessage());
    }

    @Test
    void testValidate_CostCode_Equals_ZERO() {
        expenseDto.setCostCode(0L);
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("CostCode is required.", exception.getMessage());
    }

    @Test
    void testValidate_Amount_Is_Null() {
        expenseDto.setAmount(null);
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("Amount is required.", exception.getMessage());
    }

    @Test
    void testValidate_Amount_Is_Zero() {
        expenseDto.setAmount(new BigDecimal(BigInteger.ZERO));
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("Amount should not be 0 or negative.", exception.getMessage());
    }

    @Test
    void testValidate_Amount_Is_Negative() {
        expenseDto.setAmount(new BigDecimal("-100"));
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("Amount should not be 0 or negative.", exception.getMessage());
    }

    @Test
    void testValidate_Amount_Is_G8_Than_7000() {
        expenseDto.setAmount(new BigDecimal("8000"));
        ExpenseValidationException exception = assertThrows(ExpenseValidationException.class,
                () -> expenseValidation.validate(expenseDto));
        assertEquals("Amount should not be greater than 7000.", exception.getMessage());
    }
}
