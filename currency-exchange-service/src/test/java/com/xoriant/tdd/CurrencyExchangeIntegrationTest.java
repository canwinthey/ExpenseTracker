package com.xoriant.tdd;

import com.xoriant.model.ExchangeValue;
import com.xoriant.repository.ExchangeValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyExchangeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeValueRepository exchangeValueRepository;

    @BeforeEach
    void setUp() {
        ExchangeValue exchangeValue = new ExchangeValue(1L, "INR", "USD", BigDecimal.valueOf(0.012));
        when(exchangeValueRepository.findByFromAndTo("INR", "USD")).thenReturn(exchangeValue);
    }

    @Test
    void testRetrieveExchangeValue_WhenExchangeValueExists() throws Exception {
        mockMvc.perform(get("/currency-exchange/from/INR/to/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.from").value("INR"))
                .andExpect(jsonPath("$.to").value("USD"))
                .andExpect(jsonPath("$.conversionMultiple").value(0.012));
    }

    /*
    @Test
    void testRetrieveExchangeValue_WhenExchangeValueDoesNotExist() throws Exception {
        when(exchangeValueRepository.findByFromAndTo(any(), any())).thenReturn(null);
        System.out.println("ONE");
        // Perform the GET request and expect an Internal Server Error
        MvcResult mvcResult = mockMvc.perform(get("/currency-exchange/from/SPX/to/USD")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError())
                .andReturn();

        System.out.println("mvcResult: " + mvcResult.getResponse());
        // Use try-catch to handle the exception
        try {
            throw mvcResult.getResolvedException();
        } catch (NestedServletException nestedServletException) {
            // Extract the nested RuntimeException and verify the exception message
            RuntimeException runtimeException = (RuntimeException) nestedServletException.getCause();
            assertEquals("Unable to find data to convert SPX to USD", runtimeException.getMessage());
        }
    }
    */
}
