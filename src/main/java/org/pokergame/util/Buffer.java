package org.pokergame.util;

import org.pokergame.actions.PlayerAction;

public class Buffer<T> {

    private T[] items;
    private int pointer;
    private final Object lock = new Object();
    private long timeoutMillis;

    public Buffer(int capacity, long timeout) {
        this.pointer = 0;
        this.items = (T[]) new Object[capacity];
        this.timeoutMillis = timeout;
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

        long endTime = System.currentTimeMillis() + timeoutMillis;
        long timeLeft = timeoutMillis;

        while (size() == 0 && timeLeft > 0) {
            try {
                wait(timeLeft);
                timeLeft = endTime - System.currentTimeMillis();
            }
            catch (InterruptedException e) {
                // Ignore.
            }
        }

        if (size() == 0) {
            return (T) PlayerAction.TIMED_OUT;
        }

        T item = items[--pointer];
        notifyAll();
        return item;
    }

    public int size() {
        return pointer;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

}