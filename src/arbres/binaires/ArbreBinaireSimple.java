package arbres.binaires;

/**
 * Arbre binaire simple contenant uniquement la gestion de la racine.
 */
public class ArbreBinaireSimple<T extends Comparable<T>> implements ArbreBinaire<T> {
    protected Node<T> root;

    /**
     * Retourne la racine de l'arbre.
     */
    @Override
    public Node<T> getRoot() {
        return root;
    }
}
