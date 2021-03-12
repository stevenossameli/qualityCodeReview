package com.ossasteven.desafiospring.dao;

import com.ossasteven.desafiospring.model.ArticleDTO;

import java.util.List;

public interface IArticleDAO extends DAO<ArticleDTO>{
     // for validation methods
     List<String> getCategories();
     List<String> getTradeMarks();
     List<String> getProductNames();
}