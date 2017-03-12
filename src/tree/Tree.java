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
    private int sons = -1, height = 0;
    private LinkedList<Node> stack = new LinkedList<>();

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
                    q.setLevel(0);
                    q.setPosition(0);
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
                        if (levelCounter == level && p != null) {
                            p = null;
                            levelCounter = -1;
                        }
                    }
                    if (levelCounter == level) {
                        q.setLevel(level);
                        q.setPosition(position);
                        setSons(getSons() + 1);
                        if (firstHalf.contains("" + position)) {
                            antp.setLeft(q);
                        } else {
                            antp.setRight(q);
                        }
                    }
                }
            }
        }
        height(root, 0);
    }

    public void deleteNode(String string) {
        Node p = findNode(string, root, null);
        Node antp = findFather(string, root, null);
        if (antp != null) {
            if (antp.getRight() != null) {
                if (antp.getRight().getString().equals(string)) {
                    antp.setRight(null);
                }
            }
            if (antp.getLeft() != null) {
                if (antp.getLeft().getString().equals(string)) {
                    antp.setLeft(null);
                }
            }
        } else if (p != null) {
            root = null;
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
                target = findNode(string, p.getLeft(), target);
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
            }
            if (p.getLeft() != null) {
                if (p.getLeft().getString().equals(string)) {
                    target = p;
                }
            }
            if (target == null) {
                target = findFather(string, p.getRight(), target);
                if (target == null) {
                    target = findFather(string, p.getLeft(), target);
                }
            }
        }
        return target;
    }

    public int maxPosition(int level) {
        //for (int i = 0; i < level; i++) {
        //    maxPosition = maxPosition * 2;
        //}
        return (int) Math.pow(2, level);
    }

    public String allPositions(int maxPositions) {
        String positions = "";
        for (int i = 0; i < maxPositions; i++) {
            if (i < 10) {
                positions = positions + i + "-";
            } else {
                positions = positions + i;
            }
        }
        return positions;
    }

    private boolean leaf(Node p) {
        if (p != null) {
            if (p.getLeft() != null || p.getRight() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    private void height(Node p, int num) {
        if (p != null) {
            //if (num > height) { //Doesnt matter...
            //    height = num;
            //}
            height(p.getLeft(), num + 1);
            if (num > height) {
                height = num;
            }
            height(p.getRight(), num + 1);
        }
    }

    public String run(boolean direction) {
        String string = "Arbol vacio";
        if (root != null) {
            Node p = null;
            if (stack.isEmpty()) {
                stack.add(root);
            }
            if (!direction) {
                p = stack.getLast().getLeft();
            } else {
                p = stack.getLast().getRight();
            }
            if (p != null) {
                string = p.getString();
                stack.add(p);
            } else {
                string = "No se!";
            }
        }
        return string;
    }

    public void learn(String question, String answer) {
        if (!stack.isEmpty()) {
            Node qstn = new Node(question), answr = new Node(answer), p = stack.getLast();
            qstn.setLevel(p.getLevel() + 1);                        //I can use method add too, but I dont care...
            qstn.setPosition((p.getPosition() + 1) * 2 - 2);        //(p.getPosition() + 1)*2 - 1 Right son...

            answr.setLevel(qstn.getLevel() + 1);
            answr.setPosition((qstn.getPosition() + 1) * 2 - 1);    //(p.getPosition() + 1)*2 - 2 Left son...

            qstn.setRight(answr);

            p.setLeft(qstn);
            height(root, 0);
            stack.clear();
        }
    }

    public LinkedList<Node> biggerBranch(Node p, LinkedList<Node> list) {
        LinkedList<Node> bigger = new LinkedList<>();
        if (p != null) {
            list.add(p);
            LinkedList<Node> aux1 = biggerBranch(p.getLeft(), list);
            LinkedList<Node> aux2 = biggerBranch(p.getRight(), list);
            int a, b;
            try {
                a = aux1.size();
            } catch (Exception e) {
                a = 0;
            }
            try {
                b = aux2.size();
            } catch (Exception e) {
                b = 0;
            }
            if (a > b) {
                bigger.addAll(aux1);
            } else {
                bigger.addAll(aux2);
            }
            //list.remove(p);
            list.pollLast();
        } else {
            bigger.addAll(list);
        }
        return bigger;
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

    public LinkedList<Node> getStack() {
        return stack;
    }

    public void setStack(LinkedList<Node> stack) {
        this.stack = stack;
    }

}
