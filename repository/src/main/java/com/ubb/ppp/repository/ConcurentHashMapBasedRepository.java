package com.ubb.ppp.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marius Adam
 */
public class ConcurentHashMapBasedRepository<T> implements InMemoryRepository<T> {
    private Map<T, T> items;

    public ConcurentHashMapBasedRepository() {
        items = new ConcurrentHashMap<>();
    }

    @Override
    public void add(T o) {
        items.put(o, o);
    }

    @Override
    public boolean contains(T o) {
        return items.containsKey(o);
    }

    @Override
    public void remove(T o) {
        items.remove(o);
    }
}
