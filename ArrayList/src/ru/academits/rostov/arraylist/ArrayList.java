package ru.academits.rostov.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int size;

    private int modCount;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[10];
    }

    public ArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0. Current capacity is: " + capacity);
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList(Collection<? extends E> c) {
        //noinspection unchecked
        items = (E[]) new Object[c.size()];

        addAll(c);
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(size + 2);
        stringBuilder.append('[');

        for (int i = 0; i < size; ++i) {
            stringBuilder.append(items[i]).append(", ");
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

        @SuppressWarnings("unchecked") ArrayList<E> list = (ArrayList<E>) o;

        return Arrays.equals(items, list.items);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + size;
        return prime * hash + Arrays.hashCode(items);
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
        return indexOf(o) != -1;
    }

    private class MyIterator implements Iterator<E> {
        private int currentIndex = -1;
        int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in list.");
            }

            if (modCount != initialModCount) {
                throw new ConcurrentModificationException("List has been modified!");
            }

            ++currentIndex;
            return items[currentIndex];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Argument must not be null");
        }

        if (a.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        for (int i = 0; i < size; ++i) {
            a[i] = (T) items[i];
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = item;

        ++size;
        ++modCount;

        return true;
    }

    private void increaseCapacity() {
        if (size == 0) {
            ensureCapacity(10);
        } else {
            ensureCapacity(size * 2);
        }
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index == -1) {
            return false;
        }

        remove(index);

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection must not be null");
        }

        if (c.isEmpty()) {
            return false;
        }

        for (Object collectionItem : c) {
            if (!contains(collectionItem)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and <= list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        Objects.requireNonNull(c, "Argument must not be null");

        if (c.isEmpty()) {
            return false;
        }

        for (E item : c) {
            add(index, item);
            ++index;
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

        for (int i = 0; i < size; ++i) {
            if (c.contains(items[i])) {
                remove(i);
                --i;

                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        boolean isChanged = false;

        for (int i = 0; i < size; ++i) {
            if (!c.contains(items[i])) {
                remove(i);
                --i;

                isChanged = true;
            }
        }

        return isChanged;
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; ++i) {
            items[i] = null;
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldData = items[index];

        items[index] = item;

        return oldData;
    }

    @Override
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and <= list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        if (index == size) {
            add(item);
            return;
        }

        ensureCapacity(size + 1);
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;

        ++modCount;
        ++size;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E deletedItem = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);

        ++modCount;
        --size;

        return deletedItem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Method not supported");
    }
}