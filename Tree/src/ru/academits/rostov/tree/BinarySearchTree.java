package ru.academits.rostov.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    private TreeNode<E> root;
    private int size;
    private final Comparator<? super E> comparator;

    public BinarySearchTree() {
        //noinspection unchecked
        comparator = Comparator.nullsFirst((Comparator<E>) Comparator.naturalOrder());
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        depthFirstSearchRecursive(root, data -> stringBuilder.append(data).append(", "));

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "]");

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

            if (comparator.compare(data, currentNode.getData()) < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        if (comparator.compare(data, parentNode.getData()) < 0) {
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
            int dataComparisonResult = comparator.compare(data, node.getData());

            if (dataComparisonResult == 0) {
                return true;
            }

            if (dataComparisonResult < 0) {
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

        while (currentNode != null) {
            int dataComparisonResult = comparator.compare(currentNode.getData(), data);

            if (dataComparisonResult == 0) {
                break;
            }

            parentNode = currentNode;

            if (dataComparisonResult > 0) {
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

    public void depthFirstSearchRecursive(Consumer<E> consumer) {
        depthFirstSearchRecursive(root, consumer);
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