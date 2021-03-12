package com.ossasteven.desafiospring.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingCartDTO {

    private Long ticketId;

    private Long ShoppingCartId;
    private Long ClientID;
    private List<ArticleDTO> products;
    private Double totalShoppingCart;
}
