package com.xoriant.service;

import com.xoriant.model.*;
import com.xoriant.utils.ExpenseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Autowired
    private CategoryServiceProxy categoryServiceProxy;
    @Autowired
    private LocationServiceProxy locationServiceProxy;

    public Category getCategory(Long id){
        Category category = new Category();
        try {
            ResponseEntity<Category> categoryEntity = categoryServiceProxy.getCategory(id);
            if(categoryEntity != null) {
                category = categoryEntity.getBody();
            }
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return category;
    }

    public LocationDto getLocation(Long locationId){
        LocationDto locationDto = new LocationDto();
        try {
            ResponseEntity<LocationDto> locationEntity = locationServiceProxy.getLocationById(locationId);
            if(locationEntity != null) {
                locationDto = locationEntity.getBody();
            }
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return locationDto;
    }
/*
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
 */

}
