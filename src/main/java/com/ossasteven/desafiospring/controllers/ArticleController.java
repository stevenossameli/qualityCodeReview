package com.ossasteven.desafiospring.controllers;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ArticleController extends BaseController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles(@RequestParam HashMap<String, String> params) throws InvalidRequestParam, NotFoundException {
        return service.getArticles(params);
    }

    @PostMapping("/purchase-request")
    public ResponseEntity<Object> purchase(@RequestBody HashMap<String, List<ArticleDTO>> request,
                                           @RequestParam(required = false) Boolean shoppingCart,
                                           @RequestParam(required = false) Long clientId) throws StoreException{

        if (!request.containsKey("articles"))
            throw new InvalidRequestParam("unable to recognized the articles, be sure to send a valid articles list");
        return service.purchaseRequest(request.get("articles"), shoppingCart, clientId);
    }

    @PostMapping("/purchase-confirm/{ticketId}")
    public ResponseEntity<Object> confirmPurchase(@PathVariable Long ticketId) throws NotFoundException {

        return service.confirmPurchase(ticketId);
    }

}