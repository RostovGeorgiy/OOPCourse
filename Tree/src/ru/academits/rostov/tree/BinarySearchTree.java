package ru.academits.rostov.tree;

import ru.academits.rostov.tree_node.TreeNode;

import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> {
    private TreeNode<E> root;
    private int size;

    public BinarySearchTree() {
    }

    public BinarySearchTree(TreeNode<E> root) {
        this.root = root;
        ++size;
    }

    public int size() {
        return size;
    }

    public boolean add(E data) {
        if (root == null) {
            root = new TreeNode<>(data);
            ++size;

            return true;
        }

        return addRecursive(root, data);
    }

    private boolean addRecursive(TreeNode<E> node, E data) {
        if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new TreeNode<>(data));
                ++size;

                return true;
            }

            return addRecursive(node.getRight(), data);
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new TreeNode<>(data));
                ++size;

                return true;
            }

            return addRecursive(node.getLeft(), data);
        } else {
            System.out.println("Node already exists");
            return false;
        }
    }

    public boolean find(E data) {
        if (root == null) {
            return false;
        }

        if (root.getData() == data) {
            return true;
        }

        return findRecursive(root, data);
    }

    private boolean findRecursive(TreeNode<E> node, E data) {
        if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                return false;
            }

            return findRecursive(node.getRight(), data);
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                return false;
            }

            return findRecursive(node.getLeft(), data);
        } else {
            return true;
        }
    }

    private TreeNode<E> getSuccessorNode(TreeNode<E> node) {
        node = node.getRight();

        while (node != null && node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    public E delete(E data) {
        return deleteRecursive(root, data).getData();
    }

    private TreeNode<E> deleteRecursive(TreeNode<E> node, E data) {
        if (node == null) {
            return null;
        }

        if (node.getData().compareTo(data) > 0) {
            node.setLeft(deleteRecursive(node.getLeft(), data));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(deleteRecursive(node.getRight(), data));
        } else {
            --size;

            if (node.getLeft() == null) {
                return node.getRight();
            }

            if (node.getRight() == null) {
                return node.getLeft();
            }

            TreeNode<E> successor = getSuccessorNode(node);

            node.setData(successor.getData());
            node.setRight(deleteRecursive(node.getRight(), successor.getData()));
        }

        return node;
    }

    public void depthFirstSearch() {
        depthFirstSearchRecursive(root);
    }

    private void depthFirstSearchRecursive(TreeNode<E> node) {
        if (node == null) {
            return;
        }

        System.out.println(node.getData());
        depthFirstSearchRecursive(node.getLeft());
        depthFirstSearchRecursive(node.getRight());
    }

    public void depthFirstStack() {
        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<E> node = stack.pop();
            System.out.println(node.getData());

            if (node.getRight() != null) {
                stack.push(node.getRight());
            }

            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
    }

    public void widthFirstSearch() {
        if (root == null) {
            return;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<E> node = queue.poll();
            System.out.println(node.getData());

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
    }
}