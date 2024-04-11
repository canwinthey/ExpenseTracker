package com.xoriant.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="category")
public class Category {
	@Id
	private Long id;
	private String name;

	public Category(Long id, String name){
		this.id = id;
		this.name = name;
	}
}
