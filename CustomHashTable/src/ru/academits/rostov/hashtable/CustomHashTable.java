package ru.academits.rostov.hashtable;

import java.util.*;

public class CustomHashTable<E> implements Collection<E> {
    private final LinkedList<E>[] buckets;
    private int size;

    private int modCount;

    public CustomHashTable() {
        this(10);
    }

    public CustomHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0. Current capacity is: " + capacity);
        }

        //noinspection unchecked
        buckets = new LinkedList[capacity];

        for (int i = 0; i < capacity; ++i) {
            buckets[i] = new LinkedList<>();
        }
    }

    public CustomHashTable(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (LinkedList<E> bucket : buckets) {
            for (E item : bucket) {
                stringBuilder.append(item).append(", ");
            }
        }

        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        return stringBuilder.append(']').toString();
    }

    private int getIndex(Object o) {
        return Math.abs((o != null ? o.hashCode() : 0) % buckets.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return buckets[getIndex(o)].contains(o);
    }

    private class CustomIterator implements Iterator<E> {
        private int bucketIndex;
        private Iterator<E> listIterator;

        private final int initialModCount = modCount;

        public CustomIterator() {
            advanceToNextBucket();
        }

        private void advanceToNextBucket() {
            while (bucketIndex < size) {
                LinkedList<E> list = new LinkedList<>(buckets[bucketIndex]);
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
                throw new ConcurrentModificationException("Hashtable has been modified!");
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
        int arraySize = 0;

        for (LinkedList<E> bucket : buckets) {
            arraySize += bucket.size();
        }

        //noinspection unchecked
        E[] array = (E[]) new Object[arraySize];
        int i = 0;

        for (LinkedList<E> bucket : buckets) {
            for (E item : bucket) {
                array[i] = item;
                ++i;
            }
        }

        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        Objects.requireNonNull(a, "Input array must not be null.");

        T[] array = (T[]) toArray();
        int arrayLength = array.length;

        if (a.length < arrayLength) {
            return array;
        }

        System.arraycopy(array, 0, a, 0, arrayLength);

        if (a.length > array.length) {
            a[arrayLength] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e);

        buckets[index].add(e);

        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        LinkedList<E> bucket = new LinkedList<>(buckets[index]);

        boolean isChanged = bucket.remove(o);

        if (isChanged) {
            ++modCount;
            --size;
        }

        return isChanged;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, "Input collection must not be null");

        for (Object collectionItem : c) {
            if (!contains(collectionItem)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c, "Input collection must not be null");

        if (c.isEmpty()) {
            return false;
        }

        for (E collectionItem : c) {
            add(collectionItem);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, "Input collection must not be null");

        if (c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;
        int newSize = 0;

        for (LinkedList<E> bucket : buckets) {
            if (bucket.removeAll(c)) {
                isChanged = true;
            }

            newSize += bucket.size();
        }

        if (isChanged) {
            ++modCount;
            size = newSize;
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "Input collection must not be null");

        boolean isChanged = false;
        int newSize = 0;

        for (LinkedList<E> bucket : buckets) {
            if (bucket.retainAll(c)) {
                isChanged = true;
            }

            newSize += bucket.size();
        }

        if (isChanged) {
            ++modCount;
            size = newSize;
        }

        return isChanged;
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            for (LinkedList<E> bucket : buckets) {
                if (bucket != null) {
                    bucket.clear();
                }
            }

            size = 0;
            ++modCount;
        }
    }
}