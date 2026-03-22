package ru.academits.rostov.hashtable_main;

import ru.academits.rostov.hashtable.CustomHashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        CustomHashTable<Integer> hashTable = new CustomHashTable<>();
        hashTable.add(1);
        hashTable.add(null);
        hashTable.add(10);

        System.out.println("Testing toArray method.");
        Object[] array = hashTable.toArray();
        System.out.println(Arrays.toString(array));

        System.out.println("Testing remove method.");
        System.out.println(hashTable.remove(3));
        System.out.println(hashTable);

        System.out.println("Testing addAll method.");
        System.out.println(hashTable.addAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(hashTable);

        System.out.println("Testing removeAll method.");
        System.out.println(hashTable.removeAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(Arrays.toString(hashTable.toArray()));

        Iterator<Integer> tableIterator = hashTable.iterator();
        System.out.println("Testing iterator.");

        while (tableIterator.hasNext()) {
            System.out.println(tableIterator.next());
        }

        System.out.println("Testing retainAll method.");
        System.out.println(hashTable.retainAll(new ArrayList<>(Arrays.asList(2, 3, 5, 20))));
        System.out.println("HastTable is empty: " + hashTable);
    }
}