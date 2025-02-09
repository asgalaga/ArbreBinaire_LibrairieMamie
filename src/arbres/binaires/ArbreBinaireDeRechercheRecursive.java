package arbres.binaires;

import model.Livre;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation récursive d'un arbre binaire de recherche (ABR) avec support pour la recherche avancée.
 */
public class ArbreBinaireDeRechercheRecursive<T extends Comparable<T>> extends ArbreBinaireSimple<T> implements ArbreBinaireDeRecherche<T> {

    /**
     * Recherche un nœud contenant la clé spécifiée.
     */
    @Override
    public Node<T> chercherNode(T key) {
        return chercherRecursive(root, key);
    }

    private Node<T> chercherRecursive(Node<T> current, T key) {
        if (current == null || current.getKey().equals(key)) {
            return current;
        }
        return key.compareTo(current.getKey()) < 0 ?
                chercherRecursive(current.getLeft(), key) :
                chercherRecursive(current.getRight(), key);
    }

    /**
     * Insère une clé dans l'arbre.
     */
    @Override
    public void insererNode(T key) {
        root = insererRecursive(root, key);
    }

    private Node<T> insererRecursive(Node<T> current, T key) {
        if (current == null) {
            return new Node<>(key);
        }
        if (key.compareTo(current.getKey()) < 0) {
            current.setLeft(insererRecursive(current.getLeft(), key));
        } else if (key.compareTo(current.getKey()) > 0) {
            current.setRight(insererRecursive(current.getRight(), key));
        }
        return current;
    }

    /**
     * Supprime une clé de l'arbre.
     */
    @Override
    public void supprimerNode(T key) {
        root = supprimerRecursive(root, key);
    }

    private Node<T> supprimerRecursive(Node<T> current, T key) {
        if (current == null) {
            return null;
        }
        if (key.compareTo(current.getKey()) < 0) {
            current.setLeft(supprimerRecursive(current.getLeft(), key));
        } else if (key.compareTo(current.getKey()) > 0) {
            current.setRight(supprimerRecursive(current.getRight(), key));
        } else {
            if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            }
            Node<T> smallestValue = trouverMin(current.getRight());
            current.setKey(smallestValue.getKey());
            current.setRight(supprimerRecursive(current.getRight(), smallestValue.getKey()));
        }
        return current;
    }

    /**
     * Trouve le nœud avec la plus petite clé dans un sous-arbre.
     */
    private Node<T> trouverMin(Node<T> root) {
        return root.getLeft() == null ? root : trouverMin(root.getLeft());
    }

    /**
     * Collecte tous les éléments de l'arbre en ordre croissant.
     */
    public void collecterNoeuds(Node<T> node, List<T> liste) {
        if (node != null) {
            collecterNoeuds(node.getLeft(), liste);
            liste.add(node.getKey());
            collecterNoeuds(node.getRight(), liste);
        }
    }

    /**
     * Recherche les livres par catégorie.
     */
    public List<T> getLivresParCategorie(String categorie) {
        List<T> resultat = new ArrayList<>();
        collecterLivresParCategorie(root, categorie, resultat);
        return resultat;
    }

    private void collecterLivresParCategorie(Node<T> node, String categorie, List<T> liste) {
        if (node != null) {
            collecterLivresParCategorie(node.getLeft(), categorie, liste);
            if (((Livre) node.getKey()).getCategorie().equalsIgnoreCase(categorie)) {
                liste.add(node.getKey());
            }
            collecterLivresParCategorie(node.getRight(), categorie, liste);
        }
    }
}
