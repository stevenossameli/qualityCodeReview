package com.ossasteven.desafiospring.controllers;


import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ClientDTO;
import com.ossasteven.desafiospring.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ClientController extends BaseController{

    @Autowired
    IClientService<ClientDTO> service;

    @PostMapping("/client")
    public ResponseEntity<Object> createClient(@RequestBody ClientDTO client) throws AlreadyExistsException, InvalidRequestParam {
        return service.createClient(client);
    }

    @GetMapping("/clients")
    public List<ClientDTO> getClients(@RequestParam(required = false) String provincia) throws NotFoundException, InvalidRequestParam {
       return service.getAll(provincia);
    }
}
