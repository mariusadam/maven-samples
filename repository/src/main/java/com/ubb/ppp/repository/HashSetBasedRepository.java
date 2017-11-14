package com.ubb.ppp.repository;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marius Adam
 */
public class HashSetBasedRepository<T> implements InMemoryRepository<T> {
    private Set<T> items;

    public HashSetBasedRepository() {
        items = new HashSet<>();
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
