import java.time.LocalDate;

public class Etudiant {
  public static final int MOYENNE = 10;
  private static int nbEtudiants = 1;
  private final int id;
  private final String nom;
  private final String prenom;
  private final LocalDate dateNaissance;
  private boolean delegue;

  public Etudiant(String nom, String prenom, LocalDate dateNaissance) {
    super();
    this.id = nbEtudiants++;
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaissance = dateNaissance;
    this.delegue = false;
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public LocalDate getDateNaissance() {
    return dateNaissance;
  }

  public static int getNbEtudiants() {
    return nbEtudiants;
  }

  public boolean isDelegue() {
    return delegue;
  }

  public void setDelegue(boolean delegue) {
    this.delegue = delegue;
  }


}
