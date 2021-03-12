package com.ossasteven.desafiospring.repository;

import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;

import java.util.List;

public interface IClientRepository<T> {

    Long saveClient(T client) throws AlreadyExistsException;
    List<T> getAll(String param) throws NotFoundException, InvalidRequestParam;
    T getById (Long id) throws StoreException;
}
