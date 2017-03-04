/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

/**
 *
 * @author John
 */
public class Node implements java.io.Serializable {

    private String string;
    private Node left, right;
    private int level, position;

    public Node(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public Node getLeft() {
        try {
            return left;
        } catch (Exception ex) {
            return null;
        }
    }

    public Node getRight() {
        try {
            return right;
        } catch (Exception ex) {
            return null;
        }
    }

    public int getLevel() {
        try {
            return level;
        } catch (Exception ex) {
            return -1;
        }
    }

    public int getPosition() {
        try {
            return position;
        } catch (Exception ex) {
            return -1;
        }
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
