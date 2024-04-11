package com.xoriant.bdd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoriant.model.ExpenseDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class FeatureUtils {
    public static String URL = "http://localhost:8080";
    public static ExpenseDto getMockExpenseDto(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource(filename);
        try {
            return objectMapper.readValue(resource.getInputStream(), ExpenseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
