package model;

/**
 * Représente un livre avec ses informations essentielles.
 * Implémente Comparable pour permettre le tri par titre.
 */
public class Livre implements Comparable<Livre> {
    private String isbn;
    private String titre;
    private String auteur;
    private String categorie;
    private double prix;
    private String datePublication;
    private int quantite;

    /**
     * Constructeur par défaut (nécessaire pour la sérialisation JSON avec Gson).
     */
    public Livre() {}

    /**
     * Constructeur principal pour initialiser un livre.
     */
    public Livre(String isbn, String titre, String auteur, String categorie, double prix, String datePublication, int quantite) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.prix = prix;
        this.datePublication = datePublication;
        this.quantite = quantite;
    }

    // Getters
    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getCategorie() { return categorie; }
    public double getPrix() { return prix; }
    public String getDatePublication() { return datePublication; }
    public int getQuantite() { return quantite; }

    // Setters (nécessaires pour la désérialisation JSON)
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setDatePublication(String datePublication) { this.datePublication = datePublication; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    /**
     * Compare les livres par titre (ordre alphabétique, insensible à la casse).
     */
    @Override
    public int compareTo(Livre o) {
        return this.titre.compareToIgnoreCase(o.titre);
    }

    /**
     * Retourne une représentation sous forme de chaîne du livre.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s) - %.2f$ - Publié le %s - Quantité : %d",
                isbn, titre, auteur, categorie, prix, datePublication, quantite);
    }
}
