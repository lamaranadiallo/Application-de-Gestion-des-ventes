import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class FenetreHistoriqueVentes extends JFrame {
    private Inventaire inventaire;
    private JTable tableVentes;
    private DefaultTableModel modeleTable;

    public FenetreHistoriqueVentes(Inventaire inventaire) {
        this.inventaire = inventaire;
        initialiserInterface();
    }

    private void initialiserInterface() {
        setTitle("Historique des Ventes");
        setSize(800, 600);
        setLayout(new BorderLayout());

        String[] colonnes = {"Date", "Nombre de Produits", "Total"};
        modeleTable = new DefaultTableModel(colonnes, 0);
        tableVentes = new JTable(modeleTable);
        JScrollPane scrollPane = new JScrollPane(tableVentes);
        add(scrollPane, BorderLayout.CENTER);

        JButton boutonDetails = new JButton("Voir Détails");
        boutonDetails.addActionListener(e -> afficherDetailsVente());
        add(boutonDetails, BorderLayout.SOUTH);

        afficherHistorique();
    }

    private void afficherHistorique() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Vente vente : inventaire.getHistoriqueVentes()) {
            Object[] row = {
                sdf.format(vente.getDate()),
                vente.getLignesVente().size(),
                String.format("%.2f €", vente.getTotal())
            };
            modeleTable.addRow(row);
        }
    }

    private void afficherDetailsVente() {
        int selectedRow = tableVentes.getSelectedRow();
        if (selectedRow != -1) {
            Vente vente = inventaire.getHistoriqueVentes().get(selectedRow);
            JOptionPane.showMessageDialog(this, vente.toString(), "Détails de la Vente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une vente.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}