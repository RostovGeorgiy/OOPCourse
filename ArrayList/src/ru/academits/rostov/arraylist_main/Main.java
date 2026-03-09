package ru.academits.rostov.arraylist_main;

import ru.academits.rostov.arraylist.CustomArrayList;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        CustomArrayList<Integer> list = new CustomArrayList<>(15);

        list.add(1);
        list.add(2);
        list.add(null);

        System.out.println("Equals: " + list.equals(new CustomArrayList<>(Arrays.asList(2, 1, null))));

        Integer[] array = new Integer[3];

        array = list.toArray(array);

        System.out.println("toArray(a) method:");
        System.out.println(Arrays.toString(array));

        System.out.println(list.set(2, 8));

        System.out.println(list);

        Integer integer = list.get(1);

        System.out.println(integer);

        list.set(1, 100);

        list.remove((Integer) 3);

        System.out.println(list);

        List<Integer> list1 = new java.util.ArrayList<>(Arrays.asList(1, 2, 3));

        System.out.println(list.size() + " " + list1.size());

        System.out.println(list.addAll(0, list1));

        System.out.println("List after addAll method:");
        System.out.println(list);

        System.out.println(list.contains(100));

        System.out.println("Testing containsAll method:");
        System.out.println(list.containsAll(new java.util.ArrayList<>(Arrays.asList(3, 2, 8))));

        System.out.println("Testing addAll method:");
        System.out.println(list.addAll(new java.util.ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(list);

        System.out.println("Testing removeAll method:");
        System.out.println(list.removeAll(new java.util.ArrayList<>(Arrays.asList(30, 100, 5))));
        System.out.println(list);

        System.out.println("Testing add(index) method:");
        list.add(3, 50);
        System.out.println(list);

        System.out.println("Testing remove method:");
        list.remove(2);
        System.out.println(list);

        System.out.println("Creating iterator.");

        Iterator<Integer> intIterator = list.iterator();

        while (intIterator.hasNext()) {
            System.out.println(intIterator.next());
        }

        list.addAll(4, new java.util.ArrayList<>(Arrays.asList(100, 200, 300)));
        list.removeLast();
        System.out.println(list);

        CustomArrayList<Integer> list2 = new CustomArrayList<>(new java.util.ArrayList<>(Arrays.asList(100, 200, 300)));
        System.out.println(list2);

        list2.clear();
        System.out.println(list2);
    }
}