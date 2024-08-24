public class Utilisateur {
    private int idUser;
    private String username;
    private String password;
    private String fonction;

    public Utilisateur(int idUser, String username, String password, String fonction) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.fonction = fonction;
    }

    // Getters
    public int getIdUser() { return idUser; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFonction() { return fonction; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFonction(String fonction) { this.fonction = fonction; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}