/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.LinkedList;
import java.util.Scanner;
import tree.Tree;
/**
 *
 * @author John
 */
public class TreeManager {

    Scanner read = new Scanner(System.in);
    int sons = -1, height = 0;
    LinkedList<String> levels = new LinkedList<>();
    
    public void createTree(Tree tree) {
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
        height(tree.getRoot(), 0);
        tree.setHeight(height);
    }

    public void showTree(Tree tree, Node root) {
        LinkedList<Integer> travel = new LinkedList<>();
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
        for (Integer num : travel) {
            System.out.print(num + "-");
        }
    }

    public void graphicTree(Tree tree, Node p) { //fix, missing Grandchildren from a missing son... I really need to fix this?...
        if (tree.getRoot() != null) {            //fix, add spaces for a better view...
            int hght = tree.getHeight();
            int level = p.getLevel() + 1;
            //System.out.println(level + "<" + height);
            System.out.println(p.getString() + "-");
            while (level <= hght) {
                clean();
                levelNodes(tree.getRoot(), level);
                for (String node : levels) {
                    System.out.print(node + "-");
                }
                System.out.println("");
                level++;
            }
        }
    }

    public void preOrder(Node p, LinkedList travel) {
        if (p != null) {
            travel.add(p.getString());
            preOrder(p.getLeft(), travel);
            preOrder(p.getRight(), travel);
        }
    }

    public void inOrder(Node p, LinkedList travel) {
        if (p != null) {
            inOrder(p.getLeft(), travel);
            travel.add(p.getRight());
            inOrder(p.getRight(), travel);
        }
    }

    public void postOrder(Node p, LinkedList travel) {
        if (p != null) {
            postOrder(p.getLeft(), travel);
            postOrder(p.getRight(), travel);
            travel.add(p.getString());
        }
    }

    public void levelOrder(Node root, LinkedList travel) {
        if (root != null) {
            LinkedList<Node> queue = new LinkedList<>();
            queue.addLast(root);
            while (queue.size() > 0) {
                Node p = queue.pollFirst();
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

    public void levelNodes(Node p, int level) {
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

    public boolean leaf(Node p) {
        if (p != null) {
            if (p.getLeft() != null || p.getRight() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public void offspring(Node p) {
        if (p != null) {
            sons++;
            offspring(p.getLeft());
            offspring(p.getRight());
        }
    }

    public int nodes(Node p) {
        clean();
        offspring(p);
        return sons + 1;
    }

    public void height(Node p, int num) {
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

    public void clean() {
        sons = -1;
        height = 0;
        levels = new LinkedList<>();
    }
    
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
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
    }*/
}

