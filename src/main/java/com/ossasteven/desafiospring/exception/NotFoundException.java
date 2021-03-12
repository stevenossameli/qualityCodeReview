package com.ossasteven.desafiospring.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends StoreException {

    private static final long serialVersionUID = -5695296474287298161L;

    private static final String name = "Resource not found in our database";
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;


    public NotFoundException(String message){
        super(name, message, httpStatus );
    }
}
