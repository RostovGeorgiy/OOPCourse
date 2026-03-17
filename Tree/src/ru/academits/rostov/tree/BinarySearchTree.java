package ru.academits.rostov.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    private TreeNode<E> root;
    private int size;
    private final Comparator<? super E> comparator;

    public BinarySearchTree() {
        comparator = null;
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private int compare(E data1, E data2) {
        Comparator<? super E> nodeDataComparator;
        //noinspection unchecked
        nodeDataComparator = Comparator.nullsFirst(Objects.requireNonNullElseGet(this.comparator, () -> (Comparator<E>) Comparator.naturalOrder()));

        return nodeDataComparator.compare(data1, data2);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> current = root;
        boolean first = true;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }

            current = stack.pop();

            if (!first) {
                stringBuilder.append(", ");
            }

            stringBuilder.append(current.getData());

            first = false;

            current = current.getRight();
        }

        stringBuilder.append("]");

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

        TreeNode<E> current = root;
        TreeNode<E> parent = null;

        while (current != null) {
            parent = current;

            if (compare(data, current.getData()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        if (compare(data, parent.getData()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    public boolean find(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> node = root;

        while (!Objects.equals(node, null)) {
            if (compare(data, node.getData()) == 0) {
                return true;
            }

            if (compare(data, node.getData()) < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        return false;
    }

    public E delete(E data) {
        TreeNode<E> current = root;
        TreeNode<E> parent = null;

        while (current != null && current.getData() != data) {
            parent = current;

            if (compare(data, current.getData()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        if (current == null) {
            return null;
        }

        E deletedNodeData = current.getData();
        --size;

        if (current.getLeft() == null || current.getRight() == null) {
            TreeNode<E> child = (current.getLeft() != null) ? current.getLeft() : current.getRight();

            replaceNode(parent, current, child);
        } else {
            TreeNode<E> successorParent = current;
            TreeNode<E> successor = current.getRight();

            while (successor.getLeft() != null) {
                successorParent = successor;

                successor = successor.getLeft();
            }

            if (successorParent != current) {
                successorParent.setLeft(successor.getRight());

                successor.setRight(current.getRight());
            }

            successor.setLeft(current.getLeft());

            replaceNode(parent, current, successor);
        }

        return deletedNodeData;
    }

    private void replaceNode(TreeNode<E> parent, TreeNode<E> oldNode, TreeNode<E> newNode) {
        if (parent == null) {
            root = newNode;
        } else if (Objects.equals(parent.getLeft(), oldNode)) {
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

        depthFirstSearchRecursive(node.getLeft(), System.out::println);
        depthFirstSearchRecursive(node.getRight(), System.out::println);
    }

    public void depthFirstSearch(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new ArrayDeque<>();
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