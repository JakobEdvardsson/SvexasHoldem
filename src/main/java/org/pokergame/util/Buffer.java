package org.pokergame.util;

public class Buffer<T> {

    private T[] items;
    private int pointer;
    private final Object lock = new Object();

    public Buffer(int capacity) {
        this.pointer = 0;
        this.items = (T[]) new Object[capacity];
    }

    public synchronized void add(T item) {
        try {
            while (size() == items.length) {
                wait();
            }
        } catch (InterruptedException e) {}

        items[pointer++] = item;
        notifyAll();
    }

    public synchronized T get() {
        try {
            while (size() == 0) {
                wait();
            }

        } catch (InterruptedException e) {}

        notifyAll();
        return items[--pointer];
    }

    public int size() {
        return pointer;
    }
}