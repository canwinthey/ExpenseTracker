package com.xoriant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ExpenseDto {

	private Long id;
	private Long costCode;
	private String description;
	private String expenseDate;
	private Long stateId;
	private Long locationId;
	private String currency;
	private BigDecimal amount;
	private BigDecimal currencyAmtInUSD;
	private Long categoryId;

}
