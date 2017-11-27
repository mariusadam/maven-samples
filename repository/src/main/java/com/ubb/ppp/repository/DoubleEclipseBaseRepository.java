package com.ubb.ppp.repository;

import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;

public class DoubleEclipseBaseRepository implements DoubleMemoryRepository {
    private DoubleArrayList list = new DoubleArrayList();
    @Override
    public void add(Double o) {
        list.add(o);
    }

    @Override
    public boolean contains(Double o) {
        return list.contains(o);
    }

    @Override
    public void remove(Double o) {
        list.remove(o);
    }
}
