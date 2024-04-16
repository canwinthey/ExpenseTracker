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


@Service
public class ExpenseService {
    private static final String USD = "USD";

    @Autowired
    private RestService restService;
    @Autowired
    private ExpenseValidation expenseValidation;
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense createExpense(ExpenseDto expenseDto) throws URISyntaxException, ExpenseValidationException {
        if (!expenseValidation.validate(expenseDto)) {
            System.out.println("Validation Failed!!!");
        }
        Expense expense = new Expense();
        ExchangeValue exchangeValue = restService.getCurrencyExchange(expenseDto.getCurrency(), USD);
        if (exchangeValue != null) {
            BigDecimal currencyAmtInUSD = exchangeValue.getConversionMultiple().multiply(expenseDto.getAmount());
            expenseDto.setCurrencyAmtInUSD(currencyAmtInUSD);
            System.out.println("currencyAmtInUSD: " + currencyAmtInUSD);
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
            System.out.println("category: " + category.getName());
        }
    }

    private void populateLocation(Long locationId, Expense expense) {
        LocationDto locationDto = restService.getLocation(locationId);
        if (locationDto != null) {
            expense.setLocation(locationDto.getLocation());
            expense.setState(locationDto.getState());
        }
    }


    public Expense updateExpense(ExpenseDto expenseDto) throws URISyntaxException {
        if (expenseDto.getId() == 0) {
            return null;
        }
        if (!expenseValidation.validate(expenseDto)) {
            // Throw exception
        }
        return createExpense(expenseDto);
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
            System.out.println(ex.getMessage());
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