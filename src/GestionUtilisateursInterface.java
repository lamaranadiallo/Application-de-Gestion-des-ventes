import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionUtilisateursInterface extends JFrame {
    private GestionUtilisateurs gestionUtilisateurs;
    private JTable tableUtilisateurs;
    private DefaultTableModel modeleTable;
    private JTextField champId, champUsername, champPassword, champFonction, champRecherche;

    public GestionUtilisateursInterface(GestionUtilisateurs gestionUtilisateurs) {
        this.gestionUtilisateurs = gestionUtilisateurs;
        initialiserInterface();
    }

    private void initialiserInterface() {
        setTitle("Gestion des Utilisateurs");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Table des utilisateurs
        String[] colonnes = {"ID", "Nom d'utilisateur", "Fonction"};
        modeleTable = new DefaultTableModel(colonnes, 0);
        tableUtilisateurs = new JTable(modeleTable);
        JScrollPane scrollPane = new JScrollPane(tableUtilisateurs);
        add(scrollPane, BorderLayout.CENTER);

        // Panneau de formulaire
        JPanel panelFormulaire = new JPanel(new GridLayout(5, 2));
        champId = new JTextField();
        champUsername = new JTextField();
        champPassword = new JPasswordField();
        champFonction = new JTextField();
        champRecherche = new JTextField();

        panelFormulaire.add(new JLabel("ID:"));
        panelFormulaire.add(champId);
        panelFormulaire.add(new JLabel("Nom d'utilisateur:"));
        panelFormulaire.add(champUsername);
        panelFormulaire.add(new JLabel("Mot de passe:"));
        panelFormulaire.add(champPassword);
        panelFormulaire.add(new JLabel("Fonction:"));
        panelFormulaire.add(champFonction);
        panelFormulaire.add(new JLabel("Recherche:"));
        panelFormulaire.add(champRecherche);

        // Panneau de boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton boutonAjouter = new JButton("Ajouter");
        JButton boutonModifier = new JButton("Modifier");
        JButton boutonSupprimer = new JButton("Supprimer");
        JButton boutonRechercher = new JButton("Rechercher");
        JButton boutonActualiser = new JButton("Actualiser");

        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonModifier);
        panelBoutons.add(boutonSupprimer);
        panelBoutons.add(boutonRechercher);
        panelBoutons.add(boutonActualiser);

        JPanel panelSud = new JPanel(new BorderLayout());
        panelSud.add(panelFormulaire, BorderLayout.CENTER);
        panelSud.add(panelBoutons, BorderLayout.SOUTH);

        add(panelSud, BorderLayout.SOUTH);

        // Ajout des listeners
        boutonAjouter.addActionListener(e -> ajouterUtilisateur());
        boutonModifier.addActionListener(e -> modifierUtilisateur());
        boutonSupprimer.addActionListener(e -> supprimerUtilisateur());
        boutonRechercher.addActionListener(e -> rechercherUtilisateur());
        boutonActualiser.addActionListener(e -> actualiserTable());

        // Sélection dans la table
        tableUtilisateurs.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableUtilisateurs.getSelectedRow();
                if (selectedRow != -1) {
                    champId.setText(tableUtilisateurs.getValueAt(selectedRow, 0).toString());
                    champUsername.setText(tableUtilisateurs.getValueAt(selectedRow, 1).toString());
                    champFonction.setText(tableUtilisateurs.getValueAt(selectedRow, 2).toString());
                    champPassword.setText(""); // Pour des raisons de sécurité, ne pas afficher le mot de passe
                }
            }
        });

        actualiserTable();
    }

    private void ajouterUtilisateur() {
        try {
            int id = Integer.parseInt(champId.getText());
            String username = champUsername.getText();
            String password = new String(((JPasswordField) champPassword).getPassword());
            String fonction = champFonction.getText();

            if (username.isEmpty() || password.isEmpty() || fonction.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Utilisateur nouvelUtilisateur = new Utilisateur(id, username, password, fonction);
            gestionUtilisateurs.ajouterUtilisateur(nouvelUtilisateur);
            actualiserTable();
            viderChamps();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierUtilisateur() {
        try {
            int id = Integer.parseInt(champId.getText());
            String username = champUsername.getText();
            String password = new String(((JPasswordField) champPassword).getPassword());
            String fonction = champFonction.getText();

            if (username.isEmpty() || fonction.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom d'utilisateur et la fonction doivent être remplis", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Utilisateur utilisateurModifie = new Utilisateur(id, username, password, fonction);
            gestionUtilisateurs.modifierUtilisateur(utilisateurModifie);
            actualiserTable();
            viderChamps();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerUtilisateur() {
        try {
            int id = Integer.parseInt(champId.getText());
            gestionUtilisateurs.supprimerUtilisateur(id);
            actualiserTable();
            viderChamps();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherUtilisateur() {
        String terme = champRecherche.getText();
        Utilisateur utilisateur = gestionUtilisateurs.rechercherUtilisateur(terme);
        if (utilisateur != null) {
            modeleTable.setRowCount(0);
            Object[] row = {utilisateur.getIdUser(), utilisateur.getUsername(), utilisateur.getFonction()};
            modeleTable.addRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Aucun utilisateur trouvé", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualiserTable() {
        modeleTable.setRowCount(0);
        for (Utilisateur utilisateur : gestionUtilisateurs.getUtilisateurs()) {
            Object[] row = {utilisateur.getIdUser(), utilisateur.getUsername(), utilisateur.getFonction()};
            modeleTable.addRow(row);
        }
    }

    private void viderChamps() {
        champId.setText("");
        champUsername.setText("");
        champPassword.setText("");
        champFonction.setText("");
        champRecherche.setText("");
    }
}