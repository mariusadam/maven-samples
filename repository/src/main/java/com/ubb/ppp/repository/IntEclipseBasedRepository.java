package com.ubb.ppp.repository;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

public class IntEclipseBasedRepository implements IntMemoryRepository{
    private IntArrayList list;

    public IntEclipseBasedRepository() {
        this.list = new IntArrayList();
    }

    @Override
    public void add(Integer o) {
        list.add(o);
    }

    @Override
    public boolean contains(Integer o) {
        return list.contains(o);
    }

    @Override
    public void remove(Integer o) {
        list.remove(o);
    }
}
