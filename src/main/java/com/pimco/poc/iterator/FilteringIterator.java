package com.pimco.poc.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import function.IObjectTest;

/**
 * @author rohitdhingra
 */
public class FilteringIterator<T> implements Iterator<T> {
    private final Iterator<? extends T> iterator;
    private final IObjectTest<T> predicate;
    private T nextElement;
    private boolean hasNext;

    public FilteringIterator(final Iterator<T> it, final IObjectTest<T> test) {
        iterator = it;
        predicate = test;
    }

    @Override
    public boolean hasNext() {
        hasNext = false;
        while (iterator.hasNext()) {
            nextElement = iterator.next();
            if (predicate.test(nextElement)) {
                hasNext = true;
                break;
            }
        }
        return hasNext;
    }

    @Override
    public T next() {
        if (hasNext) {
            return nextElement;
        }
        /** scenario where next() is called without checking if element exist or not? **/
        if (hasNext()) {
            return nextElement;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
    	iterator.remove();
    }

}
