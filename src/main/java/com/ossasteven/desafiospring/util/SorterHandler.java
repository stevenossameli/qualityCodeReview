package com.ossasteven.desafiospring.util;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class SorterHandler {

    Sorter<ArticleDTO> sorter;

    @Autowired
    public SorterHandler(Sorter<ArticleDTO> sorter) {
        this.sorter = sorter;
    }

    public void sort(List<ArticleDTO> toSort, String orderStr) throws InvalidRequestParam {


        int order = Integer.parseInt(orderStr);

        Comparator<ArticleDTO> aComparator;


        switch (order) {
            case 0:
                aComparator = (a, b) -> a.getName().compareToIgnoreCase(b.getName());
                sorter.sort(toSort, aComparator);
                break;

            case 1:
                aComparator = (a, b) -> b.getName().compareToIgnoreCase(a.getName());
                sorter.sort(toSort, aComparator);
                break;

            case 2:
                aComparator = (a, b) -> b.getPrice().compareTo(a.getPrice());
                sorter.sort(toSort, aComparator);
                break;

            case 3:
                aComparator = Comparator.comparing(ArticleDTO::getPrice);
                sorter.sort(toSort, aComparator);
                break;

            default:
                throw new InvalidRequestParam(order + " is not a valid order number");

        }
    }
}
