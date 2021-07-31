package ru.job4j.store;

import java.util.Collection;

public interface Store<T> {
    Collection<T> findAll();

    void save(T post);

    T findById(int id);

    void removeById(int id);
}
