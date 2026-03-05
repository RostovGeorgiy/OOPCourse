package ru.academits.rostov.arraylist;

import java.util.*;

public class CustomArrayList<E> implements List<E> {
    private E[] items;
    private int size;

    private int modCount;

    public CustomArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[10];
    }

    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0. Current capacity is: " + capacity);
        }
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public CustomArrayList(Collection<? extends E> c) {
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

        CustomArrayList<?> list = (CustomArrayList<?>) o;

        return Arrays.equals(items, list.items);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(items);
        return hash;
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
        Objects.requireNonNull(o, "Argument must not be null");

        return indexOf(o) != -1;
    }

    private class CustomIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int initialModCount = modCount;

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
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("Argument must not be null");
        }

        if (a.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        Objects.requireNonNull(item, "Argument must not be null");

        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = item;

        ++size;
        ++modCount;

        return true;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            ensureCapacity(10);
        } else {
            ensureCapacity(items.length * 2);
        }
    }

    @Override
    public boolean remove(Object o) {
        Objects.requireNonNull(o, "Argument must not be null");

        int index = indexOf(o);

        if (index == -1) {
            return false;
        }

        remove(index);

        return true;
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

        return addAll(size, c);
    }

    @SuppressWarnings({"SuspiciousSystemArraycopy"})
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

        int collectionSize = c.size();

        ensureCapacity(size + collectionSize);

        System.arraycopy(items, index, items, index + collectionSize, size - index);
        System.arraycopy(c.toArray(), 0, items, index, collectionSize);

        size += collectionSize;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        if (c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (int i = size - 1; i >= 0; --i) {
            if (c.contains(items[i])) {
                remove(i);

                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "Argument must not be null");

        boolean isChanged = false;

        for (int i = size - 1; i >= 0; --i) {
            if (!c.contains(items[i])) {
                remove(i);

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
        if (size != 0) {
            Arrays.fill(items, null);
        }

        size = 0;
        ++modCount;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        Objects.requireNonNull(item, "Argument must not be null");

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

        Objects.requireNonNull(item, "Argument must not be null");

        if (index == size) {
            add(item);
            return;
        }

        if (size + 1 >= items.length) {
            increaseCapacity();
        }

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
        Objects.requireNonNull(o, "Argument must not be null");

        for (int i = 0; i < size; ++i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Objects.requireNonNull(o, "Argument must not be null");

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