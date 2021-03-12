package com.ossasteven.desafiospring.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TicketDTO {
    Long id;
    List<ArticleDTO> articles;
    Double total;
}
