package com.ossasteven.desafiospring.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.services.ArticleService;
import com.ossasteven.desafiospring.util.GetArticles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService service;


    @Test
    public void verifyGetAll() throws Exception {

        List<ArticleDTO> expected = GetArticles.getFourMocks();

        Mockito.when(service.getArticles(new HashMap<>())).thenReturn(expected);

       MvcResult result =  mockMvc.perform(get("/api/v1/articles"))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        Type listType = new TypeToken<List<ArticleDTO>>(){}.getType();
        List<ArticleDTO> response = new Gson().fromJson(responseBody, listType );

        Mockito.verify(service, Mockito.atLeastOnce()).getArticles(new HashMap<>());
        Assertions.assertIterableEquals(expected, response);
    }
}