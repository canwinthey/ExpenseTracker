package com.xoriant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xoriant.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{
	public Category findByName(String name);
}
