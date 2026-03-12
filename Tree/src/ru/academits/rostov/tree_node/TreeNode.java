package ru.academits.rostov.tree_node;

public class TreeNode<E> {
    private TreeNode<E> left;
    private TreeNode<E> right;
    private E data;

    public TreeNode(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public TreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<E> node) {
        left = node;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> node) {
        right = node;
    }

    public void setData(E data) {
        this.data = data;
    }
}