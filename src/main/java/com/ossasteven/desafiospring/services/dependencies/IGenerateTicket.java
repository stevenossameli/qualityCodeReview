package com.ossasteven.desafiospring.services.dependencies;

import com.ossasteven.desafiospring.exception.StoreException;

import java.util.List;

public interface IGenerateTicket <T, S, A> {
    T generateTicket(List<A> ticketComponents) throws StoreException;
    S generateShoppingCart (List<A> cartComponents, Long clientId) throws StoreException;
    Double confirmPurchase(Long ticketId);
}
