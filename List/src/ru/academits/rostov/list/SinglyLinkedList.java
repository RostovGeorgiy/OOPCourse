package ru.academits.rostov.list;

import java.util.Objects;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public SinglyLinkedList() {
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < list size. Current index: "
                    + index + " and size: " + size + ".");
        }
    }

    private ListItem<T> getItemByIndex(int index) {
        int i = 0;

        ListItem<T> item = head;

        while (i < index) {
            item = item.getNext();
            ++i;
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        for (ListItem<T> item = head; item != null; item = item.getNext()) {
            stringBuilder.append(item.getData());

            if (item.getNext() != null) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");

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

        if (size != list.getSize()) {
            return false;
        }

        ListItem<T> thisCurrentItem = head;
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

        ListItem<T> currentItem = head;

        while (currentItem != null) {
            hash = prime * hash + (currentItem.getData() != null ? currentItem.getData().hashCode() : 0);
            currentItem = currentItem.getNext();
        }

        return hash;
    }

    public int getSize() {
        return size;
    }

    public T getDataByIndex(int index) {
        checkIndex(index);

        int i = 0;

        ListItem<T> currentItem = head;

        while (i < index) {
            currentItem = currentItem.getNext();
            ++i;
        }

        return currentItem.getData();
    }

    public T setDataByIndex(int index, T data) {
        checkIndex(index);

        ListItem<T> currentItem = getItemByIndex(index);

        T oldData = currentItem.getData();
        currentItem.setData(data);

        return oldData;
    }

    public T deleteItemByIndex(int index) {
        checkIndex(index);

        T deletedData;

        if (index == 0) {
            return deleteFirst();
        } else {
            ListItem<T> targetItem = getItemByIndex(index - 1);

            ListItem<T> nextTargetItem = targetItem.getNext();

            deletedData = nextTargetItem.getData();

            targetItem.setNext(nextTargetItem.getNext());

            --size;
        }

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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and <= list size. Current index: "
                    + index + " and size: " + size + ".");
        }

        if (index == 0) {
            insertFirst(data);

            return;
        }

        if (index == size) {
            add(data);

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
        if (Objects.equals(head.getData(), data)) {
            deleteFirst();

            return true;
        }

        for (ListItem<T> currentItem = head, previousItem = null;
             currentItem != null;
             previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (currentItem.getData().equals(data)) {
                assert previousItem != null;
                previousItem.setNext(currentItem.getNext());
            }
        }

        return false;
    }

    public T deleteFirst() {
        T deletedData = head.getData();

        head = head.getNext();
        --size;

        return deletedData;
    }

    public void flip() {
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

    public SinglyLinkedList<T> copy() {
        SinglyLinkedList<T> copyList = new SinglyLinkedList<>();

        copyList.head = new ListItem<>(head.getData());

        ListItem<T> currentItem = head.getNext();
        ListItem<T> currentCopyItem = copyList.head;

        while (currentItem != null) {
            currentCopyItem.setNext(new ListItem<>(currentItem.getData()));
            currentCopyItem = currentCopyItem.getNext();
            currentItem = currentItem.getNext();

        }

        copyList.size = size;

        return copyList;
    }
}