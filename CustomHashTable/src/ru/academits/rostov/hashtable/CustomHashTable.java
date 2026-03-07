package ru.academits.rostov.hashtable;

import java.util.*;

public class CustomHashTable<E> implements Collection<E> {
    private ArrayList<LinkedList<E>> buckets;
    private int size;
    private int capacity;

    private int modCount;
    private double loadFactor = 0.7;

    public CustomHashTable() {
        this(10);
        capacity = 10;
    }

    public CustomHashTable(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0. Current capacity is: " + size);
        }

        this.capacity = capacity;
        buckets = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    public CustomHashTable(Collection<? extends E> c) {
        this();

        addAll(c);

        capacity = buckets.size();
    }

    private boolean bucketNotNull(LinkedList<E> bucket) {
        return !bucket.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                for (E item : bucket) {
                    stringBuilder.append(item).append(", ");
                }
            }
        }

        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        return stringBuilder.append(']').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        CustomHashTable<?> hashTable = (CustomHashTable<?>) o;

        if (size != hashTable.size()) {
            return false;
        }

        return Arrays.equals(toArray(), hashTable.toArray());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime + hash * Arrays.hashCode(toArray());

        return hash;
    }

    private int getHashIndex(Object o) {
        return Math.abs(o.hashCode() % capacity);
    }

    private void resize(int newCapacity) {
        ArrayList<LinkedList<E>> newBuckets = new ArrayList<>(newCapacity);

        for (int i = 0; i < newCapacity; ++i) {
            newBuckets.add(new LinkedList<>());
        }

        capacity = newCapacity;

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                for (E item : bucket) {
                    newBuckets.get(getHashIndex(item)).add(item);
                }
            }
        }

        buckets = newBuckets;
    }

    @Override
    public int size() {
        int size = 0;

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                size += bucket.size();
            }
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return buckets.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        Objects.requireNonNull(o, "Argument must not be null.");

        return buckets.get(getHashIndex(o)).contains(o);
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    private class CustomIterator implements Iterator<E> {
        private int bucketIndex = 0;
        private Iterator<E> listIterator = null;

        private final int initialModCount = modCount;

        CustomIterator() {
            advanceToNextBucket();
        }

        private void advanceToNextBucket() {
            while (bucketIndex < size) {
                LinkedList<E> list = buckets.get(bucketIndex);
                listIterator = list.iterator();

                if (listIterator.hasNext()) {
                    return;
                }

                ++bucketIndex;
            }

            listIterator = null;
        }

        @Override
        public boolean hasNext() {
            return listIterator != null && listIterator.hasNext();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No elements left in hashtable");
            }

            if (modCount != initialModCount) {
                throw new ConcurrentModificationException("List has been modified!");
            }

            E item = listIterator.next();

            if (!listIterator.hasNext()) {
                ++bucketIndex;

                advanceToNextBucket();
            }

            return item;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        ArrayList<E> items = new ArrayList<>();

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                items.addAll(bucket);
            }
        }

        return items.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Argument must not be null");
        }

        int tableSize = 0;

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                tableSize += bucket.size();
            }
        }

        if (a.length < tableSize) {
            ArrayList<T> tableItemsList = new ArrayList<>();

            for (LinkedList<E> bucket : buckets) {
                if (bucketNotNull(bucket)) {
                    tableItemsList.addAll((Collection<? extends T>) bucket);
                }
            }

            return (T[]) tableItemsList.toArray();
        }

        int i = 0;

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                for (E item : bucket) {
                    a[i] = (T) item;
                    ++i;
                }
            }
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e, "Argument must not be null.");

        int hash = getHashIndex(e);

        if (buckets.get(hash) == null) {
            buckets.add(hash, new LinkedList<>());
        }

        buckets.get(hash).add(e);

        ++size;

        if ((1.0 * size / capacity) > loadFactor) {
            resize(capacity * 2);
        }

        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int hash = getHashIndex(o);

        LinkedList<E> bucket = buckets.get(hash);

        if (!bucketNotNull(bucket)) {
            return false;
        }

        --size;
        ++modCount;

        return bucket.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        for (Object collectionItem : c) {
            if (!contains(collectionItem)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        for (E collectionItem : c) {
            add(collectionItem);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        if (c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                int initialBucketSize = bucket.size();

                if (bucket.removeAll(c)) {
                    size -= bucket.size() - initialBucketSize;
                }

                isChanged = true;
            }
        }

        if (isChanged) {
            ++modCount;
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        if (c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (LinkedList<E> bucket : buckets) {
            if (bucketNotNull(bucket)) {
                int initialBucketSize = bucket.size();

                if (bucket.retainAll(c)) {
                    size -= bucket.size() - initialBucketSize;
                }

                isChanged = true;
            }
        }

        ++modCount;

        return isChanged;
    }

    @Override
    public void clear() {
        for (LinkedList<E> bucket : buckets) {
            if (bucket != null) {
                bucket.clear();
            }
        }

        ++modCount;
        size = 0;
    }
}