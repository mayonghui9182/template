package cn.net.mayh.BinaryTree;

public class BinaryTree {

    public static void main(String[] args) {

        final int[] values = {1, 3, 4, 5, 2, 8, 6, 7, 9, 0};
        Node node = new Node(values[0]);
        for (int i = 1; i < values.length; i++) {
            createBinaryTree(node, values[i]);
        }
        inOrderTransval(node);
    }

    public static Node createBinaryTree(Node node, int value) {
        if (node.getValue() > value) {
            if (node.getLeft() == null) {
                node.setLeft(new Node(value));
            }else{
                createBinaryTree(node.getLeft(),value);
            }
        }else{
            if (node.getRight() == null) {
                node.setRight(new Node(value));
            }else{
                createBinaryTree(node.getRight(),value);
            }
        }
        return node;
    }

    public static void inOrderTransval(Node node) {
        if (node == null) {
            return;
        } else {
            inOrderTransval(node.getLeft());
            System.out.print(node.getValue());
            System.out.print(",");
            inOrderTransval(node.getRight());
        }
    }
}

class Node {

    // 节点值
    private int value;

    // 左节点
    private Node left;

    // 右节点
    private Node right;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}