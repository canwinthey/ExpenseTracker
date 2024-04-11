package com.xoriant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xoriant.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
	
}
