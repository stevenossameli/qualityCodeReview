package com.ossasteven.desafiospring.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.model.TicketDTO;
import com.ossasteven.desafiospring.repository.IArticleRepository;
import com.ossasteven.desafiospring.services.dependencies.GenerateTicket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.ossasteven.desafiospring.util.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

class ArticleServiceTest {

    @Mock
    private IArticleRepository<ArticleDTO> repository;

    @Mock
    private GenerateTicket ticket;

    ArticleService service;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        service = new ArticleService(repository, ticket);
    }

    @Test
    @DisplayName("Get All the articles")
    void getArticles() throws NotFoundException, InvalidRequestParam {

        //Arrange
        List<ArticleDTO> expected  = readJsonArticles.loadDatabase();
        Mockito.when(repository.getAll()).thenReturn(expected);


        // Act
        List<ArticleDTO> actual = service.getArticles(new HashMap<>());


        // Assert
        Mockito.verify(repository, Mockito.atLeastOnce()).getAll();

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void purchaseRequest() throws StoreException {

        // Arrange
        List<ArticleDTO> articleDTOS  = GetArticles.getFourMocks();
        TicketDTO expected = new TicketDTO();
        expected.setId(1L);
        expected.setArticles(articleDTOS);
        expected.setTotal(0D);
        Mockito.when(ticket.generateTicket(articleDTOS)).thenReturn(expected);

        // Act
        ResponseEntity<Object> res = service.purchaseRequest(articleDTOS, null, null);

        // Assert
        HashMap<String, Object> keyValue = (HashMap<String, Object>) res.getBody();
        TicketDTO actual = (TicketDTO) keyValue.get("ticket");
        Assertions.assertEquals(expected, actual);
    }

}