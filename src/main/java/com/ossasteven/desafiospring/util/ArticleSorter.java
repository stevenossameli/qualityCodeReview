package com.ossasteven.desafiospring.util;

import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ArticleSorter implements Sorter<ArticleDTO> {

    @Override
    public void sort(List<ArticleDTO> list, Comparator<ArticleDTO> c) {
        list.sort(c);
    }
}