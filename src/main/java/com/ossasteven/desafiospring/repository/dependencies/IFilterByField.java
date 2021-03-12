package com.ossasteven.desafiospring.repository.dependencies;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;

import java.util.List;

public interface IFilterByField <T, E>{
     List<T> filterByField(List<T> toFilter, E condition, E value) throws InvalidRequestParam, NotFoundException;
}
