package com.ossasteven.desafiospring.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    Long id;
    String name;
    String category;
    String tradeMark;
    Double price;
    Integer quantity;
    Boolean sendFree;
    Integer prestige;
}