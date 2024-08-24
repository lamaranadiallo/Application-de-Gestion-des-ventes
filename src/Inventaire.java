import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventaire {
    private List<Produit> produits;
    private List<Vente> ventes;

    public Inventaire() {
        this.produits = new ArrayList<>();
        this.ventes = new ArrayList<>();
    }

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public void retirerProduit(Produit produit) {
        produits.remove(produit);
    }

    public Produit obtenirProduit(String nom) {
        return produits.stream()
                .filter(p -> p.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    public void mettreAJourQuantite(String nom, int nouvelleQuantite) {
        Produit produit = obtenirProduit(nom);
        if (produit != null) {
            produit.setQuantite(nouvelleQuantite);
        }
    }

    public List<Produit> rechercherProduits(String terme) {
        return produits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(terme.toLowerCase()))
                .collect(Collectors.toList());
    }


    public void effectuerVente(Vente vente) {
        for (LigneVente ligne : vente.getLignesVente()) {
            Produit produit = ligne.getProduit();
            int quantiteVendue = ligne.getQuantite();
            
            if (produit.getQuantite() >= quantiteVendue) {
                produit.setQuantite(produit.getQuantite() - quantiteVendue);
            } else {
                throw new IllegalStateException("Stock insuffisant pour " + produit.getNom());
            }
        }
        ventes.add(vente);
    }

    public void sauvegarderInventaire(String nomFichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            for (Produit produit : produits) {
                writer.write(String.format("%s,%f,%d%n", 
                    produit.getNom().replace(",", "\\,"), 
                    produit.getPrix(), 
                    produit.getQuantite()));
            }
        }
    }

    public void chargerInventaire(String nomFichier) throws IOException {
        produits.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] donnees = ligne.split(",");
                if (donnees.length == 3) {
                    String nom = donnees[0].replace("\\,", ",");
                    double prix = Double.parseDouble(donnees[1]);
                    int quantite = Integer.parseInt(donnees[2]);
                    produits.add(new Produit(nom, prix, quantite));
                }
            }
        }
    }

    public List<Produit> getProduits() {
        return produits;
    }
    
    public List<Vente> getVentes() {
        return ventes;
    }

}