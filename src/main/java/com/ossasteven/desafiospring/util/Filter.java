package com.ossasteven.desafiospring.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class Filter<T> {
    public List<T> filterBy(List<T> toFilter, Predicate<T> p) {

        if (toFilter != null && p != null) {
            return toFilter.stream().filter(p).collect(Collectors.toList());
        }
        return toFilter;
    }
}