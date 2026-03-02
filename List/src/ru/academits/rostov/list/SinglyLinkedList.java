package ru.academits.rostov.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<E> {
    private ListItem<E> head;
    private int size;

    public SinglyLinkedList() {
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }
    }

    private ListItem<E> getItemByIndex(int index) {
        int i = 0;

        ListItem<E> item = head;

        while (i < index) {
            item = item.getNext();
            ++i;
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append('[');

        for (ListItem<E> item = head; item != null; item = item.getNext()) {
            stringBuilder.append(item.getData()).append(", ");
        }

        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        stringBuilder.append(']');


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

        SinglyLinkedList<?> list = (SinglyLinkedList<?>) o;

        if (size != list.size) {
            return false;
        }

        ListItem<E> thisCurrentItem = head;
        ListItem<?> otherCurrentItem = list.head;

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
        final int prime = 37;
        int hash = 1;

        ListItem<E> currentItem = head;

        while (currentItem != null) {
            hash = prime * hash + (currentItem.getData() != null ? currentItem.getData().hashCode() : 0);
            currentItem = currentItem.getNext();
        }

        return hash;
    }

    public int getSize() {
        return size;
    }

    public E getDataByIndex(int index) {
        checkIndex(index);

        return getItemByIndex(index).getData();
    }

    public E setDataByIndex(int index, E data) {
        checkIndex(index);

        ListItem<E> currentItem = getItemByIndex(index);

        E oldData = currentItem.getData();
        currentItem.setData(data);

        return oldData;
    }

    public E deleteByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return deleteFirst();
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);

        ListItem<E> itemToDelete = previousItem.getNext();

        E deletedData = itemToDelete.getData();

        previousItem.setNext(itemToDelete.getNext());

        --size;

        return deletedData;
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        }

        return head.getData();
    }

    public void addFirst(E data) {
        head = new ListItem<>(data, head);
        ++size;
    }

    public void add(E data) {
        addByIndex(size, data);
    }

    public void addByIndex(int index, E data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and <= list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        if (index == 0) {
            addFirst(data);

            return;
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);

        ListItem<E> newItem = new ListItem<>(data, previousItem.getNext());

        previousItem.setNext(newItem);

        ++size;
    }

    public boolean deleteByData(E data) {
        if (head == null) {
            return false;
        }

        if (Objects.equals(data, head.getData())) {
            deleteFirst();

            return true;
        }

        for (ListItem<E> currentItem = head.getNext(), previousItem = head;
             currentItem != null;
             previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (Objects.equals(data, currentItem.getData())) {
                previousItem.setNext(currentItem.getNext());

                return true;
            }
        }

        return false;
    }

    public E deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        }

        E deletedData = head.getData();

        head = head.getNext();
        --size;

        return deletedData;
    }

    public void flip() {
        ListItem<E> previousItem = null;
        ListItem<E> currentItem = head;

        while (currentItem != null) {
            ListItem<E> nextItem = currentItem.getNext();
            currentItem.setNext(previousItem);
            previousItem = currentItem;
            currentItem = nextItem;
        }

        head = previousItem;
    }

    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> copyList = new SinglyLinkedList<>();

        if (head == null) {
            return copyList;
        }

        copyList.head = new ListItem<>(head.getData());

        ListItem<E> currentItem = head.getNext();
        ListItem<E> currentCopyItem = copyList.head;

        while (currentItem != null) {
            currentCopyItem.setNext(new ListItem<>(currentItem.getData()));
            currentCopyItem = currentCopyItem.getNext();
            currentItem = currentItem.getNext();
        }

        copyList.size = size;

        return copyList;
    }
}