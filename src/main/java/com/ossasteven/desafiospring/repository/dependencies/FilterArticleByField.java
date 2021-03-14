package com.ossasteven.desafiospring.repository.dependencies;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.util.Filter;
import com.ossasteven.desafiospring.util.SorterHandler;
import com.ossasteven.desafiospring.validation.FilterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterArticleByField implements IFilterByField<ArticleDTO, String> {

    FilterValidation validation;
    SorterHandler sorterHandler;
    Filter<ArticleDTO> filter;

    @Autowired
    public FilterArticleByField(FilterValidation validation, SorterHandler sorterHandler, Filter<ArticleDTO> filter) {
        this.validation = validation;
        this.sorterHandler = sorterHandler;
        this.filter = filter;
    }

    @Override
    public List<ArticleDTO> filterByField(List<ArticleDTO> toFilter, String condition, String value) throws InvalidRequestParam, NotFoundException {
        return filterHandler(toFilter, condition, value);
    }

    public List<ArticleDTO> filterHandler(List<ArticleDTO> toFilter, String filterName, String value) throws InvalidRequestParam, NotFoundException {

        //Handle exceptions with the params
        validation.filterValidator(filterName, value);

        if (filterName.equalsIgnoreCase("category"))
            return filter.filterBy(toFilter, ArticleDTO -> ArticleDTO.getCategory().equalsIgnoreCase(value));
        if (filterName.equalsIgnoreCase("productName") || filterName.equalsIgnoreCase("name"))
            return filter.filterBy(toFilter, ArticleDTO -> ArticleDTO.getName().equalsIgnoreCase(value));
        if (filterName.equalsIgnoreCase("sendFree")) return filter.filterBy(toFilter, ArticleDTO::getSendFree);
        if (filterName.equalsIgnoreCase("trademark"))
            return filter.filterBy(toFilter, ArticleDTO -> ArticleDTO.getTradeMark().equalsIgnoreCase(value));
        if (filterName.equalsIgnoreCase("prestige"))
            return filter.filterBy(toFilter, ArticleDTO -> ArticleDTO.getPrestige() == Integer.parseInt(value));
        if (filterName.equalsIgnoreCase("order")) {
            sorterHandler.sort(toFilter, value);
            return toFilter;
        }

        throw new InvalidRequestParam(" Filter " + filterName + " not recognized");
    }
}