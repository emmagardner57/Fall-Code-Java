package deque;

import java.util.Iterator;


public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {


    private class TNode {
        private T item;
        private TNode next;
        private TNode prev;
        public TNode(T i, TNode p, TNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }
    private TNode sentinel;
    private TNode first;
    private TNode last;
    private int size = 0;

    @Override
    public void addFirst(T item) {
        if (size == 0) {
            sentinel = new TNode(null, sentinel, sentinel);
            last = new TNode(item, sentinel, sentinel);
            first = last;
        } else {
            first = new TNode(item, sentinel, first);
            first.next.prev = first;
        }
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            sentinel = new TNode(null, sentinel, sentinel);
            first = new TNode(item, sentinel, sentinel);
            last = first;
        } else {
            last = new TNode(item, last, sentinel);
            last.prev.next = last;
        }
        size++;
    }


    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (TNode curr = first; curr.next != null; curr = curr.next) {
            if (curr.next.item == null) {
                System.out.println(curr.item);
            }
            System.out.print(curr.item + " ");
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = first.item;
        first = first.next;
        if (first.item != null) {
            first.prev = sentinel;
        }
        size--;
        return x;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = last.item;
        last = last.prev;
        if (last.item != null) {
            last.next = sentinel;
        }
        size--;
        return x;
    }

    @Override
    public T get(int index) {
        TNode curr = first;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return curr.item;
            }
            curr = curr.next;
        }
        return null;
    }

    public T getRecursive(int index) {
        TNode temp = first;
        return helper(index, temp);
    }

    private T helper(int index, TNode temp) {
        if (index == 0) {
            return temp.item;
        }
        index--;
        return helper(index, temp.next);
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {

            Deque<T> tempList = (Deque<T>) o;

            if (tempList.size() != size) {
                return false;
            }

            for (int x = 0; x < size; x++) {
                if (!tempList.get(x).equals(get(x))) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    private class LLDIterator implements Iterator<T> {
        private TNode counter;
        private int index = 0;

        public LLDIterator() {
            counter = first;
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            T value = counter.item;
            index++;
            counter = counter.next;
            return value;
        }
    }

    public Iterator<T> iterator() {
        return new LLDIterator();
    }
}

