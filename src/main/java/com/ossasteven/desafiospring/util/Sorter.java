package com.ossasteven.desafiospring.util;

import java.util.Comparator;
import java.util.List;

public interface Sorter<T> {
    void sort(List<T> list, Comparator<T> c);
}
