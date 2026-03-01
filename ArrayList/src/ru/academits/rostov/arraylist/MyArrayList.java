package ru.academits.rostov.arraylist;

import java.util.*;

public class MyArrayList<E> implements List<E> {
    E[] items;
    int size;

    int modCount = 0;

    public MyArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[10];
    }

    public MyArrayList(int size) {
        this();
        this.size = size;
        ensureCapacity(size);
    }

    private void checkArgumentNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException("Argument must not be null");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            @SuppressWarnings("unchecked") E[] increasedCapacityArray = (E[]) new Object[capacity];

            System.arraycopy(items, 0, increasedCapacityArray, 0, size);

            items = increasedCapacityArray;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(items, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return items.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(items[i], o)) {
                return true;
            }
        }

        return false;
    }

    private class MyIterator implements Iterator<E> {
        private int currentIndex = -1;
        int previousModCount = modCount;

        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        public E next() {
            if (currentIndex == size) {
                throw new NoSuchElementException("No more items in list.");
            }

            if (modCount != previousModCount) {
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
        @SuppressWarnings("unchecked") E[] copiedArray = (E[]) new Object[size];
        System.arraycopy(items, 0, copiedArray, 0, size);

        return copiedArray;
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
            a = (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        int previousSize = size;

        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = item;
        ++size;
        ++modCount;

        return size == previousSize + 1;
    }

    @Override
    public void addFirst(E e) {
        ensureCapacity(size + 1);

        System.arraycopy(items, 0, items, 1, size);

        items[0] = e;

        ++size;
        ++modCount;
    }

    @Override
    public void addLast(E e) {
        ensureCapacity(size + 1);

        System.arraycopy(items, 0, items, 0, size);

        items[size] = e;

        ++size;
        ++modCount;
    }

    private void increaseCapacity() {
        ensureCapacity(size * 2);
    }

    @Override
    public boolean remove(Object o) {
        int previousSize = size;

        for (int i = 0; i < size; ++i) {
            if (items[i].equals(o)) {
                System.arraycopy(items, 0, items, 0, i);

                System.arraycopy(items, i + 1, items, i, size - (i + 1));

                --size;
                ++modCount;

                break;
            }
        }

        return size == previousSize - 1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection must not be null");
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
        ensureCapacity(items.length + c.size());

        @SuppressWarnings("unchecked") E[] collectionItems = (E[]) c.toArray();

        int previousSize = size;

        for (E item : collectionItems) {
            add(item);
        }

        modCount++;

        return previousSize != size;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);

        checkArgumentNotNull(c);

        int previousSize = size;

        int arrayShift = c.size();

        ensureCapacity(size + c.size());

        modCount++;

        System.arraycopy(items, index, items, index + arrayShift, size - index);

        @SuppressWarnings("unchecked") Iterator<E> iterator = (Iterator<E>) c.iterator();

        for (int i = index; i < index + arrayShift; ++i) {
            items[i] = iterator.next();
        }

        size += arrayShift;

        return size == previousSize + c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        checkArgumentNotNull(c);

        int previousSize = size;

        for (Object item : c) {
            remove(item);
        }

        modCount++;

        trimToSize();

        return previousSize != size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        checkArgumentNotNull(c);

        int previousSize = size;

        for (E item : items) {
            if (!c.contains(item)) {
                remove(item);
            }
        }

        modCount++;

        return previousSize + c.size() == size;
    }

    public void trimToSize() {
        System.arraycopy(items, 0, items, 0, size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        items = (E[]) new Object[0];
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

        ensureCapacity(size + 1);

        if (index == size) {
            add(item);
            return;
        }

        System.arraycopy(items, 0, items, 0, index);
        System.arraycopy(items, index, items, index + 1, items.length - index - 1);
        items[index] = item;

        ++modCount;
        ++size;
    }

    @Override
    public E removeFirst() {
        E deletedItem = items[0];

        System.arraycopy(items, 1, items, 0, size - 1);

        --size;
        ++modCount;

        return deletedItem;
    }

    @Override
    public E removeLast() {
        E deletedItem = items[size - 1];

        System.arraycopy(items, 0, items, 0, size - 1);

        items[size - 1] = null;

        --size;
        ++modCount;

        return deletedItem;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        if (index == size) {
            return removeLast();
        }

        System.arraycopy(items, 0, items, 0, index);

        System.arraycopy(items, index + 1, items, index, items.length - index - 1);

        ++modCount;
        --size;

        return null;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; ++i) {
                if (items[i] == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; ++i) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (items[i] == null)
                    return i;
            }
        } else {
            for (int i = size - 1; i >= 0; --i) {
                if (o.equals(items[i])) {
                    return i;
                }
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