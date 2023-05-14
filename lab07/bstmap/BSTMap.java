package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {




    private class BST {
        private BST left;
        private BST right;
        private K key;
        private V value;

        public BST(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int size = 0;
    private BST middle;

    public void clear(){
        size = 0;
        middle = null;
    }
    public boolean containsKey(K key){
        return cKHelp(key, middle);
    }

    private boolean cKHelp(K key, BST curr) {
        if (curr == null) {
            return false;
        }
        if (curr.key.equals(key)) {
            return true;
        }
        if (curr.key.compareTo(key) > 0) {
            return cKHelp(key, curr.right);
        }
        return cKHelp(key, curr.left);
    }

    public V get(K key){
        return helper(key, middle);
    }

    private V helper(K key, BST curr) {
        if (curr == null) {
            return null;
        }
        if (curr.key.compareTo(key) == 0) {
            return curr.value;
        }
        if (curr.key.compareTo(key) > 0) {
            return helper(key, curr.right);
        }
        return helper(key, curr.left);
    }

    public int size(){
        return size;
    }
    public void put(K key, V value){
        size++;
        middle = helpPut(middle, key, value);
    }

    private BST helpPut(BST curr, K key, V value) {
        if (curr == null) {
            curr = new BST(key, value);
        }
        else if (curr.key.equals(key)) {
            curr.value = value;
            size--;
        }
        else if (curr.key.compareTo(key) > 0) {
            curr.right = helpPut(curr.right, key, value);
        }
        else {
            curr.left = helpPut(curr.left, key, value);
        }
        return curr;
    }

    public void printInOrder(){
        printer(middle);
    }

    private void printer(BST curr) {
        if (curr == null) {
            return;
        }
        if (curr.left != null) {
            printer(curr.left);
        }
        System.out.println(curr.key);
        printer(curr.right);
    }

    public Set<K> keySet(){
        throw new UnsupportedOperationException();
    }
    public V remove(K key){
        size--;
        throw new UnsupportedOperationException();
    }
    public V remove(K key, V value){
        size--;
        throw new UnsupportedOperationException();
    }
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
