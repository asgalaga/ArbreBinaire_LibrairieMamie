package arbres.binaires;

/**
 * Interface définissant les méthodes de recherche en profondeur dans un arbre binaire.
 */
public interface RechercheParPronfondeur {
    /**
     * Parcours pré-ordre : Racine -> Gauche -> Droite.
     */
    void preOrderRecherche(Node node);

    /**
     * Parcours post-ordre : Gauche -> Droite -> Racine.
     */
    void postOrderRecherche(Node node);

    /**
     * Parcours en ordre : Gauche -> Racine -> Droite.
     */
    void byOrderRecherche(Node node);
}
