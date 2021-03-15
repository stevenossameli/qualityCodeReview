package com.ossasteven.desafiospring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.model.ClientDTO;
import com.ossasteven.desafiospring.services.ClientService;
import com.ossasteven.desafiospring.util.ResponseEntityMapper;
import com.ossasteven.desafiospring.util.readJsonArticles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
class HttpClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientService service;


    @Test
    void getClients() throws Exception {

        // Arrange

        List<ClientDTO> expected = readJsonArticles.loadClientsDB();
        Mockito.when(service.getAll(null)).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/clients")).andExpect(status().isOk()).andReturn();

        // Assert
        List<ClientDTO> actual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<ClientDTO>>() {});
        Mockito.verify(service, Mockito.atLeastOnce()).getAll(null);
        Assertions.assertIterableEquals(expected, actual);

    }

    @Test
    void createtClient() throws Exception {

        // Arrange
        ClientDTO newClient = new ClientDTO();
        newClient.setClientId(1L);
        newClient.setFullName("Spring user");
        newClient.setEmail("springuser@javadoc.com");
        newClient.setProvincia("california");
        newClient.setProvincia(".++*4iBsuUdj#");

        Mockito.when(service.createClient(newClient)).thenReturn(new ResponseEntity<>("Client created successfully", HttpStatus.OK));


        // Act
        String json = new ObjectMapper().writeValueAsString(newClient);
        MvcResult result = mockMvc.perform(post("/api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()).andReturn();

        // Assert
        Mockito.verify(service, Mockito.atLeastOnce()).createClient(newClient);
        Assertions.assertEquals("Client created successfully", result.getResponse().getContentAsString());

    }

    @Test
    void checkClientAlreadyExists() throws Exception {

        // Arrange
        ClientDTO newClient = new ClientDTO();
        newClient.setClientId(1L);
        newClient.setFullName("Spring user");
        newClient.setEmail("springuser@javadoc.com");
        newClient.setProvincia("california");
        newClient.setProvincia(".++*4iBsuUdj#");
        Mockito.when(service.createClient(newClient)).thenThrow(new AlreadyExistsException("The client with the email springuser@javadoc.com already exists"));


        // Act
        String json = new ObjectMapper().writeValueAsString(newClient);
        MvcResult result = mockMvc.perform(post("/api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError()).andReturn();

        // Assert
        Mockito.verify(service, Mockito.atLeastOnce()).createClient(newClient);
        HashMap<String, String> actualRes = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<HashMap<String, String>>() {});
        Assertions.assertEquals("The client already exists", actualRes.get("name"));

    }
}