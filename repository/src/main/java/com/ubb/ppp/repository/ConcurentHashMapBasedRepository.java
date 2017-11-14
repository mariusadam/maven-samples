package com.ubb.ppp.repository;

import com.ubb.ppp.beans.HasId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marius Adam
 */
public class ConcurentHashMapBasedRepository<Id, T  extends HasId<Id>> implements InMemoryRepository<T> {
    private Map<Id, T> items;

    public ConcurentHashMapBasedRepository() {
        items = new ConcurrentHashMap<>();
    }

    @Override
    public void add(T o) {
        items.put(o.getId(), o);
    }

    @Override
    public boolean contains(T o) {
        return items.containsKey(o.getId());
    }

    @Override
    public void remove(T o) {
        items.remove(o.getId());
    }
}
