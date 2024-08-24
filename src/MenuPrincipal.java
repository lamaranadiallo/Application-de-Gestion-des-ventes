import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private Utilisateur utilisateurConnecte;
    private Inventaire inventaire;
    private GestionUtilisateurs gestionUtilisateurs;

    public MenuPrincipal(Utilisateur utilisateur, Inventaire inventaire, GestionUtilisateurs gestionUtilisateurs) {
        this.utilisateurConnecte = utilisateur;
        this.inventaire = inventaire;
        this.gestionUtilisateurs = gestionUtilisateurs;
        initialiserInterface();
    }

    private void initialiserInterface() {
        setTitle("Menu Principal - " + utilisateurConnecte.getFonction());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));

        add(new JLabel("Bienvenue, " + utilisateurConnecte.getUsername() + " (" + utilisateurConnecte.getFonction() + ")"));

        JButton boutonGestionProduits = new JButton("Gestion des Produits");
        boutonGestionProduits.addActionListener(e -> ouvrirGestionProduits());
        add(boutonGestionProduits);

        JButton boutonVentes = new JButton("Réaliser une Vente");
        boutonVentes.addActionListener(e -> ouvrirInterfaceVentes());
        add(boutonVentes);

        if ("directeur".equals(utilisateurConnecte.getFonction())) {
            JButton boutonGestionUtilisateurs = new JButton("Gestion des Utilisateurs");
            boutonGestionUtilisateurs.addActionListener(e -> ouvrirGestionUtilisateurs());
            add(boutonGestionUtilisateurs);

            JButton boutonTableauBord = new JButton("Tableau de Bord");
            boutonTableauBord.addActionListener(e -> ouvrirTableauBord());
            add(boutonTableauBord);
        }

        JButton boutonDeconnexion = new JButton("Déconnexion");
        boutonDeconnexion.addActionListener(e -> deconnexion());
        add(boutonDeconnexion);

        setLocationRelativeTo(null);
    }

    private void ouvrirGestionProduits() {
        InterfaceUtilisateur gestionProduits = new InterfaceUtilisateur(inventaire);
        gestionProduits.setVisible(true);
    }

    private void ouvrirInterfaceVentes() {
        InterfaceUtilisateur interfaceVentes = new InterfaceUtilisateur(inventaire);
        interfaceVentes.setVisible(true);
    }

    private void ouvrirGestionUtilisateurs() {
        // À implémenter : ouvrir l'interface de gestion des utilisateurs
        JOptionPane.showMessageDialog(this, "Fonctionnalité de gestion des utilisateurs à implémenter");
    }

    private void ouvrirTableauBord() {
        TableauDeBord tableauBord = new TableauDeBord(inventaire);
        tableauBord.setVisible(true);
    }

    private void deconnexion() {
        int choix = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir vous déconnecter ?", 
            "Confirmation de déconnexion", 
            JOptionPane.YES_NO_OPTION);
        
        if (choix == JOptionPane.YES_OPTION) {
            this.dispose();
            new FenetreConnexion(gestionUtilisateurs).setVisible(true);
        }
    }
}