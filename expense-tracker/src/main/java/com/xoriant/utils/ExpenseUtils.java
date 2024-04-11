package com.xoriant.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class ExpenseUtils {

    public static HttpEntity getHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(headers);
    }
}
