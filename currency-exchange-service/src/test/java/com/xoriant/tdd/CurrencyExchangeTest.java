package com.xoriant.tdd;

import com.xoriant.controller.CurrencyExchangeController;
import com.xoriant.model.ExchangeValue;
import com.xoriant.repository.ExchangeValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CurrencyExchangeTest {

    @Mock
    private ExchangeValueRepository repository;

    @InjectMocks
    private CurrencyExchangeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRetrieveExchangeValue_WhenExchangeValueExists() {
        String fromCurrency = "INR";
        String toCurrency = "USD";
        BigDecimal conversionMultiple = BigDecimal.valueOf(0.012);
        ExchangeValue exchangeValue = new ExchangeValue(1L, fromCurrency, toCurrency, conversionMultiple);
        when(repository.findByFromAndTo(fromCurrency, toCurrency)).thenReturn(exchangeValue);

        ExchangeValue result = controller.retrieveExchangeValue(fromCurrency, toCurrency, new HashMap<>());

        assertNotNull(result);
        assertEquals(exchangeValue, result);
    }
/*
    @Test
    void testRetrieveExchangeValue_WhenExchangeValueDoesNotExist() {
        String fromCurrency = "SGX";
        String toCurrency = "USD";
        when(repository.findByFromAndTo(fromCurrency, toCurrency))
                .thenThrow(RuntimeException.class)

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.retrieveExchangeValue(fromCurrency, toCurrency, new HashMap<>()));

        assertEquals("Unable to find data to convert " + fromCurrency + " to " + toCurrency, exception.getMessage());
    }
*/
    // Add more test cases for boundary, negative, and error scenarios as required
}
