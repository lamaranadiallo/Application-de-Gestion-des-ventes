import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.IOException;

public class InterfaceUtilisateur extends JFrame {
    private Inventaire inventaire;
    private JTable tableProduits;
    private DefaultTableModel modeleTable;
    private JTextField champNom, champPrix, champQuantite, champRecherche, champQuantiteVente;
    private JTextArea zoneVente;
    private Vente venteEnCours;

    public InterfaceUtilisateur(Inventaire inventaire) {
        this.inventaire = inventaire;
        this.venteEnCours = new Vente();
        initialiserInterface();
    }

    private void initialiserInterface() {
        setTitle("Gestion des Ventes");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal divisé en deux
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        // Panel gauche : gestion des produits
        JPanel panelGauche = new JPanel(new BorderLayout());
        splitPane.setLeftComponent(panelGauche);

        // Table des produits
        String[] colonnes = {"Nom", "Prix", "Quantité"};
        modeleTable = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rend la table non éditable
            }
        };
        tableProduits = new JTable(modeleTable);
        JScrollPane scrollPane = new JScrollPane(tableProduits);
        panelGauche.add(scrollPane, BorderLayout.CENTER);

        // Panel pour les actions sur les produits
        JPanel panelActions = new JPanel(new GridLayout(7, 2));
        champNom = new JTextField();
        champPrix = new JTextField();
        champQuantite = new JTextField();
        champRecherche = new JTextField();
        JButton boutonAjouter = new JButton("Ajouter Produit");
        JButton boutonModifier = new JButton("Modifier Produit");
        JButton boutonSupprimer = new JButton("Supprimer Produit");
        JButton boutonRechercher = new JButton("Rechercher");

        // Ajout des boutons de sauvegarde et de chargement
        JButton boutonSauvegarder = new JButton("Sauvegarder Inventaire");
        JButton boutonCharger = new JButton("Charger Inventaire");

        JPanel panelFichier = new JPanel(new FlowLayout());
        panelFichier.add(boutonSauvegarder);
        panelFichier.add(boutonCharger);

        panelGauche.add(panelFichier, BorderLayout.NORTH);

        panelActions.add(new JLabel("Nom:"));
        panelActions.add(champNom);
        panelActions.add(new JLabel("Prix:"));
        panelActions.add(champPrix);
        panelActions.add(new JLabel("Quantité:"));
        panelActions.add(champQuantite);
        panelActions.add(boutonAjouter);
        panelActions.add(boutonModifier);
        panelActions.add(boutonSupprimer);
        panelActions.add(new JLabel("Recherche:"));
        panelActions.add(champRecherche);
        panelActions.add(boutonRechercher);

        panelGauche.add(panelActions, BorderLayout.SOUTH);

        // Panel droit : réalisation des ventes
        JPanel panelDroit = new JPanel(new BorderLayout());
        splitPane.setRightComponent(panelDroit);

        zoneVente = new JTextArea();
        zoneVente.setEditable(false);
        JScrollPane scrollVente = new JScrollPane(zoneVente);
        panelDroit.add(scrollVente, BorderLayout.CENTER);

        JPanel panelVente = new JPanel(new FlowLayout());
        champQuantiteVente = new JTextField(5);
        JButton boutonAjouterVente = new JButton("Ajouter à la vente");
        JButton boutonTerminerVente = new JButton("Terminer la vente");
        JButton boutonHistorique = new JButton("Historique des Ventes");
        panelVente.add(boutonHistorique);

        JButton boutonTableauDeBord = new JButton("Tableau de Bord");
        panelVente.add(boutonTableauDeBord);

        panelVente.add(new JLabel("Quantité:"));
        panelVente.add(champQuantiteVente);
        panelVente.add(boutonAjouterVente);
        panelVente.add(boutonTerminerVente);

        panelDroit.add(panelVente, BorderLayout.SOUTH);

        // Ajout des listeners pour les boutons
        boutonAjouter.addActionListener(e -> ajouterProduit());
        boutonModifier.addActionListener(e -> modifierProduit());
        boutonSupprimer.addActionListener(e -> supprimerProduit());
        boutonRechercher.addActionListener(e -> rechercherProduit());
        boutonAjouterVente.addActionListener(e -> ajouterALaVente());
        boutonTerminerVente.addActionListener(e -> terminerVente());
        boutonHistorique.addActionListener(e -> ouvrirHistoriqueVentes());

        // Ajout des listeners pour les nouveaux boutons
        boutonSauvegarder.addActionListener(e -> sauvegarderInventaire());
        boutonCharger.addActionListener(e -> chargerInventaire());

        boutonTableauDeBord.addActionListener(e -> ouvrirTableauDeBord());

        // Sélection d'un produit dans la table
        tableProduits.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableProduits.getSelectedRow();
                if (selectedRow != -1) {
                    champNom.setText((String) modeleTable.getValueAt(selectedRow, 0));
                    champPrix.setText(modeleTable.getValueAt(selectedRow, 1).toString());
                    champQuantite.setText(modeleTable.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        afficherInventaire(); // Afficher l'inventaire initial
    }

    private void ajouterProduit() {
        try {
            String nom = champNom.getText();
            double prix = Double.parseDouble(champPrix.getText());
            int quantite = Integer.parseInt(champQuantite.getText());

            Produit nouveauProduit = new Produit(nom, prix, quantite);
            inventaire.ajouterProduit(nouveauProduit);

            // Réinitialiser les champs
            champNom.setText("");
            champPrix.setText("");
            champQuantite.setText("");

            afficherInventaire(); // Rafraîchir l'affichage
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierProduit() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String nom = champNom.getText();
                double prix = Double.parseDouble(champPrix.getText());
                int quantite = Integer.parseInt(champQuantite.getText());

                Produit produit = inventaire.getProduits().get(selectedRow);
                produit.setNom(nom);
                produit.setPrix(prix);
                produit.setQuantite(quantite);

                afficherInventaire(); // Rafraîchir l'affichage
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerProduit() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow != -1) {
            Produit produit = inventaire.getProduits().get(selectedRow);
            inventaire.retirerProduit(produit);
            afficherInventaire(); // Rafraîchir l'affichage
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherProduit() {
        String recherche = champRecherche.getText().toLowerCase();
        List<Produit> produitsFiltres = inventaire.rechercherProduits(recherche);
        afficherProduits(produitsFiltres);
    }

    private void ajouterALaVente() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Produit produitSelectionne = inventaire.getProduits().get(selectedRow);
                int quantite = Integer.parseInt(champQuantiteVente.getText());
                if (quantite > 0 && quantite <= produitSelectionne.getQuantite()) {
                    venteEnCours.ajouterLigne(produitSelectionne, quantite);
                    mettreAJourAffichageVente();
                } else {
                    JOptionPane.showMessageDialog(this, "Quantité invalide ou insuffisante en stock.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void terminerVente() {
        if (!venteEnCours.getLignesVente().isEmpty()) {
            try {
                inventaire.effectuerVente(venteEnCours);
                JOptionPane.showMessageDialog(this, "Vente terminée avec succès. Total: " + String.format("%.2f", venteEnCours.getTotal()) + " €", "Vente terminée", JOptionPane.INFORMATION_MESSAGE);
                venteEnCours = new Vente();
                mettreAJourAffichageVente();
                afficherInventaire(); // Mettre à jour l'affichage de l'inventaire
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun produit dans la vente actuelle.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherInventaire() {
        afficherProduits(inventaire.getProduits());
    }

    private void afficherProduits(List<Produit> produits) {
        modeleTable.setRowCount(0); // Efface toutes les lignes existantes
        for (Produit produit : produits) {
            Object[] row = {produit.getNom(), produit.getPrix(), produit.getQuantite()};
            modeleTable.addRow(row);
        }
    }

    private void ouvrirHistoriqueVentes() {
        FenetreHistoriqueVentes fenetreHistorique = new FenetreHistoriqueVentes(inventaire);
        fenetreHistorique.setVisible(true);
    }

    private void mettreAJourAffichageVente() {
        zoneVente.setText(venteEnCours.toString());
    }


    private void sauvegarderInventaire() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder l'inventaire");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String nomFichier = fileChooser.getSelectedFile().getAbsolutePath();
                if (!nomFichier.toLowerCase().endsWith(".csv")) {
                    nomFichier += ".csv";
                }
                inventaire.sauvegarderInventaire(nomFichier);
                JOptionPane.showMessageDialog(this, "Inventaire sauvegardé avec succès.", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void chargerInventaire() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Charger l'inventaire");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String nomFichier = fileChooser.getSelectedFile().getAbsolutePath();
                inventaire.chargerInventaire(nomFichier);
                afficherInventaire();
                JOptionPane.showMessageDialog(this, "Inventaire chargé avec succès.", "Chargement", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ouvrirTableauDeBord() {
        TableauDeBord tableauDeBord = new TableauDeBord(inventaire);
        tableauDeBord.setVisible(true);
    }


}