package arbres.binaires;

/**
 * Classe représentant un arbre AVL (un type d'arbre binaire de recherche équilibré).
 * Il s'agit d'une extension d'un arbre binaire de recherche classique,
 * avec des rotations pour maintenir l'équilibre après chaque insertion.
 */
public class ArbreAVL<T extends Comparable<T>> extends ArbreBinaireDeRechercheRecursive<T> {

    /**
     * Retourne la hauteur d'un nœud dans l'arbre.
     * Si le nœud est null, la hauteur est 0.
     */
    private int getHauteur(Node<T> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    /**
     * Calcule le facteur d'équilibre d'un nœud.
     * Il s'agit de la différence entre la hauteur du sous-arbre gauche et celle du sous-arbre droit.
     */
    private int getFacteurEquilibre(Node<T> node) {
        return (node == null) ? 0 : getHauteur(node.getLeft()) - getHauteur(node.getRight());
    }

    /**
     * Effectue une rotation à droite pour rééquilibrer l'arbre.
     * Utilisé lorsque le sous-arbre gauche est trop grand.
     */
    private Node<T> rotationDroite(Node<T> y) {
        Node<T> x = y.getLeft();
        Node<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(Math.max(getHauteur(y.getLeft()), getHauteur(y.getRight())) + 1);
        x.setHeight(Math.max(getHauteur(x.getLeft()), getHauteur(x.getRight())) + 1);

        return x;
    }

    /**
     * Effectue une rotation à gauche pour rééquilibrer l'arbre.
     * Utilisé lorsque le sous-arbre droit est trop grand.
     */
    private Node<T> rotationGauche(Node<T> x) {
        Node<T> y = x.getRight();
        Node<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(Math.max(getHauteur(x.getLeft()), getHauteur(x.getRight())) + 1);
        y.setHeight(Math.max(getHauteur(y.getLeft()), getHauteur(y.getRight())) + 1);

        return y;
    }

    /**
     * Insère un élément dans l'arbre AVL tout en maintenant son équilibre.
     */
    @Override
    public void insererNode(T key) {
        root = insererEquilibre(root, key);
    }

    /**
     * Insère un élément de manière récursive en s'assurant que l'arbre reste équilibré.
     */
    private Node<T> insererEquilibre(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key);
        }

        if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(insererEquilibre(node.getLeft(), key));
        } else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(insererEquilibre(node.getRight(), key));
        } else {
            return node; // L'élément est déjà dans l'arbre, pas d'insertion
        }

        // Mise à jour de la hauteur du nœud actuel
        node.setHeight(Math.max(getHauteur(node.getLeft()), getHauteur(node.getRight())) + 1);
        int balance = getFacteurEquilibre(node);

        // Vérifications et rotations pour maintenir l'équilibre
        if (balance > 1 && key.compareTo(node.getLeft().getKey()) < 0) {
            return rotationDroite(node);
        }
        if (balance < -1 && key.compareTo(node.getRight().getKey()) > 0) {
            return rotationGauche(node);
        }
        if (balance > 1 && key.compareTo(node.getLeft().getKey()) > 0) {
            node.setLeft(rotationGauche(node.getLeft()));
            return rotationDroite(node);
        }
        if (balance < -1 && key.compareTo(node.getRight().getKey()) < 0) {
            node.setRight(rotationDroite(node.getRight()));
            return rotationGauche(node);
        }

        return node;
    }
}
