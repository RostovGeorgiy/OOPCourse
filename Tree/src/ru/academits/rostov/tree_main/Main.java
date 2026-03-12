package ru.academits.rostov.tree_main;

import ru.academits.rostov.tree.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(10);
        tree.add(11);
        tree.add(12);

        System.out.println(tree.find(7));
        System.out.println(tree.find(20));

        System.out.println("Testing delete method:");
        System.out.println(tree.delete(60));
        System.out.println(tree.delete(4));

        System.out.println("Testing depth-first search(recursive).");
        tree.depthFirstSearch();

        System.out.println("Testing depth-first search(using stack).");
        tree.depthFirstStack();

        System.out.println("Testing width-first search.");
        tree.widthFirstSearch();

        System.out.println("Testing size method.");
        System.out.println(tree.size());
    }
}