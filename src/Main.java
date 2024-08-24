import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Inventaire inventaire = new Inventaire();

        // Ajout de quelques produits initiaux
        inventaire.ajouterProduit(new Produit("Ordinateur portable", 999.99, 10));
        inventaire.ajouterProduit(new Produit("Souris sans fil", 29.99, 50));
        inventaire.ajouterProduit(new Produit("Clavier mécanique", 89.99, 20));

        // Lancement de l'interface utilisateur
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceUtilisateur ui = new InterfaceUtilisateur(inventaire);
                ui.setVisible(true);
            }
        });
    }
}