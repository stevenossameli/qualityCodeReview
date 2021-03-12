package com.ossasteven.desafiospring.repository;

import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;

import java.util.HashMap;
import java.util.List;

public interface IArticleRepository<T> {
     List<T> getAll(HashMap<String, String> params) throws InvalidRequestParam, NotFoundException;
     List<T> getAll();
     T getById (Long id) throws StoreException;
     T getByNameAndBrand(String name, String brand) throws InvalidRequestParam, NotFoundException;
}