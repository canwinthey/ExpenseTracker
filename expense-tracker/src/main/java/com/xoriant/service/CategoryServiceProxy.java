package com.xoriant.service;

import com.xoriant.model.Category;
import com.xoriant.model.ExchangeValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "category-service")
public interface CategoryServiceProxy {

	@GetMapping("/category/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable Long id);

}