import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        GestionUtilisateurs gestionUtilisateurs = new GestionUtilisateurs();
        Inventaire inventaire = new Inventaire();

        
        // Lancement de l'interface utilisateur
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FenetreConnexion fenetreConnexion = new FenetreConnexion(gestionUtilisateurs, inventaire);
                fenetreConnexion.setVisible(true);
            }
        });
    }
}