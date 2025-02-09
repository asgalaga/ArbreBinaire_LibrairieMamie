package view;

import controller.InventaireController;
import model.Livre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibrairieGUI extends JFrame {
    private InventaireController controller;
    private JComboBox<String> searchType;
    private JTextField searchField, authorField;
    private JButton searchButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JLabel priceSeparator;

    public LibrairieGUI(InventaireController controller) {
        this.controller = controller;

        setTitle("Librairie Mamie");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setVisible(true);

        // Barre du haut : choix de recherche
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        searchType = new JComboBox<>(new String[]{
                "Tous les livres", "Par Catégorie", "Par Auteur", "Par Titre", "Par Auteur et début de Titre", "Par Auteur et Date de publication",
                "Par Année de publication", "Par Prix", "Livres en rupture de stock"
        });

        searchField = new JTextField(15);
        authorField = new JTextField(15); // Champ pour l'auteur
        searchButton = new JButton("Rechercher");

        // Ajout des champs pour la recherche par intervalle de prix
        minPriceField = new JTextField(7);
        maxPriceField = new JTextField(7);
        priceSeparator = new JLabel("à");

        // Déclenche la recherche en appuyant sur ENTER dans les champs de recherche
        searchField.addActionListener(e -> performSearch());
        authorField.addActionListener(e -> performSearch());
        minPriceField.addActionListener(e -> performSearch());
        maxPriceField.addActionListener(e -> performSearch());

        // Ajouter tous les éléments au panneau supérieur
        topPanel.add(new JLabel("Rechercher :"));
        topPanel.add(searchType);
        topPanel.add(authorField); // Champ pour l'auteur
        topPanel.add(searchField);
        topPanel.add(minPriceField);
        topPanel.add(priceSeparator);
        topPanel.add(maxPriceField);
        topPanel.add(searchButton);

        // Rendre les champs spécifiques invisibles au départ
        minPriceField.setVisible(false);
        maxPriceField.setVisible(false);
        priceSeparator.setVisible(false);
        authorField.setVisible(false);

        add(topPanel, BorderLayout.NORTH);

        // Tableau pour afficher les résultats
        String[] columnNames = {"ISBN", "Titre", "Auteur", "Catégorie", "Prix", "Date de publication", "Quantité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);

        // Ajout du tri automatique sur les colonnes
        resultTable.setAutoCreateRowSorter(true);

        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        // Gestion du changement de type de recherche
        searchType.addActionListener(e -> {
            boolean isPriceSearch = searchType.getSelectedItem().equals("Par Prix");
            boolean isAuthorTitleSearch = searchType.getSelectedItem().equals("Par Auteur et début de Titre");

            minPriceField.setVisible(isPriceSearch);
            maxPriceField.setVisible(isPriceSearch);
            priceSeparator.setVisible(isPriceSearch);

            authorField.setVisible(isAuthorTitleSearch);
            searchField.setVisible(!isPriceSearch);

            revalidate();
            repaint();
        });

        // Action du bouton
        searchButton.addActionListener(e -> performSearch());

        setVisible(true);
    }

    private void performSearch() {
        String selectedOption = (String) searchType.getSelectedItem();
        String query = searchField.getText().trim();
        String authorQuery = authorField.getText().trim();
        tableModel.setRowCount(0); // Efface les résultats précédents

        List<Livre> resultats = null;

        try {
            if (selectedOption.equals("Par Catégorie")) {
                resultats = controller.getLivresParCategorie(query);
            } else if (selectedOption.equals("Par Auteur")) {
                resultats = controller.getLivresParAuteur(query);
            } else if (selectedOption.equals("Par Titre")) {
                resultats = controller.getLivresParTitre(query);
            } else if (selectedOption.equals("Par Année de publication")) {
                resultats = controller.getLivresParAnnee(query);
            } else if (selectedOption.equals("Tous les livres")) {
                resultats = controller.getTousLesLivres(); // Affiche tout
            } else if (selectedOption.equals("Livres en rupture de stock")) {
                resultats = controller.getLivresRuptureStock();
            }else if (selectedOption.equals("Par Auteur et Date de publication")) {
                resultats = controller.getLivresParAuteurEtDate(query);
            }else if (selectedOption.equals("Par Prix")) {
                try {
                    String minText = minPriceField.getText().trim();
                    String maxText = maxPriceField.getText().trim();

                    if (minText.isEmpty() || maxText.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer un prix minimum et maximum.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double minPrix = Double.parseDouble(minText);
                    double maxPrix = Double.parseDouble(maxText);

                    if (minPrix > maxPrix) {
                        JOptionPane.showMessageDialog(this, "Le prix minimum doit être inférieur au prix maximum.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    resultats = controller.getLivresParPrixIntervalle(minPrix, maxPrix);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else if (selectedOption.equals("Par Auteur et début de Titre")) {
                resultats = controller.getLivreParAuteurEtTitre(authorQuery, query);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (resultats != null && !resultats.isEmpty()) {
            for (Livre livre : resultats) {
                tableModel.addRow(new Object[]{
                        livre.getIsbn(), livre.getTitre(), livre.getAuteur(),
                        livre.getCategorie(), livre.getPrix(), livre.getDatePublication(), livre.getQuantite()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun résultat trouvé.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
