package ru.academits.rostov.main;

import ru.academits.rostov.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.addFirst("This");
        list.add("is a");
        list.add("string1");

        System.out.println("Getting data by index: " + list.getDataByIndex(2));
        System.out.println("Setting new data by index. Old data: " + list.setDataByIndex(2, "string"));

        System.out.println(list);

        System.out.println(list.deleteByIndex(1));

        System.out.println(list);

        list.addByIndex(1, "is changed");

        list.addByIndex(0, "!");
        System.out.println(list.deleteByData("avb"));

        list.flip();

        SinglyLinkedList<String> copyList = list.copy();

        System.out.println("Base list: " + list);

        list.setDataByIndex(1, "new");
        System.out.println("Copy list: " + copyList);

        System.out.println("Deleting first item in copy list: " + copyList.deleteFirst());

        list.deleteByData("!");

        copyList.addByIndex(copyList.getSize(), "someData");

        System.out.println(copyList);

        System.out.println(new SinglyLinkedList<>().copy());

    }
}