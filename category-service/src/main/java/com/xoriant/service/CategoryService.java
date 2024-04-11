package com.xoriant.service;

import com.xoriant.model.Category;
import com.xoriant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<List<Category>> getAllCategories() {
        ResponseEntity<List<Category>> responseEntity = null;
        List<Category> list = categoryRepository.findAll();
        if(CollectionUtils.isEmpty(list)){
            responseEntity = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        }
        return responseEntity;
    }

    public ResponseEntity<Category> getCategory(Long id) {
        Category category = new Category();
        ResponseEntity<Category> responseEntity = null;
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if(categoryOpt.isPresent()){
            category = categoryOpt.get();
        }
        if(categoryOpt.isPresent()){
            category = categoryOpt.get();
            responseEntity = new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(category, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<Category> createCategory(Category category) throws URISyntaxException {
        Category result = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);
    }

    public ResponseEntity<Category> updateCategory(Category category) {
        Category result = categoryRepository.save(category);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<Category> deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
