package ru.academits.rostov.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SingleLinkedList<T> {
    private ListItem<T> head;
    private int size = 0;

    public SingleLinkedList() {
    }

    private ListItem<T> getItemByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        int i = 0;

        ListItem<T> item = head;

        while (i < index - 1) {
            item = item.getNext();
            ++i;
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (ListItem<T> item = head; item != null; item = item.getNext()) {
            stringBuilder.append(item.getData());
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        SingleLinkedList<?> list = (SingleLinkedList<?>) o;

        if (this.size != list.size) {
            return false;
        }

        ListItem<T> thisCurrentItem = head;
        ListItem<?> otherCurrentItem = list.head;

        if (size != list.getSize()) {
            return false;
        }

        while (thisCurrentItem != null) {
            if (!Objects.equals(thisCurrentItem.getData(), otherCurrentItem.getData())) {
                return false;
            }
            thisCurrentItem = thisCurrentItem.getNext();
            otherCurrentItem = otherCurrentItem.getNext();
        }

        return true;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int hash = 1;

        ListItem<T> currentItem = head;

        while (currentItem != null) {
            hash = prime * hash + currentItem.getData().hashCode();
            currentItem = currentItem.getNext();
        }

        return hash;
    }

    public int getSize() {
        return size;
    }

    public T getDataByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        int i = 0;

        ListItem<T> currentItem = head;

        while (i < index) {
            currentItem = currentItem.getNext();
            ++i;
        }

        return currentItem.getData();
    }

    public T setDataByIndex(int index, T data) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        int i = 0;
        ListItem<T> currentItem = head;

        while (i < index) {
            currentItem = currentItem.getNext();
            ++i;
        }

        T oldData = currentItem.getData();
        currentItem.setData(data);

        return oldData;
    }

    public T deleteItemByIndex(int index) {
        if (head == null) {
            throw new NoSuchElementException("List must not be empty!");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        T deletedData;

        if (index == 0) {
            deletedData = head.getData();

            head = head.getNext();
        } else {
            ListItem<T> targetItem = getItemByIndex(index - 1);

            deletedData = targetItem.getNext().getData();

            targetItem.setNext(targetItem.getNext().getNext());
        }

        --size;

        return deletedData;
    }

    public void insertFirst(T data) {
        if (head == null) {
            head = new ListItem<>(data);
        } else {
            head = new ListItem<>(data, head);
        }

        ++size;
    }

    public void add(T data) {
        if (head == null) {
            insertFirst(data);

            return;
        }

        if (head.getNext() == null) {
            head.setNext(new ListItem<>(data));
            ++size;

            return;
        }

        ListItem<T> currentItem = head.getNext();

        while (currentItem.getNext() != null) {
            currentItem = currentItem.getNext();
        }

        currentItem.setNext(new ListItem<>(data));
        ++size;
    }

    public void insertItemByIndex(int index, T data) {
        if (index < 0 || (index != 0 && index >= size)) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        if (index == 0) {
            insertFirst(data);

            return;
        }

        int i = 0;
        ListItem<T> currentItem = head;

        while (i < index - 1) {
            currentItem = currentItem.getNext();
            ++i;
        }

        ListItem<T> newItem = new ListItem<>(data, currentItem.getNext());

        currentItem.setNext(newItem);

        ++size;
    }

    public boolean deleteItemByData(T data) {
        if (head == null) {
            throw new NoSuchElementException("List must not be empty!");
        }

        if (Objects.equals(head.getData(), data)) {
            head = head.getNext();
            --size;
            return true;
        }

        ListItem<T> current = head;

        while (current.getNext() != null) {
            if (Objects.equals(current.getNext().getData(), data)) {
                current.setNext(current.getNext().getNext());

                --size;

                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("List must not be empty!");
        }

        T deletedData = head.getData();

        head = head.getNext();
        --size;

        return deletedData;
    }

    public void flip() {
        if (head == null) {
            throw new NoSuchElementException("List must not be empty!");
        }

        ListItem<T> previousItem = null;
        ListItem<T> currentItem = head;
        ListItem<T> nextTemp;

        while (currentItem != null) {
            nextTemp = currentItem.getNext();
            currentItem.setNext(previousItem);
            previousItem = currentItem;
            currentItem = nextTemp;
        }

        head = previousItem;
    }

    public SingleLinkedList<T> copy() {

        ListItem<T> currentItem = head;

        SingleLinkedList<T> copyList = new SingleLinkedList<>();

        for (int i = 0; i < size; ++i) {
            copyList.add(currentItem.getData());
            currentItem = currentItem.getNext();
        }

        return copyList;
    }
}