package main;

import controller.InventaireController;
import model.Livre;
import utils.JSONHandler;
import view.LibrairieGUI;
import java.util.List;

/**
 * Point d'entrée principal du programme.
 * Charge les livres depuis un fichier JSON et lance l'interface utilisateur.
 */
public class Main {
    public static void main(String[] args) {
        // Charger les livres depuis le fichier JSON
        List<Livre> livres = JSONHandler.chargerLivres();

        // Vérification : afficher les livres chargés
        System.out.println("📚 Livres chargés : " + livres.size());
        for (Livre livre : livres) {
            System.out.println(livre);
        }

        // Initialiser le contrôleur avec les livres chargés
        InventaireController controller = new InventaireController(livres);

        // Lancer l'interface graphique
        new LibrairieGUI(controller);
    }
}
