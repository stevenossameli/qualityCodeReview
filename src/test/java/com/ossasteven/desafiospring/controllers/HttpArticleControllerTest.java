package com.ossasteven.desafiospring.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.services.ArticleService;
import com.ossasteven.desafiospring.util.GetArticles;
import com.ossasteven.desafiospring.util.ResponseEntityPurchaseRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArticleController.class)
public class HttpArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService service;


    @Test
    public void verifyGetAll() throws Exception {


        // Given
        List<ArticleDTO> expected = GetArticles.getFourMocks();
        Mockito.when(service.getArticles(new HashMap<>())).thenReturn(expected);

        // When
        MvcResult result = mockMvc.perform(get("/api/v1/articles"))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();


        // Then
        String responseBody = result.getResponse().getContentAsString();
        List<ArticleDTO> res = new ObjectMapper().readValue(responseBody, new TypeReference<List<ArticleDTO>>() {});
        Mockito.verify(service, Mockito.atLeastOnce()).getArticles(new HashMap<>());
        Assertions.assertIterableEquals(expected, res);
    }

    @Test
    public void verifyPurchaseRequest() throws Exception {

        // Given
        ArticleDTO martillo = new ArticleDTO();
        martillo.setId(1L);
        martillo.setName("Martillo de hierro");
        martillo.setQuantity(4);

        ArticleDTO llaveInglesa = new ArticleDTO();
        llaveInglesa.setId(2L);
        llaveInglesa.setName("Llave Inglesa roja");
        llaveInglesa.setQuantity(4);

        List<ArticleDTO> expected = Arrays.asList(martillo, llaveInglesa);
        ResponseEntity<Object> expectedResponse = ResponseEntityPurchaseRequest.getPurchaseRequestResponse(expected);
        Mockito.when(service.purchaseRequest(expected, null, null)).thenReturn(expectedResponse);
        HashMap<String, List<ArticleDTO>> payLoad = new HashMap<>();
        payLoad.put("articles", expected);
        String json = new ObjectMapper().writeValueAsString(payLoad);


        // When
        MvcResult result = mockMvc.perform(post("/api/v1/purchase-request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // Then
        TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {};
        HashMap<String, Object> actualRes = new ObjectMapper().readValue(result.getResponse().getContentAsString(), type);
        String articlesJson = new ObjectMapper().writeValueAsString(actualRes.get("articles"));
        List<ArticleDTO> actual = new ObjectMapper().readValue(articlesJson, new TypeReference<List<ArticleDTO>>(){});

        Mockito.verify(service, Mockito.atLeastOnce()).purchaseRequest(expected, null, null);

        Assertions.assertIterableEquals(expected, actual);

    }
}