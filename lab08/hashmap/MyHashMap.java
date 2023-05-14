package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private static final int startSize = 16;
    private static final double loadFactor = 0.75;
    private int numBuckets;
    private int size = 0;
    private double maximum;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(startSize);
        numBuckets = startSize;
        maximum = loadFactor;
        buckets = new Collection[startSize];

    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        numBuckets = initialSize;
        maximum = loadFactor;
        buckets = new Collection[initialSize];
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        numBuckets = initialSize;
        maximum = maxLoad;
        buckets = new Collection[initialSize];
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
       return new Collection[tableSize];
    }

    public void clear() {
        size = 0;
        buckets = createTable(numBuckets);
    }

    public boolean containsKey(K key) {
        int relCode = key.hashCode();
        relCode %= numBuckets;

        if (relCode < 0) {
            relCode *= -1;
        }
        if (buckets[relCode] == null) {
            return false;
        }
        for (Node item : buckets[relCode]) {
            if (item.key.hashCode() == key.hashCode()) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        int relCode = key.hashCode();
        relCode %= numBuckets;

        if (relCode < 0) {
            relCode *= -1;
        }

        if (buckets[relCode] != null) {
            for (Node curr : buckets[relCode]) {
                if (curr != null && (curr.key.hashCode() == key.hashCode())) {
                    return curr.value;
                }
            }
        }
        return null;
    }

    public int size() {
        return size;
    }
    private void emptyBucket(int index, K key, V value) {
        buckets[index] = createBucket();
        Node curr = createNode(key, value);
        buckets[index].add(curr);
        size++;
    }

    public void put(K key, V value) {
        double doubSize = size;
        double doubBucks = numBuckets;
        double comparison = doubSize / doubBucks;
        if (comparison > maximum) {
            enlargen();
        }

        int relCode = key.hashCode();
        relCode %= numBuckets;
        boolean added = false;

        if (relCode < 0) {
            relCode *= -1 ;
        }

        if (buckets[relCode] == null) {
            emptyBucket(relCode, key, value);
            added = true;
        }

        else if (!added) {
            for (Node item : buckets[relCode]) {
                if (item.key.hashCode() == key.hashCode()) {
                    item.value = value;
                    added = true;
                }
            }
        }

        if (!added) {
            Node curr = createNode(key, value);
            buckets[relCode].add(curr);
            size++;
        }
    }
    private void enlargen() {
        Collection<Node>[] temp = buckets;
        this.clear();
        numBuckets *= 2;
        buckets = createTable(numBuckets);
        size = 0;
        for (Collection<Node> list : temp) {
            if (list != null) {
                for (Node item : list) {
                    put(item.key, item.value);
                }
            }
        }
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        return iterator();
    }



}
