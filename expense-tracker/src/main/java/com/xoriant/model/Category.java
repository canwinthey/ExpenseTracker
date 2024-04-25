package com.xoriant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category {
	private Long id;
	private String name;

	public Category(Long id, String name){
		this.id = id;
		this.name = name;
	}
}
