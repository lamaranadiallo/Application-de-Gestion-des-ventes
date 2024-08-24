import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TableauDeBord extends JFrame {
    private Inventaire inventaire;

    public TableauDeBord(Inventaire inventaire) {
        this.inventaire = inventaire;
        initialiserInterface();
    }

    private void initialiserInterface() {
        setTitle("Tableau de Bord");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel panelStats = new JPanel(new GridLayout(0, 2, 10, 10));
        panelStats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelStats.add(new JLabel("Chiffre d'affaires total :"));
        panelStats.add(new JLabel(String.format("%.2f €", calculerChiffreAffairesTotal())));

        panelStats.add(new JLabel("Nombre total de ventes :"));
        panelStats.add(new JLabel(String.valueOf(inventaire.getHistoriqueVentes().size())));

        panelStats.add(new JLabel("Produit le plus vendu :"));
        Produit produitPlusVendu = trouverProduitPlusVendu();
        panelStats.add(new JLabel(produitPlusVendu != null ? produitPlusVendu.getNom() : "Aucun"));

        panelStats.add(new JLabel("Vente la plus importante :"));
        panelStats.add(new JLabel(String.format("%.2f €", trouverVentePlusImportante())));

        add(panelStats, BorderLayout.CENTER);

        JPanel panelProduits = creerPanelTopProduits();
        add(panelProduits, BorderLayout.SOUTH);
    }

    private double calculerChiffreAffairesTotal() {
        return inventaire.getHistoriqueVentes().stream()
                .mapToDouble(Vente::getTotal)
                .sum();
    }

    private Produit trouverProduitPlusVendu() {
        Map<Produit, Integer> ventesParProduit = new HashMap<>();
        for (Vente vente : inventaire.getHistoriqueVentes()) {
            for (LigneVente ligne : vente.getLignesVente()) {
                ventesParProduit.merge(ligne.getProduit(), ligne.getQuantite(), Integer::sum);
            }
        }
        return ventesParProduit.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private double trouverVentePlusImportante() {
        return inventaire.getHistoriqueVentes().stream()
                .mapToDouble(Vente::getTotal)
                .max()
                .orElse(0.0);
    }

    private JPanel creerPanelTopProduits() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Top 5 des produits les plus vendus"));

        List<Map.Entry<Produit, Integer>> topProduits = trouverTopProduits(5);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Map.Entry<Produit, Integer> entry : topProduits) {
            listModel.addElement(String.format("%s - %d vendu(s)", entry.getKey().getNom(), entry.getValue()));
        }

        JList<String> liste = new JList<>(listModel);
        panel.add(new JScrollPane(liste), BorderLayout.CENTER);

        return panel;
    }

    private List<Map.Entry<Produit, Integer>> trouverTopProduits(int n) {
        Map<Produit, Integer> ventesParProduit = new HashMap<>();
        for (Vente vente : inventaire.getHistoriqueVentes()) {
            for (LigneVente ligne : vente.getLignesVente()) {
                ventesParProduit.merge(ligne.getProduit(), ligne.getQuantite(), Integer::sum);
            }
        }
        return ventesParProduit.entrySet().stream()
                .sorted(Map.Entry.<Produit, Integer>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
}