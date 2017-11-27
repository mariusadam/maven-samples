package com.ubb.ppp.repository;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class IntFastUtilBaseRepository implements IntMemoryRepository {
    private IntArrayList list = new IntArrayList();

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
