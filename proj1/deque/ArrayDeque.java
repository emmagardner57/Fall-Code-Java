package deque;

import java.util.Iterator;


public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int length = 0;
    private int amount = 0;
    private T[] items;
    private T[] newList;
    private int nextFirst;
    private int nextLast;
    private static final int START_LENGTH = 8;
    private static final int MIN_LENGTH = 16;


    private void checkResize() {
        if (amount > length) {
            resize(length * 2);
        }
        if ((length >= MIN_LENGTH) && (amount < (.25 * length))) {
            resize(length / 2);
        }
    }

    @Override
    public void addFirst(T item) {
        if (length == 0) {
            arrayDeque();
        }
        amount++;
        checkResize();
        items[nextFirst] = item;
        nextFirst = goBack(nextFirst);
    }

    @Override
    public void addLast(T item) {
        if (length == 0) {
            arrayDeque();
        }
        amount++;
        checkResize();
        items[nextLast] = item;
        nextLast = goForward(nextLast);
    }


    @Override
    public int size() {
        return amount;
    }

    @Override
    public void printDeque() {
        for (int i = goForward(nextFirst); i != nextLast; i = goForward(i)) {
            if (goForward(i) == nextLast) {
                System.out.println(items[i]);
            } else {
                System.out.print(items[i] + " ");
            }
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = goForward(nextFirst);
        T temp = items[nextFirst];
        amount--;
        checkResize();
        items[nextFirst] = null;
        return temp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = goBack(nextLast);
        T temp = items[nextLast];
        amount--;
        checkResize();
        items[nextLast] = null;
        return temp;
    }

    @Override
    public T get(int index) {
        if (index > amount) {
            return null;
        }
        int x = goForward(nextFirst) + index;
        if (x >= length) {
            x -= length;
        }
        return items[x];
    }



    public boolean equals(Object o) {
        if (o instanceof Deque) {
            Deque<T> tempList = (Deque<T>) o;

            if (tempList.size() != amount) {
                return false;
            }

            for (int x = 0; x < amount; x++) {
                if (!tempList.get(x).equals(get(x))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private void arrayDeque() {
        items = (T[]) new Object[START_LENGTH];
        length = START_LENGTH;
        nextFirst = 0;
        nextLast = 1;
    }


    private void resize(int x) {
        newList = (T[]) new Object[x];
        int y = nextFirst;
        nextLast = amount;
        if (x > length) {
            nextLast = amount - 1;
        }
        for (int i = 0; i < nextLast; i++) {
            y = goForward(y);
            newList[i] = items[y];
        }
        items = newList;
        length = x;
        nextFirst = length - 1;
    }


    private int goBack(int x) {
        x--;
        if (x < 0) {
            x = length - 1;
        }
        return x;
    }


    private int goForward(int x) {
        x++;
        if (x >= length) {
            x = 0;
        }
        return x;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int counter;
        public ArrayDequeIterator() {
            counter = goForward(nextFirst);
        }

        public boolean hasNext() {
            int nextCounter = counter + 1;
            return nextCounter <= nextLast;
        }

        public T next() {
            T value = items[counter];
            counter++;
            return value;
        }


    }



}

