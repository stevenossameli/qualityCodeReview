package com.ossasteven.desafiospring.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ArticleDAO implements IArticleDAO {

    List<ArticleDTO> articlesDB;

    @PostConstruct
    public void loadData() {
        this.articlesDB = loadDatabase();
    }

    @Override
    public Optional<ArticleDTO> findOne(Long id) throws StoreException {

        if (articlesDB != null) {
            Optional<ArticleDTO> item = articlesDB.stream()
                    .filter(ArticleDTO -> ArticleDTO.getId().equals(id))
                    .findFirst();
            if (item.isPresent())
                return item;
        }

        throw new NotFoundException("Product with id " + id + " not found");

    }

    @Override
    public Collection<ArticleDTO> getAll() {
        return this.articlesDB;
    }

    @Override
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (ArticleDTO ar :
                articlesDB) {
            categories.add(ar.getCategory().toLowerCase());
        }

        return categories.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getTradeMarks() {
        List<String> tMarks = new ArrayList<>();
        for (ArticleDTO ar :
                articlesDB) {
            tMarks.add(ar.getTradeMark().toLowerCase());
        }

        return tMarks.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (ArticleDTO ar :
                articlesDB) {
            names.add(ar.getName().toLowerCase());
        }

        return names.stream().distinct().collect(Collectors.toList());
    }

    private List<ArticleDTO> loadDatabase() {
        List<ArticleDTO> data = new ArrayList<>();

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:articles.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<ArticleDTO>> typeRef = new TypeReference<>() {
        };

        try {
            data = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    // Unused DAO methods
    @Override
    public Long save(ArticleDTO articleDTO) {
        return null;
    }

    @Override
    public void update(ArticleDTO articleDTO) {
    }

    @Override
    public void delete(Long id) {
    }
}