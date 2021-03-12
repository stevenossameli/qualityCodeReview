package com.ossasteven.desafiospring.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class StoreException extends Exception {

    private static final long serialVersionUID = -5690690211617964873L;

    private String name;
    private HttpStatus httpStatus;

    public StoreException(String name, String message, HttpStatus httpStatus) {
        super(message);
        this.name = name;
        this.httpStatus = httpStatus;
    }
}