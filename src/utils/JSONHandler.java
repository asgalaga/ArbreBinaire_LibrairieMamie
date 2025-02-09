package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Livre;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère la lecture et l'écriture des livres dans un fichier JSON.
 */
public class JSONHandler {
    private static final String FICHIER_JSON = "./src/ressource/livres.json"; // Chemin du fichier JSON
    private static final Gson gson = new Gson(); // Instance de Gson pour la sérialisation/désérialisation

    /**
     * Charge la liste des livres à partir du fichier JSON.
     * @return Liste des livres chargés.
     */
    public static List<Livre> chargerLivres() {
        List<Livre> livres = new ArrayList<>();

        // Vérifie si le fichier existe avant de le lire
        File fichier = new File(FICHIER_JSON);
        if (!fichier.exists()) {
            System.err.println("⚠ Fichier JSON introuvable : " + FICHIER_JSON);
            return livres;
        }

        try (Reader reader = new FileReader(fichier)) {
            Type listType = new TypeToken<List<Livre>>() {}.getType();
            livres = gson.fromJson(reader, listType);
            if (livres == null) {
                livres = new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement du fichier JSON : " + e.getMessage());
        }

        return livres;
    }

    /**
     * Sauvegarde la liste des livres dans le fichier JSON.
     * @param livres Liste des livres à sauvegarder.
     */
    public static void sauvegarderLivres(List<Livre> livres) {
        try (Writer writer = new FileWriter(FICHIER_JSON)) {
            gson.toJson(livres, writer);
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la sauvegarde du fichier JSON : " + e.getMessage());
        }
    }
}
