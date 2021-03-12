package com.ossasteven.desafiospring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private Long clientId;
    private String fullName;
    private String email;
    private String provincia;

    //The password has Write Only Access for security

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


}