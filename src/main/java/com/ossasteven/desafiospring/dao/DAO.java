package com.ossasteven.desafiospring.dao;

import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.StoreException;

import java.util.Collection;
import java.util.Optional;

public interface DAO <T> {
    Optional<T> findOne(Long id) throws StoreException;
    Collection<T> getAll();
    Long save(T t) throws AlreadyExistsException;
    void update(T t);
    void delete(Long id);
}
