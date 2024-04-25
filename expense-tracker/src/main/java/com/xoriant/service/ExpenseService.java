package com.xoriant.service;

import com.xoriant.exception.ExpenseValidationException;
import com.xoriant.model.*;
import com.xoriant.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class ExpenseService {
    private static final String USD = "USD";
    Logger logger = Logger.getLogger("ExpenseService");
    @Autowired
    private RestService restService;
    @Autowired
    private ExpenseValidation expenseValidation;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    public Expense createExpense(ExpenseDto expenseDto) throws URISyntaxException, ExpenseValidationException {
        if (!expenseValidation.validate(expenseDto)) {
            logger.info("Validation Failed!!!");
        }
        Expense expense = new Expense();
        ExchangeValue exchangeValue = currencyExchangeServiceProxy.retrieveExchangeValue(expenseDto.getCurrency(), USD);
        if (exchangeValue != null) {
            BigDecimal currencyAmtInUSD = exchangeValue.getConversionMultiple().multiply(expenseDto.getAmount());
            expenseDto.setCurrencyAmtInUSD(currencyAmtInUSD);
            logger.info("currencyAmtInUSD: " + currencyAmtInUSD);
        }
        populateCategory(expenseDto.getCategoryId(), expense);
        populateLocation(expenseDto.getLocationId(), expense);
        populateExpense(expenseDto, expense);
        return expenseRepository.save(expense);
    }

    private void populateExpense(ExpenseDto expenseDto, Expense expense) {
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setCurrencyAmtInUSD(expenseDto.getCurrencyAmtInUSD());
        expense.setCurrency(expenseDto.getCurrency());
        expense.setAmount(expenseDto.getAmount());
        expense.setDescription(expenseDto.getDescription());
        expense.setCostCode(expenseDto.getCostCode());
    }

    private void populateCategory(Long categoryId, Expense expense) {
        Category category = restService.getCategory(categoryId);
        if (category != null) {
            expense.setCategory(category.getName());
            logger.info("category: " + category.getName());
        }
    }

    private void populateLocation(Long locationId, Expense expense) {
        LocationDto locationDto = restService.getLocation(locationId);
        if (locationDto != null) {
            expense.setLocation(locationDto.getLocation());
            expense.setState(locationDto.getState());
        }
    }

    public ResponseEntity<List<Expense>> getExpenses() {
        ResponseEntity<List<Expense>> responseEntity = null;
        List<Expense> list = expenseRepository.findAll();
        if (CollectionUtils.isEmpty(list)) {
            responseEntity = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        }
        return responseEntity;
    }

    public ResponseEntity<Expense> deleteExpense(Long id) {
        try {
            expenseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex){
            logger.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Expense> getExpense(Long id) {
        ResponseEntity<Expense> responseEntity = null;
        Expense expense = new Expense();
        Optional<Expense> expenseOpt = expenseRepository.findById(id);
        if (expenseOpt.isPresent()) {
            expense = expenseOpt.get();
            responseEntity = new ResponseEntity<>(expense, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(expense, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}