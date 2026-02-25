package ru.academits.rostov.main;

import ru.academits.rostov.list.SingleLinkedList;

public class Main {
    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.insertFirst("This");
        list.add(" is a ");
        list.add("string1");

        System.out.println("Getting data by index: " + list.getDataByIndex(2));
        System.out.println("Setting new data by index. Old data: " + list.setDataByIndex(2, "string"));

        System.out.println(list);

        System.out.println(list.deleteItemByIndex(1));

        System.out.println(list);

        list.insertItemByIndex(1, " is changed ");

        list.insertItemByIndex(0, "!");
        System.out.println(list.deleteItemByData("avb"));

        list.flip();

        SingleLinkedList<String> copyList = list.copy();

        copyList.deleteItemByData("This");
        System.out.println(list);

        System.out.println(copyList);
        System.out.println("Deleting first item in copy list: " + copyList.deleteFirst());

        System.out.println(copyList);
    }
}