package com.ossasteven.desafiospring.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends StoreException {
    private static final long serialVersionUID = -6741669065913613449L;

    private static final String name = "The client already exists";
    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public AlreadyExistsException(String message) {
        super(name, message, httpStatus);

    }

}
