package com.xoriant.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.xoriant.model.ExpenseDto;
import com.xoriant.service.ExpenseService;
import com.xoriant.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

	private ExpenseService expenseService;
	@Autowired
	public ExpenseController(ExpenseService expenseService){
		this.expenseService = expenseService;
	}

	@GetMapping
	public ResponseEntity<List<Expense>> getExpenses(){
		return expenseService.getExpenses();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Expense>  getExpenseById(@PathVariable Long id){
		return expenseService.getExpense(id);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Expense> deleteExpense(@PathVariable Long id){
		return expenseService.deleteExpense(id);
	}
	
	@PostMapping
	public ResponseEntity<Expense> createExpense(@RequestBody ExpenseDto expenseDto) throws URISyntaxException{
		Expense result = expenseService.createExpense(expenseDto);
		return ResponseEntity.created(new URI("/expenses" + result.getId())).body(result);
	}

	@PutMapping
	public ResponseEntity<Expense> editExpense(@RequestBody ExpenseDto expenseDto) throws URISyntaxException{
		Expense result = expenseService.updateExpense(expenseDto);
		return ResponseEntity.created(new URI("/expenses" + result.getId())).body(result);
	}
}
