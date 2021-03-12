package com.ossasteven.desafiospring.validation;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class PurchaseValidation {

    public void checkStock(ArticleDTO article, ArticleDTO found) throws NotFoundException {
        if (article.getQuantity() > found.getQuantity())
            throw new NotFoundException("Sorry, we don't have enough " + article.getName() + " in stock to fulfil your order");
    }


    public boolean validateFields(ArticleDTO request, ArticleDTO found) throws InvalidRequestParam {
        boolean v;

        if (request.getName() != null) {
            v = request.getName().equalsIgnoreCase(found.getName());
            if (!v)
                throw new InvalidRequestParam("the product name " + request.getName() + " does not match with the id " + found.getId());
        } else {
            request.setName(found.getName());
        }

        if (request.getTradeMark() != null) {
            v = request.getTradeMark().equalsIgnoreCase(found.getTradeMark());
            if (!v)
                throw new InvalidRequestParam("the product trade mark " + request.getTradeMark() + " does not match with the id " + found.getId());
        } else {
            request.setTradeMark(found.getTradeMark());
        }

        if (request.getQuantity() == null)
            request.setQuantity(1);

        request.setPrice(found.getPrice());

        return true;

    }
}
