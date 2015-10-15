package com.simpleNettyServer.util;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ConnectQueue<E> implements Iterable<E> {
    private final ConcurrentLinkedQueue<E> queue = new ConcurrentLinkedQueue<>();
    private int counter = 0;

    public Iterator<E> iterator() {
        return queue.iterator();
    }

    public synchronized boolean add(E e) {
        counter++;
        if (counter == 16) {
            queue.remove();
            counter--;
        }
        return queue.add(e);
    }
}
