package com.ossasteven.desafiospring.services;

import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ClientDTO;
import com.ossasteven.desafiospring.repository.IClientRepository;
import com.ossasteven.desafiospring.validation.ClientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ClientService implements IClientService<ClientDTO> {

    @Autowired
    IClientRepository<ClientDTO> repository;

    @Override
    public ResponseEntity<Object> createClient(ClientDTO client) throws AlreadyExistsException, InvalidRequestParam {

        ClientValidation.validateFields(client);

        HashMap<String, String> response = new HashMap<>();

        Long rows = repository.saveClient(client);

        if (rows > 0) {
            response.put("message", "client registered successfully");
            response.put("statusCode", "" + HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "Unexpected server error");
            response.put("statusCode", "" + HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ClientDTO> getAll(String param) throws NotFoundException, InvalidRequestParam {
        return repository.getAll(param);

    }
}
