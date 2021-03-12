package com.ossasteven.desafiospring.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class ExceptionDTO {
    String name;
    String message;
    HttpStatus httpStatus;
}
