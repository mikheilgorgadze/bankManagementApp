package org.utils;

import java.util.Comparator;
import java.util.function.Consumer;

public class MyArrayList<T> implements MyList<T>{
    private Object[] arr;
    private int curSize;
    private int maxSize;
    private final double loadFactor = 0.75;
    private final int increaseBy = 2;

    public MyArrayList(){
        this.curSize = 0;
        int initSize = 5;
        this.maxSize = initSize;
        this.arr = new Object[initSize];
    }

    public MyArrayList(Object o){
        MyArrayList<Object> arrayList = new MyArrayList<>();
        arrayList.add(o);
    }
    @Override
    public void add(T o) {
        if (curSize < maxSize * loadFactor){
            arr[curSize++] = o;
        } else {
            maxSize = maxSize * increaseBy;
            Object[] newArr = new Object[maxSize];
            for (int i = 0; i < curSize; i++) {
                newArr[i] = arr[i];
            }

            newArr[curSize++] = o;
            arr = newArr;
        }
    }

    @Override
    public void add(int index, T o) {
        if (index < 0 || index >= curSize){
            throw new IndexOutOfBoundsException("Index out of range");
        }

        if (curSize >= maxSize * loadFactor) {
            maxSize = maxSize * increaseBy;
        }
        curSize++;
        Object[] newArr = new Object[maxSize];
        for (int i = 0; i < curSize; i++){
            if (i < index){
                newArr[i] = arr[i];
            } else if (index == i) {
                newArr[i] = o;
            } else {
                newArr[i] = arr[i-1];
            }
        }
        arr = newArr;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= curSize){
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return (T) arr[index];
    }

    @Override
    public int indexOf(T o) {
        for (int i = 0; i < curSize; i++) {
           if (arr[i].equals(o)) {
               return i;
           }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T o) {
        int lastIndex = -1;
        for (int i = 0; i < curSize; i++) {
            if (arr[i].equals(o)) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= curSize){
            throw new IndexOutOfBoundsException("Index out of range");
        }
        Object[] newArr = new Object[curSize - 1];
        for (int i = 0; i < curSize; i++) {
            if (i < index) {
                newArr[i] = arr[i];
            } else if (i > index) {
                newArr[i - 1] = arr[i];
            }
        }
        curSize--;
        arr = newArr;
    }

    @Override
    public void remove(T o) {
        int index = indexOf(o);
        if (index >= 0){
           remove(index);
        }
    }

    @Override
    public int size() {
        return curSize;
    }

    @Override
    public boolean isEmpty() {
        return curSize == 0;
    }

    @Override
    public boolean contains(T o) {
        return indexOf(o) >= 0;
    }

    @Override
    public void sort(Comparator<T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        for (int i = 0; i < curSize - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < curSize; j++) {
                if (c.compare((T) arr[j], (T) arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (i != minIndex) {
                T temp = (T) arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    public void forEach(Consumer<T> consumer){
        for (int i = 0; i < size(); i++) {
            consumer.accept(get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < curSize; i++) {
            str.append(arr[i]);
            if (i < curSize - 1) {
                str.append(", ");
            }
        }

        str.append("]");

        return str.toString();
    }
}
