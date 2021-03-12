package com.ossasteven.desafiospring.validation;

import com.ossasteven.desafiospring.dao.IArticleDAO;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilterValidation {

    @Autowired
    IArticleDAO dao;

    public void filterValidator(String filterName, String value) throws InvalidRequestParam, NotFoundException {

        if (filterName.equalsIgnoreCase("category") && !dao.getCategories().contains(value.toLowerCase()))
            throw new NotFoundException(value + " category not found in our database");

        if (filterName.equalsIgnoreCase("tradeMark") && !dao.getTradeMarks().contains(value.toLowerCase()))
            throw new NotFoundException(value + " Trade Mark not found in our database");

        if (filterName.equalsIgnoreCase("productName") && !dao.getProductNames().contains(value.toLowerCase()))
            throw new NotFoundException(value + " product not found");

        if (filterName.equalsIgnoreCase("sendFree") && !value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false"))
            throw new InvalidRequestParam(value + " not valid as a filter value ");

        if (filterName.equalsIgnoreCase("prestige") || filterName.equalsIgnoreCase("order")) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                throw new InvalidRequestParam(" the value " + value + " is not a number");
            }
        }
    }
}