package com.ossasteven.desafiospring.services;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.model.ShoppingCartDTO;
import com.ossasteven.desafiospring.model.TicketDTO;
import com.ossasteven.desafiospring.repository.IArticleRepository;
import com.ossasteven.desafiospring.services.dependencies.IGenerateTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;


@Service
public class ArticleService implements IArticleService {

    IArticleRepository<ArticleDTO> repository;
    IGenerateTicket<TicketDTO, ShoppingCartDTO, ArticleDTO> ticketGenerator;


    @Autowired
    public ArticleService(IArticleRepository<ArticleDTO> repository, IGenerateTicket<TicketDTO, ShoppingCartDTO, ArticleDTO> ticketGenerator) {
        this.repository = repository;
        this.ticketGenerator = ticketGenerator;
    }

    @Override
    public List<ArticleDTO> getArticles(@RequestParam(required = false) HashMap<String, String> params) throws InvalidRequestParam, NotFoundException {
        if (params.isEmpty())
            return repository.getAll();
        return repository.getAll(params);
    }

    @Override
    public ResponseEntity<Object> purchaseRequest(List<ArticleDTO> articles, Boolean isCart, Long clientId) throws StoreException {
        HashMap<Object, Object> body = new HashMap<>();

        body.put("statusCode", HttpStatus.OK.value());


        if (isCart == null || !isCart) {
            TicketDTO ticket = ticketGenerator.generateTicket(articles);
            if (ticket != null) {
                body.put("ticket", ticket);
                body.put("mensaje", "La solicitud de compra se completó con éxito");
                return new ResponseEntity<>(body, HttpStatus.OK);
            }
        }


        body.put("ShoppingCart", ticketGenerator.generateShoppingCart(articles, clientId));
        body.put("mensaje", "Producto(s) añadido(s) al carrito de compra exitosamente ");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> confirmPurchase(Long ticketId) throws NotFoundException {

        Double totalTicket = ticketGenerator.confirmPurchase(ticketId);

        if (totalTicket == null)
            throw new NotFoundException("the ticket with the id " + ticketId + " does not exists");

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Purchase completed successfully");
        response.put("total price", "" + totalTicket);
        response.put("Status Code", "" + HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);


    }
}