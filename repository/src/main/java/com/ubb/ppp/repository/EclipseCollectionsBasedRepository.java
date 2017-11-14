package com.ubb.ppp.repository;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import java.util.Map;
import java.util.Set;

/**
 * @author Marius Adam
 */
public class EclipseCollectionsBasedRepository<T> implements InMemoryRepository<T> {
    private Set<T> items;

    public EclipseCollectionsBasedRepository() {
        items = new UnifiedSet<>();
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
