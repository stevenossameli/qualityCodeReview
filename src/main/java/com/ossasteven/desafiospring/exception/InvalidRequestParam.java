package com.ossasteven.desafiospring.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestParam extends StoreException{

    private static final long serialVersionUID = 8912078400430558965L;

    private static final String name = "Invalid request param";
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public InvalidRequestParam(String message){
        super(name, message, httpStatus );
    }
}
