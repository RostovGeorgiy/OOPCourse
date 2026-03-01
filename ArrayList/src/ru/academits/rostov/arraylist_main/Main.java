package ru.academits.rostov.arraylist_main;

import ru.academits.rostov.arraylist.MyArrayList;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(3);
        list.add(2);
        list.add(1);

        Integer[] array = new Integer[2];

        array = list.toArray(array);
        System.out.println(Arrays.toString(array));

        System.out.println(list.set(2, 8));

        System.out.println(Arrays.toString(array));

        Integer integer = list.get(1);

        System.out.println(integer);

        list.set(1, 100);

        list.remove((Integer) 3);

        System.out.println(list);

        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));

        System.out.println(list.size() + " " + list1.size());

        System.out.println(list.addAll(list1));

        System.out.println(list.size() + " " + list1.size());

        System.out.println(list);

        System.out.println("Testing containsAll method:");
        System.out.println(list.containsAll(new ArrayList<>(Arrays.asList(3, 2, 8))));

        System.out.println("Testing addAll method:");
        System.out.println(list.addAll(new ArrayList<>(Arrays.asList(10, 20, 30))));
        System.out.println(list);

        System.out.println("Testing removeAll method:");
        System.out.println(list.removeAll(new ArrayList<>(Arrays.asList(30, 100, 5))));
        System.out.println(list);

        System.out.println("Testing add(index) method:");
        list.add(2, 50);
        System.out.println(list);

        System.out.println("Testing remove method:");
        list.remove(2);
        System.out.println(list);

        System.out.println("Creating iterator.");

        Iterator<Integer> intIterator = list.iterator();

        while (intIterator.hasNext()) {
            System.out.println(intIterator.next());
        }

        list.addAll(4, new ArrayList<>(Arrays.asList(100, 200, 300)));
        list.removeLast();
        System.out.println(list);
    }
}