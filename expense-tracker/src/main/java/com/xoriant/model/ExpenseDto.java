package com.xoriant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xoriant.utils.LocalDateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class ExpenseDto {

	private Long id;
	private Long costCode;
	private String description;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate expenseDate;
	private Long stateId;
	private Long locationId;
	private String currency;
	private BigDecimal amount;
	private BigDecimal currencyAmtInUSD;
	private Long categoryId;

}
