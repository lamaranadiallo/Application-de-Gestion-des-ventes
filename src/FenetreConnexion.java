import javax.swing.*;
import java.awt.*;

public class FenetreConnexion extends JFrame {
    private JTextField champUsername;
    private JPasswordField champPassword;
    private GestionUtilisateurs gestionUtilisateurs;
    private Inventaire inventaire;

    public FenetreConnexion(GestionUtilisateurs gestionUtilisateurs, Inventaire inventaire) {
        this.gestionUtilisateurs = gestionUtilisateurs;
        this.inventaire = inventaire;
        initialiserInterface();
    }

    private void initialiserInterface() {
        setTitle("Connexion");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Nom d'utilisateur:"));
        champUsername = new JTextField();
        add(champUsername);

        add(new JLabel("Mot de passe:"));
        champPassword = new JPasswordField();
        add(champPassword);

        JButton boutonConnexion = new JButton("Se connecter");
        boutonConnexion.addActionListener(e -> seConnecter());
        add(boutonConnexion);

        setLocationRelativeTo(null);
    }

    private void seConnecter() {
        String username = champUsername.getText();
        String password = new String(champPassword.getPassword());

        Utilisateur utilisateur = gestionUtilisateurs.authentifier(username, password);
        if (utilisateur != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie en tant que " + utilisateur.getFonction());
            ouvrirMenuPrincipal(utilisateur);
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ouvrirMenuPrincipal(Utilisateur utilisateur) {
        MenuPrincipal menuPrincipal = new MenuPrincipal(utilisateur, inventaire, gestionUtilisateurs);
        menuPrincipal.setVisible(true);
        this.dispose(); // Ferme la fenêtre de connexion
    }
}