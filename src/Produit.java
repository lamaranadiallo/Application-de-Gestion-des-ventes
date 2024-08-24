public class Produit {
    // Attributs privés de la classe
    private String nom;
    private double prix;
    private int quantite;

    // Constructeur
    public Produit(String nom, double prix, int quantite) {
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // Méthode pour afficher les informations du produit
    @Override
    public String toString() {
        return "Produit{" +
                "nom='" + nom + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                '}';
    }
}