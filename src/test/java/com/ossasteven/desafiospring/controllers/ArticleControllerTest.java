package com.ossasteven.desafiospring.controllers;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.services.ArticleService;
import com.ossasteven.desafiospring.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class ArticleControllerTest {

    @Mock
    private ArticleService service;

    ArticleController controller;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new ArticleController(service);
    }


    //testing get all without filter
    @Test()
    void getArticles() throws NotFoundException, InvalidRequestParam {

        List<ArticleDTO> expected = GetArticles.getFourMocks();

        when(service.getArticles(new HashMap<>())).thenReturn(expected);

        List<ArticleDTO> actual = controller.getArticles(new HashMap<>());

        verify(service, atLeast(1)).getArticles(new HashMap<>());
        assertIterableEquals(expected, actual);
    }

    //testing get all by category
    @Test
    void getArticlesByCategory() throws NotFoundException, InvalidRequestParam {

        List<ArticleDTO> expected = GetArticles.getFourMocks();
        expected = GetArticles.getByCategory("herramientas", expected);

        HashMap<String, String> params = new HashMap<>();
        params.put("category", "herramientas");
        when(service.getArticles(params)).thenReturn(expected);

        List<ArticleDTO> actual = controller.getArticles(params);

        verify(service, atLeast(1)).getArticles(params);
        assertIterableEquals(expected, actual);

    }

    // testing Category and sendFree

    @Test
    void testFilterCombination() throws NotFoundException, InvalidRequestParam {
        List<ArticleDTO> expected = GetArticles.getEightMocks();
        expected = GetArticles.getByCategory("herramientas", expected);
        expected = GetArticles.getBySendFree(true, expected);

        HashMap<String, String> params = new HashMap<>();
        params.put("category", "herramientas");
        params.put("sendFree", "true");

        when(service.getArticles(params)).thenReturn(expected);
        List<ArticleDTO> actual = controller.getArticles(params);

        verify(service, atLeast(1)).getArticles(params);
        assertIterableEquals(expected, actual);

    }


    // testing order ASC
    @Test
    void testAscendingOrder() throws NotFoundException, InvalidRequestParam {
        List<ArticleDTO> expected = GetArticles.getFourMocks();

        Comparator<ArticleDTO> comparator = (a,b) -> a.getName().compareToIgnoreCase(b.getName());
        GetArticles.sortList(expected, comparator);

        HashMap<String, String> params = new HashMap<>();
        params.put("order", "0");

        when(service.getArticles(params)).thenReturn(expected);

        List<ArticleDTO> actual = controller.getArticles(params);

        verify(service, atLeast(1)).getArticles(params);
        assertIterableEquals(expected, actual);

    }

    // testing order DESC
    @Test
    void testDescendingOrder() throws NotFoundException, InvalidRequestParam {
        List<ArticleDTO> expected = GetArticles.getFourMocks();

        Comparator<ArticleDTO> comparator = (a,b) -> b.getName().compareToIgnoreCase(a.getName());
        GetArticles.sortList(expected, comparator);

        HashMap<String, String> params = new HashMap<>();
        params.put("order", "1");

        when(service.getArticles(params)).thenReturn(expected);

        List<ArticleDTO> actual = controller.getArticles(params);

        verify(service, atLeast(1)).getArticles(params);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testAscendingPriceOrder() throws NotFoundException, InvalidRequestParam {

        List<ArticleDTO> expected = GetArticles.getFourMocks();

        Comparator<ArticleDTO> comparator = (a,b) -> b.getPrice().compareTo(a.getPrice());
        GetArticles.sortList(expected, comparator);

        HashMap<String, String> params = new HashMap<>();
        params.put("order", "2");

        when(service.getArticles(params)).thenReturn(expected);

        List<ArticleDTO> actual = controller.getArticles(params);

        verify(service, atLeast(1)).getArticles(params);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testDescendingPriceOrder() throws NotFoundException, InvalidRequestParam {

        List<ArticleDTO> expected = GetArticles.getFourMocks();

        Comparator<ArticleDTO> comparator = Comparator.comparing(ArticleDTO::getPrice);
        GetArticles.sortList(expected, comparator);

        HashMap<String, String> params = new HashMap<>();
        params.put("order", "3");

        when(service.getArticles(params)).thenReturn(expected);

        List<ArticleDTO> actual = controller.getArticles(params);

        verify(service, atLeast(1)).getArticles(params);
        assertIterableEquals(expected, actual);
    }

    @Test
    void purchase() throws StoreException {

        ArticleDTO martillo = new ArticleDTO();
        martillo.setId(1L);
        martillo.setName("Martillo de hierro");
        martillo.setQuantity(4);

        ArticleDTO llaveInglesa = new ArticleDTO();
        llaveInglesa.setId(2L);
        llaveInglesa.setName("Llave Inglesa roja");
        llaveInglesa.setCategory("herramientas");
        llaveInglesa.setQuantity(4);

        List<ArticleDTO> articles = Arrays.asList(martillo, llaveInglesa);
        HashMap<String, List<ArticleDTO>> payLoad = new HashMap<>();

        payLoad.put("articles", articles);

        when(service.purchaseRequest(payLoad.get("articles"), null, null)).thenReturn(new ResponseEntity<>(articles, HttpStatus.OK));

        ResponseEntity<Object> expected = new ResponseEntity<>(articles, HttpStatus.OK);

        ResponseEntity<Object> actual = controller.purchase(payLoad, null, null);

        verify(service, atLeastOnce()).purchaseRequest(articles, null, null);
        Assertions.assertEquals(expected, actual);


    }
}
