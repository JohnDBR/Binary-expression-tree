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
public class Tree {

    static Scanner read = new Scanner(System.in);
    private static Node root;
    private static int sons = -1;
    private static int height = 0;

    public void Tree() {
        this.setRoot(null);
    }

    static public void add(String string, int level, int position) {
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

    static public void deleteNode(String string) {
        Node antp = findFather(string, root, null);
        if (antp != null) {
            if (antp.getRight() != null) {
                if (antp.getRight().getString().equals(string)) {
                    antp.setRight(null);
                }
            }else if (antp.getLeft()!= null) {
                if (antp.getLeft().getString().equals(string)) {
                    antp.setLeft(null);
                }
            }
        }
    }

    static public boolean exist(String string, Node p, boolean bool) {
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

    static public Node findNode(String string, Node p, Node target) {
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

    static public Node findFather(String string, Node p, Node target) {
        if (p != null) {
            if (p.getRight() != null) {
                if (p.getRight().getString().equals(string)) {
                    target = p;
                }
            }else if (p.getLeft()!= null) {
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

    static public int maxPosition(int level) {
        int maxPosition = 1;
        for (int i = 0; i < level; i++) {
            maxPosition = maxPosition + maxPosition * 2;
        }
        return maxPosition;
    }

    static public String allPositions(int maxPositions) {
        String positions = "";
        for (int i = 0; i < maxPositions; i++) {
            positions = positions + i;
        }
        return positions;
    }

    //GETTERS SETTERS
    public static Node getRoot() {
        return root;
    }

    public static void setRoot(Node aRoot) {
        root = aRoot;
    }

    public static int getSons() {
        return sons;
    }

    public static void setSons(int aSons) {
        sons = aSons;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int aHeight) {
        height = aHeight;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int op, num;
        do {
            System.out.println(
                    "NOTA: el valor -1 en mi laboratorio es nulo, vacio\n"
                    + "porfavor usar arboles de numeros positivos\n\n"
                    + "Opciones:\n"
                    + " 1. Crear Arbol\n"
                    + " 2. Mostrar Arbol\n"
                    + " 3. Informacion detallada del nodo (punto 2 y punto 5)\n"
                    + " 4. Eliminar nodo\n"
                    + " 5. Agregar nodo\n"
                    + " 0. Salir"
            );
            System.out.println("OPCION: ");
            op = read.nextInt();
            System.out.println("");
            switch (op) {
                case 1:
                    setRoot(null);
                    createTree();
                    break;
                case 2:
                    showTree();
                    break;
                case 3:
                    clean();
                    System.out.println("Nodo:");
                    num = read.nextInt();
                    System.out.println("");
                    graphicTree(getRoot());
                    System.out.println("");
                    Node p = findNode(num);
                    height(p, 0);
                    offspring(p);
                    System.out.println(leaf(p) + "- hoja");
                    System.out.println(getHeight() + "- altura");
                    System.out.println(getSons() + "- descendencia");
                    System.out.println(nodes(p) + "- nodos del arbol/sub-arbol");
                    System.out.println(level(num) + "- nivel");
                    System.out.println(uncle(num) + "- tio");
                    break;
                case 4:
                    System.out.println("Nodo:");
                    num = read.nextInt();
                    deleteNode(num);
                    break;
                case 5:
                    createTree();
                    break;
                case 6:

                    break;
                default:
                    op = 0;
                    break;
            }
            System.out.println("\n");
            //System.out.println("\n\n");
        } while (op != 0);
    }

}
