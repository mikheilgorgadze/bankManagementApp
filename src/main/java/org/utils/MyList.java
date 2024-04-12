package org.utils;

import java.util.Comparator;

public interface MyList<T> {
    void add(T o);
    void add(int index, T o);
    T get(int index);
    int indexOf(T o);
    int lastIndexOf(T o);
    void remove(int index);
    void remove(T o);
    int size();
    boolean isEmpty();
    boolean contains(T o);
    void sort(Comparator<T> c);
}
