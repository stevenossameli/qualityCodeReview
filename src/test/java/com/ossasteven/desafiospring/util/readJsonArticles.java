package com.ossasteven.desafiospring.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readJsonArticles {

    public static List<ArticleDTO> loadDatabase() {
        List<ArticleDTO> data = new ArrayList<>();

        File file = null;
        try {
            file = ResourceUtils.getFile("src/main/resources/articles.json");
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
}
