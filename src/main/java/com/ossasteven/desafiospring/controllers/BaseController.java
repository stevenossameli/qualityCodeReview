package com.ossasteven.desafiospring.controllers;

import com.ossasteven.desafiospring.exception.ExceptionDTO;
import com.ossasteven.desafiospring.exception.StoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StoreException.class)
    public ResponseEntity<Object> handleMissingParams(StoreException ex) {

        ExceptionDTO exception = new ExceptionDTO();
        exception.setName(ex.getName());
        exception.setMessage(ex.getMessage());
        exception.setHttpStatus(ex.getHttpStatus());

       return new ResponseEntity<>(exception, ex.getHttpStatus());

    }
}
