package com.ubb.ppp.repository;

/**
 * @author Marius Adam
 */
public interface InMemoryRepository<T> {
    void add(T o);
    boolean contains(T o);
    void remove(T o);
}
