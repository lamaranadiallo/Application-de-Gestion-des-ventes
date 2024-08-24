import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Vente {
    private Date date;
    private List<LigneVente> lignesVente;
    private double total;

    public Vente() {
        this.date = new Date();
        this.lignesVente = new ArrayList<>();
        this.total = 0.0;
    }

    public void ajouterLigne(Produit produit, int quantite) {
        LigneVente ligne = new LigneVente(produit, quantite);
        lignesVente.add(ligne);
        total += ligne.getSousTotal();
    }

    public Date getDate() {
        return date;
    }

    public List<LigneVente> getLignesVente() {
        return lignesVente;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vente du ").append(date).append("\n");
        for (LigneVente ligne : lignesVente) {
            sb.append(ligne).append("\n");
        }
        sb.append("Total: ").append(String.format("%.2f", total)).append(" €");
        return sb.toString();
    }
}

class LigneVente {
    private Produit produit;
    private int quantite;
    private double sousTotal;

    public LigneVente(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
        this.sousTotal = produit.getPrix() * quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getSousTotal() {
        return sousTotal;
    }

    @Override
    public String toString() {
        return String.format("%s x%d : %.2f €", produit.getNom(), quantite, sousTotal);
    }
}