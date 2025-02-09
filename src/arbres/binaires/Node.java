package arbres.binaires;

/**
 * Représente un nœud dans un arbre binaire.
 */
public class Node<T extends Comparable<T>> {
    T key;
    Node<T> left;
    Node<T> right;
    Integer height;

    /**
     * Crée un nœud avec une clé donnée.
     */
    public Node(T key) {
        this.key = key;
        this.height = 1;
    }

    /**
     * Retourne la clé du nœud.
     */
    public T getKey() {
        return key;
    }

    /**
     * Modifie la clé du nœud.
     */
    public void setKey(T key) {
        this.key = key;
    }

    /**
     * Retourne le fils gauche du nœud.
     */
    public Node<T> getLeft() {
        return left;
    }

    /**
     * Définit le fils gauche du nœud.
     */
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    /**
     * Retourne le fils droit du nœud.
     */
    public Node<T> getRight() {
        return right;
    }

    /**
     * Définit le fils droit du nœud.
     */
    public void setRight(Node<T> right) {
        this.right = right;
    }

    /**
     * Retourne la hauteur du nœud.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Définit la hauteur du nœud.
     */
    public void setHeight(Integer height) {
        this.height = height;
    }
}
