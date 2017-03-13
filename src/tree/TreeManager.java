/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import linkedlist.LinkedList;
import java.util.Scanner;
import tree.Tree;

/**
 *
 * @author John
 */
public class TreeManager {

    static Scanner read = new Scanner(System.in);
    static Tree tree = new Tree();
    static int sons = -1, height = 0;
    static LinkedList<String> levels = new LinkedList<>();

    static public void createTree(Tree tree) {
        String string;
        int op = 1, level = 0, position = 0;
        do {
            System.out.println("Digite info del nodo: (contenido Enter nivel Enter posicion Enter)");
            string = read.next();
            level = read.nextInt();
            position = read.nextInt();
            tree.add(string, level, position);
            System.out.println("Desea ingresar mas nodos 1-Si, 0-No");
            op = read.nextInt();
        } while (op == 1);
        //height(tree.getRoot(), 0);
        //tree.setHeight(height);
    }

    static public void showTree(Tree tree) {
        Node root = tree.getRoot();
        LinkedList<String> travel = new LinkedList<>();
        System.out.println("Mostrar Arbol por 1.pre-orden - 2.in-orden 3.post-orden - 4.nivel-orden - 5.intento grafico");
        System.out.println("OPCION: ");
        int op = read.nextInt();
        System.out.println("");
        switch (op) {
            case 1:
                preOrder(root, travel);
                break;
            case 2:
                inOrder(root, travel);
                break;
            case 3:
                postOrder(root, travel);
                break;
            case 4:
                levelOrder(root, travel);
                break;
            case 5:
                //int num = read.nextInt(); //fix, cousins from uncles...
                //Node p = findNode(num);
                graphicTree(tree, root);
                break;
            default:
                break;
        }
        for (int i = 0; i < travel.size(); i++) {
            String string = travel.get(i);
            System.out.print(string + "-");
        }
        //for (String string : travel) {
        //    System.out.print(string + "-");
        //}
    }

    static public void graphicTree(Tree tree, Node p) { //fix, missing Grandchildren from a missing son... I really need to fix this?...
        if (tree.getRoot() != null) {            //fix, add spaces for a better view...
            int hght = tree.getHeight();
            int level = p.getLevel() + 1;
            //System.out.println(level + "<" + height);
            System.out.println(p.getString() + "-");
            while (level <= hght) {
                clean();
                levelNodes(tree.getRoot(), level);
                for (int i = 0; i < levels.size(); i++) {
                    String node = levels.get(i);
                    System.out.print(node + "-");
                }
                //for (String node : levels) {
                //    System.out.print(node + "-");
                //}
                System.out.println("");
                level++;
            }
        }
    }

    static public void preOrder(Node p, LinkedList<String> travel) {
        if (p != null) {
            travel.add(p.getString());
            preOrder(p.getLeft(), travel);
            preOrder(p.getRight(), travel);
        }
    }

    static public void inOrder(Node p, LinkedList<String> travel) {
        if (p != null) {
            inOrder(p.getLeft(), travel);
            travel.add(p.getString());
            inOrder(p.getRight(), travel);
        }
    }

    static public void postOrder(Node p, LinkedList<String> travel) {
        if (p != null) {
            postOrder(p.getLeft(), travel);
            postOrder(p.getRight(), travel);
            travel.add(p.getString());
        }
    }

    static public void levelOrder(Node root, LinkedList<String> travel) {
        if (root != null) {
            LinkedList<Node> queue = new LinkedList<>();
            queue.addLast(root);
            while (queue.size() > 0) {
                Node p = queue.getNode(0).getInfo();
                queue.pollFirst();
                travel.add(p.getString());
                if (p.getLeft() != null) {
                    queue.addLast(p.getLeft());
                }
                if (p.getRight() != null) {
                    queue.addLast(p.getRight());
                }
            }
        }
    }

    static public void levelNodes(Node p, int level) {
        if (p != null) {
            String string = p.getString();
            int lvl = p.getLevel();
            if (lvl == level && !levels.contains(string)) {
                levels.add(string);
            }
            if (p.getLeft() == null && lvl + 1 == level) {
                levels.add(-1 + "");
            }
            levelNodes(p.getLeft(), level);
            if (p.getRight() == null && lvl + 1 == level) {
                levels.add(-1 + "");
            }
            levelNodes(p.getRight(), level);
            //levelNodes(p.getLeft(), level);
            //levelNodes(p.getRight(), level);
        }
    }

    static public boolean leaf(Node p) {
        if (p != null) {
            if (p.getLeft() != null || p.getRight() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    static public void offspring(Node p) {
        if (p != null) {
            sons++;
            offspring(p.getLeft());
            offspring(p.getRight());
        }
    }

    static public int nodes(Node p) {
        clean();
        offspring(p);
        return sons + 1;
    }

    static public void height(Node p, int num) {
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

    static public void clean() {
        sons = -1;
        height = 0;
        levels = new LinkedList<>();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String string = "";
        int op;
        do {
            System.out.println(
                    "NOTA: el valor -1 en mi laboratorio es nulo, vacio\n\n"
                    + "Opciones:\n"
                    + " 1. Crear Arbol\n"
                    + " 2. Mostrar Arbol\n"
                    + " 3. Informacion detallada del nodo\n"
                    + " 4. Eliminar nodo\n"
                    + " 5. Agregar nodo\n"
                    + " 6. Recorrer Arbol mediante booleanos\n"
                    + " 7. Rama mas grande\n"
                    + " 0. Salir"
            );
            System.out.println("OPCION: ");
            op = read.nextInt();
            System.out.println("");
            switch (op) {
                case 1:
                    createTree(tree);
                    break;
                case 2:
                    showTree(tree);
                    break;
                case 3:
                    clean();
                    System.out.println("Nodo:");
                    string = read.next();
                    System.out.println("");
                    Node p = tree.findNode(string, tree.getRoot(), null);
                    if (p != null) {
                        graphicTree(tree, tree.getRoot());
                        System.out.println("");
                        height(p, 0);
                        offspring(p);
                        System.out.println(leaf(p) + "- hoja");
                        System.out.println(height + "- altura");
                        System.out.println(sons + "- descendencia");
                        System.out.println(nodes(p) + "- nodos del arbol/sub-arbol");
                        System.out.println(p.getLevel() + "- nivel");
                    }
                    break;
                case 4:
                    System.out.println("Nodo:");
                    string = read.next();
                    tree.deleteNode(string);
                    break;
                case 5:
                    createTree(tree);
                    break;
                case 6:
                    System.out.println("1.False - 2.True");
                    op = read.nextInt();
                    if (op == 1) {
                        string = tree.run(false);
                    } else if (op == 2) {
                        string = tree.run(true);
                    }
                    System.out.println(string);
                    if (string.equals("No se!")) {
                        String a, q;
                        System.out.println("Digite informacion a aprender: (pregunta Enter respuesta)");
                        q = read.next();
                        a = read.next();
                        tree.learn(q, a);
                    }
                    break;
                case 7:
                    System.out.print("La Rama mas larga es: ");
                    LinkedList<Node> list = new LinkedList<>();
                    list = tree.biggerBranch(tree.getRoot(), list);
                    for (int i = 0; i < list.size(); i++) {
                        Node node = list.get(i);
                        System.out.print(node.getString() + "-");
                    }
                    //for (Node node : list) {
                    //    System.out.print(node.getString() + "-");
                    //}
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
