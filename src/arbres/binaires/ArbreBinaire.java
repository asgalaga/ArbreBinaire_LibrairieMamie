package arbres.binaires;

/**
 * Interface de base pour un arbre binaire avec une racine.
 */
public interface ArbreBinaire<T extends Comparable<T>> {
    /**
     * Retourne la racine de l'arbre.
     */
    Node<T> getRoot();
}