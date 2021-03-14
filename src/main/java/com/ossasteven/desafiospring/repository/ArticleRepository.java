package com.ossasteven.desafiospring.repository;

import com.ossasteven.desafiospring.dao.IArticleDAO;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.repository.dependencies.IFilterByField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticleRepository implements IArticleRepository<ArticleDTO> {

    IArticleDAO dao;
    IFilterByField<ArticleDTO, String> filterHandler;


    @Autowired
    public ArticleRepository(IArticleDAO dao, IFilterByField<ArticleDTO, String> filterHandler) {
        this.dao = dao;
        this.filterHandler = filterHandler;
    }

    @Override
    public List<ArticleDTO> getAll(HashMap<String, String> params) throws InvalidRequestParam, NotFoundException {
        return getAllWithFilter(getAll(), params);
    }

    @Override
    public List<ArticleDTO> getAll() {
        return new ArrayList<>(dao.getAll());
    }

    @Override
    public ArticleDTO getById(Long id) throws StoreException {
        return dao.findOne(id).orElse(null);
    }

    @Override
    public ArticleDTO getByNameAndBrand(String name, String brand) throws NotFoundException {
        List<ArticleDTO> articlesDB = new ArrayList<>(dao.getAll());
        Optional<ArticleDTO> item = articlesDB.stream()
                .filter(articleDTO -> articleDTO.getName().equalsIgnoreCase(name) &&
                        articleDTO.getTradeMark().equalsIgnoreCase(brand))
                .findFirst();
        if (item.isPresent())
            return item.get();

        throw new NotFoundException("Product with name " + name + " and trade mark " + brand + " not found ");
    }

    private List<ArticleDTO> getAllWithFilter(List<ArticleDTO> list, HashMap<String, String> params) throws InvalidRequestParam, NotFoundException {
        List<ArticleDTO> filtered = list;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            filtered = filterHandler.filterByField(filtered, entry.getKey(), entry.getValue());
        }
        return filtered;
    }
}