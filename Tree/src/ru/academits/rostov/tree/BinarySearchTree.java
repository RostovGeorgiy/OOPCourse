package ru.academits.rostov.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    private TreeNode<E> root;
    private int size;
    Comparator<? super E> nodeDataComparator;

    public BinarySearchTree() {
        //noinspection unchecked
        nodeDataComparator = Comparator.nullsFirst((Comparator<E>) Comparator.naturalOrder());
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        nodeDataComparator = comparator;
    }

    private int compare(E data1, E data2) {
        return nodeDataComparator.compare(data1, data2);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        Deque<E> stack = new LinkedList<>();
        boolean isFirst = true;

        depthFirstSearchRecursive(root, stack::add);

        while (!stack.isEmpty()) {
            if (!isFirst) {
                stringBuilder.append(", ");
            }

            isFirst = false;

            stringBuilder.append(stack.remove());
        }

        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    public int size() {
        return size;
    }

    public void add(E data) {
        ++size;
        TreeNode<E> newNode = new TreeNode<>(data);

        if (root == null) {
            root = newNode;
            return;
        }

        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = null;

        while (currentNode != null) {
            parentNode = currentNode;

            if (compare(data, currentNode.getData()) < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        if (compare(data, parentNode.getData()) < 0) {
            parentNode.setLeft(newNode);
        } else {
            parentNode.setRight(newNode);
        }
    }

    public boolean contains(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> node = root;

        while (node != null) {
            int comparisonResult = compare(data, node.getData());

            if (comparisonResult == 0) {
                return true;
            }

            if (comparisonResult < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        return false;
    }

    public boolean remove(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = null;

        while (currentNode != null && compare(currentNode.getData(), data) != 0) {
            parentNode = currentNode;

            if (compare(data, currentNode.getData()) < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        if (currentNode == null) {
            return false;
        }

        --size;

        if (currentNode.getLeft() == null || currentNode.getRight() == null) {
            TreeNode<E> child = (currentNode.getLeft() != null) ? currentNode.getLeft() : currentNode.getRight();

            replaceNode(parentNode, currentNode, child);
        } else {
            TreeNode<E> successorParent = currentNode;
            TreeNode<E> successor = currentNode.getRight();

            while (successor.getLeft() != null) {
                successorParent = successor;

                successor = successor.getLeft();
            }

            if (successorParent != currentNode) {
                successorParent.setLeft(successor.getRight());

                successor.setRight(currentNode.getRight());
            }

            successor.setLeft(currentNode.getLeft());

            replaceNode(parentNode, currentNode, successor);
        }

        return true;
    }

    private void replaceNode(TreeNode<E> parent, TreeNode<E> oldNode, TreeNode<E> newNode) {
        if (parent == null) {
            root = newNode;
        } else if (parent.getLeft() == oldNode) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    public void depthFirstSearchRecursive() {
        depthFirstSearchRecursive(root, System.out::println);
    }

    private void depthFirstSearchRecursive(TreeNode<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        consumer.accept(node.getData());

        depthFirstSearchRecursive(node.getLeft(), consumer);
        depthFirstSearchRecursive(node.getRight(), consumer);
    }

    public void depthFirstSearch(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<E> node = stack.pop();

            consumer.accept(node.getData());

            if (node.getRight() != null) {
                stack.push(node.getRight());
            }

            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
    }

    public void breadthFirstSearch(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<E> node = queue.remove();

            consumer.accept(node.getData());

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
    }
}