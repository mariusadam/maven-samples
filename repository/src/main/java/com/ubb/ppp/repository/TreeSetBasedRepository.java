package com.ubb.ppp.repository;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Marius Adam
 */
public class TreeSetBasedRepository<T> implements InMemoryRepository<T> {
    private Set<T> items;

    public TreeSetBasedRepository() {
        items = new TreeSet<>();
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
