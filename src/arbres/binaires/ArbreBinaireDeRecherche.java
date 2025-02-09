package arbres.binaires;


/**
 * Interface pour un arbre binaire de recherche (ABR).
 * Ajoute les opérations de recherche, insertion et suppression.
 */
public interface ArbreBinaireDeRecherche<T extends Comparable<T>> extends ArbreBinaire<T> {
    /**
     * Recherche un nœud par clé.
     */
    Node<T> chercherNode(T key);

    /**
     * Insère une clé dans l'arbre.
     */
    void insererNode(T key);

    /**
     * Supprime une clé de l'arbre.
     */
    void supprimerNode(T key);
}
