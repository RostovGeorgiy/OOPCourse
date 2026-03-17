package ru.academits.rostov.tree_main;

import ru.academits.rostov.tree.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(null);
        tree.add(7);
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(10);
        tree.add(11);
        tree.add(12);

        System.out.println(tree.find(7));
        System.out.println(tree.find(20));

        System.out.println("Amount of nodes: " + tree.size());

        System.out.println("Testing delete method:");
        System.out.println(tree.delete(60));
        System.out.println(tree.delete(4));

        System.out.println("Testing depth-first search(recursive).");
        tree.depthFirstSearchRecursive();

        System.out.println("Testing depth-first search(using stack).");
        tree.depthFirstSearch(System.out::println);

        System.out.println("Testing breadth-first search.");
        tree.breadthFirstSearch(System.out::println);

        System.out.println("Testing size method.");
        System.out.println("Amount of nodes: " + tree.size());

        System.out.println("To string method: " + tree);
    }
}