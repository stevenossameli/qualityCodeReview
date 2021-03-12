package com.ossasteven.desafiospring.validation;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.model.ClientDTO;

public class ClientValidation {

    public static void validateFields(ClientDTO clientDTO) throws InvalidRequestParam {

        if(clientDTO.getEmail() == null)
            throw new InvalidRequestParam("you must specified an email");

        if(clientDTO.getProvincia() ==  null)
            throw new InvalidRequestParam("the client provincia can not be null");

        if(clientDTO.getPassword() == null)
            throw new InvalidRequestParam("you must specified a password");

    }
}
