import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe représentant l'inventaire de produits et l'historique des ventes.
 */
public class Inventaire {
    private List<Produit> produits;
    private List<Vente> historiqueVentes;

    /**
     * Constructeur de la classe Inventaire.
     * Initialise les listes de produits et d'historique des ventes.
     */
    public Inventaire() {
        this.produits = new ArrayList<>();
        this.historiqueVentes = new ArrayList<>();
    }

    /**
     * Ajoute un produit à l'inventaire.
     * @param produit Le produit à ajouter.
     */
    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    /**
     * Retire un produit de l'inventaire.
     * @param produit Le produit à retirer.
     */
    public void retirerProduit(Produit produit) {
        produits.remove(produit);
    }

    /**
     * Recherche un produit par son nom.
     * @param nom Le nom du produit à rechercher.
     * @return Le produit trouvé ou null si non trouvé.
     */
    public Produit obtenirProduit(String nom) {
        return produits.stream()
                .filter(p -> p.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    /**
     * Met à jour la quantité d'un produit.
     * @param nom Le nom du produit.
     * @param nouvelleQuantite La nouvelle quantité.
     */
    public void mettreAJourQuantite(String nom, int nouvelleQuantite) {
        Produit produit = obtenirProduit(nom);
        if (produit != null) {
            produit.setQuantite(nouvelleQuantite);
        }
    }

    /**
     * Recherche des produits par un terme de recherche.
     * @param terme Le terme de recherche.
     * @return Une liste de produits correspondant au terme de recherche.
     */
    public List<Produit> rechercherProduits(String terme) {
        return produits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(terme.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Effectue une vente et met à jour l'inventaire.
     * @param vente La vente à effectuer.
     * @throws IllegalStateException Si le stock est insuffisant pour un produit.
     */
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
        historiqueVentes.add(vente);
    }

    /**
     * Sauvegarde l'inventaire dans un fichier CSV.
     * @param nomFichier Le nom du fichier où sauvegarder l'inventaire.
     * @throws IOException En cas d'erreur d'écriture dans le fichier.
     */
    public void sauvegarderInventaire(String nomFichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            // Écriture de l'en-tête
            writer.write("Nom,Prix,Quantité\n");
            
            // Écriture des données
            for (Produit produit : produits) {
                writer.write(String.format("%s,%f,%d%n", 
                    produit.getNom().replace(",", "\\,"), 
                    produit.getPrix(), 
                    produit.getQuantite()));
            }
        }
    }

    /**
     * Charge l'inventaire depuis un fichier CSV.
     * @param nomFichier Le nom du fichier à charger.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    public void chargerInventaire(String nomFichier) throws IOException {
        produits.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            boolean premiereLigne = true;
            while ((ligne = reader.readLine()) != null) {
                if (premiereLigne) {
                    premiereLigne = false;
                    continue; // Ignorer l'en-tête
                }
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

    /**
     * Retourne la liste des produits.
     * @return La liste des produits.
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * Retourne l'historique des ventes.
     * @return La liste des ventes effectuées.
     */
    public List<Vente> getHistoriqueVentes() {
        return new ArrayList<>(historiqueVentes);
    }
}