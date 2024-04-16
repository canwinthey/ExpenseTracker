package com.xoriant.model;


import com.xoriant.exception.ExpenseValidationException;
import com.xoriant.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ExpenseValidation {

    private List<Long> costCodeList = Arrays.asList(100L, 200L, 300L, 400L);
    @Value("${url.category}")
    private String categoryUrl;

    @Value("${url.location}")
    private String locationUrl;

    @Autowired
    private RestService restService;

    public boolean validate(ExpenseDto expenseDto) throws ExpenseValidationException {
        boolean valid = false;
        Long costCode = expenseDto.getCostCode();
        validateCostCode(costCode);
        validateAmount(expenseDto.getAmount());

        Category categoryObj = restService.getCategory(expenseDto.getCategoryId());
        String category = categoryObj.getName();
        LocationDto locationDto = restService.getLocation(expenseDto.getLocationId());
        String location = locationDto.getLocation();
        StringBuilder message = new StringBuilder("Allowed values for costCode: ");
        message.append(costCode);

        if(costCode == 100){
            if(category.equalsIgnoreCase("Travel")){
                if(location.equalsIgnoreCase("Los Angeles") || location.equalsIgnoreCase("San Francisco")){
                    valid = true;
                } else {
                    message.append(" - Category(Travel), Location(Los Angeles, San Francisco)");
                }
            } else if(category.equalsIgnoreCase("Entertainment")){
                if(location.equalsIgnoreCase("Philadelphia") || location.equalsIgnoreCase("Pittsburgh")){
                    valid = true;
                } else {
                    message.append(" - Category(Entertainment),   Location(Philadelphia, Pittsburgh)");
                }
            } else if(category.equalsIgnoreCase("Food")){
                if(location.equalsIgnoreCase("Houston") || location.equalsIgnoreCase("San Antonio")){
                    valid = true;
                } else {
                    message.append(" - Category(Food),   Location(Houston, San Antonio)");
                }
            }
        } else if(costCode == 200){
            if(category.equalsIgnoreCase("Travel")){
                if(location.equalsIgnoreCase("Denver") || location.equalsIgnoreCase("Colorado Springs")){
                    valid = true;
                } else {
                    message.append(" - Category(Travel), Location(Denver, Colorado Springs)");
                }
            } else if(category.equalsIgnoreCase("Entertainment")){
                if(location.equalsIgnoreCase("Atlanta") || location.equalsIgnoreCase("Columbus")){
                    valid = true;
                } else {
                    message.append(" - Category(Entertainment), Location(Atlanta, Columbus)");
                }
            } else if(category.equalsIgnoreCase("Food")){
                if(location.equalsIgnoreCase("Charlotte") || location.equalsIgnoreCase("Raleigh")){
                    valid = true;
                } else {
                    message.append(" - Category(Food), Location(Charlotte, Raleigh)");
                }
            }
        } else if(costCode == 300){
            if(category.equalsIgnoreCase("Travel")){
                if(location.equalsIgnoreCase("Phoenix") || location.equalsIgnoreCase("Tucson")){
                    valid = true;
                } else {
                    message.append(" - Category(Travel), Location(Phoenix, Tucson)");
                }
            } else if(category.equalsIgnoreCase("Entertainment")){
                if(location.equalsIgnoreCase("Huntsville") || location.equalsIgnoreCase("Birmingham")){
                    valid = true;
                } else {
                    message.append(" - Category(Entertainment), Location(Huntsville, Birmingham)");
                }
            } else if(category.equalsIgnoreCase("Food")){
                if(location.equalsIgnoreCase("Chicago") || location.equalsIgnoreCase("Aurora")){
                    valid = true;
                } else {
                    message.append(" - Category(Food), Location(Chicago, Aurora)");
                }
            }
        } else if(costCode == 400){
            if(category.equalsIgnoreCase("Travel")){
                if(location.equalsIgnoreCase("Jacksonville") || location.equalsIgnoreCase("Miami")){
                    valid = true;
                } else {
                    message.append(" - Category(Travel), Location(Jacksonville, Miami)");
                }
            } else if(category.equalsIgnoreCase("Entertainment")){
                if(location.equalsIgnoreCase("New York City") || location.equalsIgnoreCase("Buffalo")){
                    valid = true;
                } else {
                    message.append(" - Category(Entertainment), Location(New York City, Buffalo)");
                }
            } else if(category.equalsIgnoreCase("Food")){
                if(location.equalsIgnoreCase("Columbus") || location.equalsIgnoreCase("Cleveland")){
                    valid = true;
                } else {
                    message.append(" - Category(Food), Location(Columbus, Cleveland)");
                }
            }
        }
        if(valid == false){
            throw new ExpenseValidationException(message.toString());
        }
        return valid;
    }

    private void validateAmount(BigDecimal amount) {
        boolean validAmount = false;
        double doubleAmt = 0.0;
        if(amount == null) {
            throw new ExpenseValidationException("Amount is required.");
        } else {
            doubleAmt = amount.doubleValue();
        }

        if(doubleAmt <= 0) {
            throw new ExpenseValidationException("Amount should not be 0 or negative.");
        } else if(doubleAmt > 7000) {
            throw new ExpenseValidationException("Amount should not be greater than 7000.");
        }
    }

    private void validateCostCode(Long costCode) throws ExpenseValidationException {
        long count = 0L;
        if(costCode == 0){
            throw new ExpenseValidationException("CostCode is required.");
        } else if (!costCodeList.contains(costCode)) {
            throw new ExpenseValidationException("CostCode should be (100, 200, 300, 400).");
        }
    }
}
