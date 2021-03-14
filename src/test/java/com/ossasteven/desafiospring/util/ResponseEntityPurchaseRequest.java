package com.ossasteven.desafiospring.util;

import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ResponseEntityPurchaseRequest {

    public static ResponseEntity<Object> getPurchaseRequestResponse(List<ArticleDTO> articles) throws NotFoundException {

        List<ArticleDTO> db = GetArticles.getFourMocks();

        if(articles==null)
            return null;


        List<ArticleDTO> res = new ArrayList<>();

        for (ArticleDTO article :
                articles) {
            Optional<ArticleDTO> op =(db.stream().filter(ArticleDTO -> ArticleDTO.getId().equals(article.getId())).findFirst());

            if(op.isPresent()){
                res.add(article);
            }
        }

        if(res.isEmpty())
            throw new NotFoundException("product not found");

        HashMap<Object, Object> body = new HashMap<>();

        body.put("ticket", "A9RCLKOFKJ8029JCK");
        body.put("articles", res);
        body.put("statusCode", HttpStatus.OK.value());
        body.put("mensaje", "La solicitud de compra se completó con éxito");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}