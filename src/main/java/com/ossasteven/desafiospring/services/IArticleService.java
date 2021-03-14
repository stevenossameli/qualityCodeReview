package com.ossasteven.desafiospring.services;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;


public interface IArticleService {

    List<ArticleDTO> getArticles(HashMap<String, String> params) throws InvalidRequestParam, NotFoundException;

    ResponseEntity<Object> purchaseRequest(List<ArticleDTO> articles, Boolean shoppingCart, Long clientID) throws StoreException;

    ResponseEntity<Object> confirmPurchase(Long ticketId) throws NotFoundException;

}
