package controller;

import arbres.binaires.ArbreBinaireDeRechercheRecursive;
import model.Livre;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gère l'inventaire des livres et permet différentes recherches et affichages.
 * Toutes les recherches sont maintenant effectuées dans l'arbre binaire.
 */
public class InventaireController {
    private ArbreBinaireDeRechercheRecursive<Livre> arbreLivres;

    /**
     * Initialise l'inventaire avec une liste de livres et les insère dans un arbre binaire.
     */
    public InventaireController(List<Livre> livres) {
        this.arbreLivres = new ArbreBinaireDeRechercheRecursive<>();
        System.out.println("📂 Nombre de livres reçus par InventaireController : " + livres.size());
        for (Livre livre : livres) {
            arbreLivres.insererNode(livre);
        }
    }

    /**
     * Recherche les livres par catégorie en utilisant l'arbre binaire.
     */
    public List<Livre> getLivresParCategorie(String categorie) {
        return arbreLivres.getLivresParCategorie(categorie);
    }

    /**
     * Retourne tous les livres triés par catégorie, auteur et titre.
     */
    public List<Livre> getTousLesLivres() {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream().sorted((l1, l2) -> {
            int catCompare = l1.getCategorie().compareToIgnoreCase(l2.getCategorie());
            if (catCompare != 0) return catCompare;
            int auteurCompare = l1.getAuteur().compareToIgnoreCase(l2.getAuteur());
            if (auteurCompare != 0) return auteurCompare;
            return l1.getTitre().compareToIgnoreCase(l2.getTitre());
        }).collect(Collectors.toList());
    }

    /**
     * Retourne les livres en rupture de stock.
     */
    public List<Livre> getLivresRuptureStock() {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream().filter(l -> l.getQuantite() == 0).collect(Collectors.toList());
    }

    /**
     * Recherche les livres par auteur.
     */
    public List<Livre> getLivresParAuteur(String auteur) {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream()
                .filter(l -> normaliserTexte(l.getAuteur()).contains(normaliserTexte(auteur)))
                .collect(Collectors.toList());
    }


    /**
     * Recherche les livres par titre.
     */
    public List<Livre> getLivresParTitre(String titre) {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream()
                .filter(l -> normaliserTexte(l.getTitre()).contains(normaliserTexte(titre)))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les livres publiés durant une année donnée.
     */
    public List<Livre> getLivresParAnnee(String annee) {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream()
                .filter(l -> l.getDatePublication().startsWith(annee))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les livres dans un intervalle de prix.
     */
    public List<Livre> getLivresParPrixIntervalle(double minPrix, double maxPrix) {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream()
                .filter(l -> l.getPrix() >= minPrix && l.getPrix() <= maxPrix)
                .sorted((l1, l2) -> Double.compare(l1.getPrix(), l2.getPrix()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche un livre par auteur et début de titre (insensible à la casse et aux accents).
     */
    public List<Livre> getLivreParAuteurEtTitre(String auteur, String debutTitre) {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream()
                .filter(l -> normaliserTexte(l.getAuteur()).contains(normaliserTexte(auteur)) &&
                        normaliserTexte(l.getTitre()).startsWith(normaliserTexte(debutTitre)))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les livres d'un auteur spécifique et les trie par date de publication (du plus récent au plus ancien).
     */
    public List<Livre> getLivresParAuteurEtDate(String auteur) {
        List<Livre> resultat = new ArrayList<>();
        arbreLivres.collecterNoeuds(arbreLivres.getRoot(), resultat);
        return resultat.stream()
                .filter(l -> normaliserTexte(l.getAuteur()).contains(normaliserTexte(auteur)))
                .sorted((l1, l2) -> l2.getDatePublication().compareToIgnoreCase(l1.getDatePublication()))
                .collect(Collectors.toList());
    }


    /**
     * Affiche les livres triés par catégorie.
     */
    public void afficherLivresParCategorie() {
        getTousLesLivres().forEach(System.out::println);
    }

    /**
     * Affiche les livres triés par auteur.
     */
    public void afficherLivresParAuteur() {
        getTousLesLivres().stream()
                .sorted((l1, l2) -> l1.getAuteur().compareToIgnoreCase(l2.getAuteur()))
                .forEach(System.out::println);
    }

    /**
     * Affiche les livres triés par titre.
     */
    public void afficherLivresParTitre() {
        getTousLesLivres().stream()
                .sorted((l1, l2) -> l1.getTitre().compareToIgnoreCase(l2.getTitre()))
                .forEach(System.out::println);
    }

    /**
     * Affiche les livres triés par date de publication (du plus récent au plus ancien).
     */
    public void afficherLivresParDate() {
        getTousLesLivres().stream()
                .sorted((l1, l2) -> l2.getDatePublication().compareToIgnoreCase(l1.getDatePublication()))
                .forEach(System.out::println);
    }

    /**
     * Normalise un texte en supprimant les accents et en mettant en minuscules.
     */
    private String normaliserTexte(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }
}
