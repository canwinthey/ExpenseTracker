package com.xoriant.model;

import java.math.BigDecimal;
import javax.persistence.*;

import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long costCode;
	private String description;
	private String expenseDate;
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
