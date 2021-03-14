package com.ossasteven.desafiospring.services;

import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IClientService<T> {
    ResponseEntity<Object> createClient(T client) throws AlreadyExistsException, InvalidRequestParam;

    List<T> getAll(String param) throws NotFoundException, InvalidRequestParam;
}
