import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateurs {
    private List<Utilisateur> utilisateurs;

    public GestionUtilisateurs() {
        utilisateurs = new ArrayList<>();
        // Ajout d'utilisateurs par défaut pour les tests
        utilisateurs.add(new Utilisateur(1, "mldiallo", "root123", "directeur"));
        utilisateurs.add(new Utilisateur(2, "caissier", "caissier123", "caissier"));
    }

    public Utilisateur authentifier(String username, String password) {
        return utilisateurs.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    public void supprimerUtilisateur(int idUser) {
        utilisateurs.removeIf(u -> u.getIdUser() == idUser);
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getIdUser() == utilisateur.getIdUser()) {
                utilisateurs.set(i, utilisateur);
                break;
            }
        }
    }

    public List<Utilisateur> getUtilisateurs() {
        return new ArrayList<>(utilisateurs);
    }

    public Utilisateur rechercherUtilisateur(String username) {
        return utilisateurs.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}