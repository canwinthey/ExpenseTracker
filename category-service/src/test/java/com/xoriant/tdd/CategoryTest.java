package com.xoriant.tdd;

import com.xoriant.model.Category;
import com.xoriant.repository.CategoryRepository;
import com.xoriant.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCategories_WhenCategoriesExist() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Travel"));
        categories.add(new Category(2L, "Entertainment"));
		categories.add(new Category(3L, "Food"));
        when(categoryRepository.findAll()).thenReturn(categories);

        ResponseEntity<List<Category>> responseEntity = categoryService.getAllCategories();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categories, responseEntity.getBody());
    }

    @Test
    void testGetAllCategories_WhenNoCategoriesExist() {
        List<Category> categories = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categories);

        ResponseEntity<List<Category>> responseEntity = categoryService.getAllCategories();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(categories, responseEntity.getBody());
    }

    @Test
    void testGetCategory_WhenCategoryExists() {
        Category category = new Category(1L, "Travel");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        ResponseEntity<Category> responseEntity = categoryService.getCategory(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category.getName(), responseEntity.getBody().getName());
    }

    @Test
    void testGetCategory_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(4L)).thenReturn(Optional.empty());

        ResponseEntity<Category> responseEntity = categoryService.getCategory(4L);

        System.out.println("testGetCategory_WhenCategoryDoesNotExist: getStatusCode(): " + responseEntity.getStatusCode());
        System.out.println("testGetCategory_WhenCategoryDoesNotExist: responseBody(): " + responseEntity.getBody());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody().getName());
    }

    @Test
    void testCreateCategory() throws URISyntaxException {
        Category category = new Category(5L, "Business");
        when(categoryRepository.save(category)).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryService.createCategory(category);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    void testUpdateCategory() {
        Category category = new Category(1L, "Travel 1");
        when(categoryRepository.save(category)).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryService.updateCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1L);

        ResponseEntity<Category> responseEntity = categoryService.deleteCategory(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }
}
