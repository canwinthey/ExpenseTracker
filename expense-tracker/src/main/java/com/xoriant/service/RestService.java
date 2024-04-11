package com.xoriant.service;

import com.xoriant.model.*;
import com.xoriant.utils.ExpenseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Value("${url.category}")
    private String categoryUrl;

    @Value("${url.location}")
    private String locationUrl;

    @Value("${url.currency}")
    private String currencyUrl;


    public Category getCategory(Long categoryId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = ExpenseUtils.getHttpEntity();
            String url = categoryUrl + categoryId;
            System.out.println(url);
            ResponseEntity<Category> categoryEntity =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Category.class);
            return categoryEntity.getBody();
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return new Category();
    }

    public LocationDto getLocation(Long locationId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = ExpenseUtils.getHttpEntity();

            String url = locationUrl + locationId;
            System.out.println(url);
            ResponseEntity<LocationDto> locationEntity =
                    restTemplate.exchange(url, HttpMethod.GET, entity, LocationDto.class);
            return locationEntity.getBody();
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return new LocationDto();
    }

    public ExchangeValue getCurrencyExchange(String from, String to){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = ExpenseUtils.getHttpEntity();
            StringBuilder sb = new StringBuilder(currencyUrl);
            sb.append("from/").append(from).append("/to/").append(to);
            System.out.println(sb.toString());
            ResponseEntity<ExchangeValue> exchangeEntity =
                    restTemplate.getForEntity(sb.toString(), ExchangeValue.class);
            return exchangeEntity.getBody();
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return new ExchangeValue();
    }
}
