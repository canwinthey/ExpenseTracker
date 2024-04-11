package com.xoriant.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
@Table(name="expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long costCode;
	private String description;
	private LocalDate expenseDate;
	private String state;
	private String location;
	private String category;
	private String currency;
	private BigDecimal amount;
	private BigDecimal currencyAmtInUSD;

	public Expense(Long id){
		this.id = id;
	}
}
