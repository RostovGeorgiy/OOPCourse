package ru.academits.rostov.hashtable_main;

import ru.academits.rostov.hashtable.CustomHashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        CustomHashTable<Integer> hashTable1 = new CustomHashTable<>();
        hashTable1.add(1);
        hashTable1.add(null);
        hashTable1.add(3);

        CustomHashTable<Integer> hashTable2 = new CustomHashTable<>(20);
        hashTable2.add(null);
        hashTable2.add(1);
        hashTable2.add(3);
        hashTable2.add(4);
        hashTable2.remove(4);

        System.out.println("Testing equals");
        System.out.println(hashTable1.equals(hashTable2));

        System.out.println("Testing toArray method.");

        Object[] array = hashTable1.toArray();
        System.out.println(Arrays.toString(array));

        System.out.println("Testing remove method.");

        System.out.println(hashTable1.remove(153));
        System.out.println(hashTable1);

        System.out.println("Testing addAll method.");

        System.out.println(hashTable1.addAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(hashTable1);

        System.out.println("Testing removeAll method.");
        System.out.println(hashTable1.removeAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(Arrays.toString(hashTable1.toArray()));

        hashTable1.addAll(new ArrayList<>(Arrays.asList(10, 20, 30, 10, 20, 30, 10, 20, 30, 10, 20, 30, 10, 20, 30)));
        System.out.println("Resized hashtable: " + hashTable1);

        Iterator<Integer> tableIterator = hashTable1.iterator();
        
        System.out.println(tableIterator.next());
    }
}