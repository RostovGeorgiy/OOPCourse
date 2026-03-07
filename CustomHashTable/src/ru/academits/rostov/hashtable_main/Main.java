package ru.academits.rostov.hashtable_main;

import ru.academits.rostov.hashtable.CustomHashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        CustomHashTable<Integer> hashTable = new CustomHashTable<>();
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);

        CustomHashTable<Integer> hashTable1 = new CustomHashTable<>(20);
        hashTable1.add(2);
        hashTable1.add(1);
        hashTable1.add(3);
        hashTable1.add(4);
        hashTable1.remove(4);

        System.out.println("Testing equals");
        System.out.println(hashTable.equals(hashTable1));

        System.out.println("Testing toArray method.");

        Object[] array = hashTable.toArray();
        System.out.println(Arrays.toString(array));

        System.out.println("Testing remove method.");

        System.out.println(hashTable.remove(2));

        System.out.println("Testing addAll method.");

        System.out.println(hashTable.addAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(hashTable);

        System.out.println("Testing removeAll method.");
        System.out.println(hashTable.removeAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(Arrays.toString(hashTable.toArray()));


        hashTable.addAll(new ArrayList<>(Arrays.asList(10, 20, 30, 10, 20, 30, 10, 20, 30, 10, 20, 30, 10, 20, 30)));
        System.out.println("Resized hashtable: " + hashTable);

        Iterator<Integer> tableIterator = hashTable.iterator();

        while (tableIterator.hasNext()) {
            System.out.println(tableIterator.next());
        }
    }
}