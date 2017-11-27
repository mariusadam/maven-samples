package com.ubb.ppp.repository;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;

public class DoubleFastUtilBaseRepository implements DoubleMemoryRepository {
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
