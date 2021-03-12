package com.ossasteven.desafiospring.services;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.repository.ArticleRepository;
import com.ossasteven.desafiospring.repository.IArticleRepository;
import com.ossasteven.desafiospring.services.dependencies.GenerateTicket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.ossasteven.desafiospring.util.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void getArticles() throws NotFoundException, InvalidRequestParam {
        List<ArticleDTO> articlesFromJson  = readJsonArticles.loadDatabase();

        Mockito.when(repository.getAll()).thenReturn(articlesFromJson);

        List<ArticleDTO> articles = service.getArticles(new HashMap<>());

        Mockito.verify(repository, Mockito.atLeastOnce()).getAll();

        Assertions.assertIterableEquals(articlesFromJson, articles);
    }
}