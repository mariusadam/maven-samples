package com.ubb.ppp.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marius Adam
 */
public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {
    private List<T> items;

    public ArrayListBasedRepository() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T o) {
        items.add(o);
    }

    @Override
    public boolean contains(T o) {
        return items.contains(o);
    }

    @Override
    public void remove(T o) {
        items.remove(o);
    }
}
