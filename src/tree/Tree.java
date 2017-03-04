/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author John
 */
public class Tree implements java.io.Serializable {

    Scanner read = new Scanner(System.in);
    private Node root;
    private int sons = -1;
    private int height = 0;

    public void Tree() {
        this.setRoot(null);
    }

    public void add(String string, int level, int position) {
        int maxPosition = maxPosition(level);
        String positions = allPositions(maxPosition);
        if (position < maxPosition) {
            if (!exist(string, root, false)) {
                Node q = new Node(string);
                if (getRoot() == null && level == 0 && position == 0) {
                    setRoot(q);
                    setSons(getSons() + 1);
                } else {
                    Node p = getRoot(), antp = null;
                    String firstHalf = "", secondHalf = "";
                    int levelCounter = 0;
                    while (p != null) {
                        levelCounter++;
                        firstHalf = positions.substring(0, positions.length() / 2);
                        secondHalf = positions.substring(positions.length() / 2, positions.length());
                        antp = p;
                        if (firstHalf.contains("" + position)) {
                            p = p.getLeft();
                            positions = firstHalf;
                        } else {
                            p = p.getRight();
                            positions = secondHalf;
                        }
                    }
                    if (levelCounter == level) {
                        if (firstHalf.contains("" + position)) {
                            antp.setLeft(q);
                            setSons(getSons() + 1);
                        } else {
                            antp.setRight(q);
                            setSons(getSons() + 1);
                        }
                    }
                }
            }
        }
    }

    public void deleteNode(String string) {
        Node antp = findFather(string, root, null);
        if (antp != null) {
            if (antp.getRight() != null) {
                if (antp.getRight().getString().equals(string)) {
                    antp.setRight(null);
                }
            } else if (antp.getLeft() != null) {
                if (antp.getLeft().getString().equals(string)) {
                    antp.setLeft(null);
                }
            }
        }
    }

    public boolean exist(String string, Node p, boolean bool) {
        if (p != null) {
            if (p.getString().equals(string)) {
                bool = true;
            } else {
                bool = exist(string, p.getRight(), bool);
                if (bool == false) {
                    bool = exist(string, p.getRight(), bool);
                }
            }
        }
        return bool;
    }

    public Node findNode(String string, Node p, Node target) {
        if (p != null) {
            if (p.getString().equals(string)) {
                target = p;
            } else {
                target = findNode(string, p.getRight(), target);
                if (target == null) {
                    target = findNode(string, p.getRight(), target);
                }
            }
        }
        return target;
    }

    public Node findFather(String string, Node p, Node target) {
        if (p != null) {
            if (p.getRight() != null) {
                if (p.getRight().getString().equals(string)) {
                    target = p;
                }
            } else if (p.getLeft() != null) {
                if (p.getLeft().getString().equals(string)) {
                    target = p;
                }
            } else {
                target = findFather(string, p.getRight(), target);
                if (target == null) {
                    target = findFather(string, p.getRight(), target);
                }
            }
        }
        return target;
    }

    public int maxPosition(int level) {
        int maxPosition = 1;
        for (int i = 0; i < level; i++) {
            maxPosition = maxPosition + maxPosition * 2;
        }
        return maxPosition;
    }

    public String allPositions(int maxPositions) {
        String positions = "";
        for (int i = 0; i < maxPositions; i++) {
            positions = positions + i;
        }
        return positions;
    }

    //GETTERS SETTERS
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node aRoot) {
        root = aRoot;
    }

    public int getSons() {
        return sons;
    }

    public void setSons(int aSons) {
        sons = aSons;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int aHeight) {
        height = aHeight;
    }

}
