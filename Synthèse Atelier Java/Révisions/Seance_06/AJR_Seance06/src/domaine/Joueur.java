package domaine;

import exceptions.MinimumMultiplicityException;
import util.Util;

public class Joueur {

  private String nom;
  private String prenom;
  private int elo;
  private Niveau niveau;
  private Club club;
  private Equipe equipe;

  public enum Niveau {
    NOM_CLASSE(0, "NC"), DEBUTANT(1000, "D"), JOUEUR_CONFIRME(1600, "JC"), MAITRE_FIDE(2300,
        "FM"), MAITRE_INTERNATIONAL(2400, "IM"), GRAND_MAITRE_INTERNATIONAL(2500, "IGM");
    private int coteMinimale;
    private String abreviation;

    private Niveau(int coteMin, String abreviation) {
      this.coteMinimale = coteMin;
      this.abreviation = abreviation;
    }

    @Override
    public String toString() {
      return abreviation;
    }
  }


  // Constructeur pour un joueur classé
  public Joueur(String nom, String prenom, int elo) {
    Util.checkString(prenom);
    Util.checkString(nom);
    setElo(elo);
    this.nom = nom;
    this.prenom = prenom;
  }

  // Constructeur pour un joueur non classé
  public Joueur(String nom, String prenom) {
    Util.checkString(prenom);
    Util.checkString(nom);
    this.niveau = Niveau.NOM_CLASSE;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public int getElo() {
    return elo;
  }

  public void setElo(int elo) {
    if (elo < 1000)
      throw new IllegalArgumentException();
    for (Niveau n : Niveau.values()) {
      if (elo >= n.coteMinimale) {
        this.niveau = n;
      } else
        break;
    }
    this.elo = elo;
  }

  public Niveau getNiveau() {
    return niveau;
  }

  @Override
  public String toString() {
    return prenom + " " + nom + "(" + niveau.toString() + ")";
  }



  // gestion de l'association Club - Joueur

  public boolean enregistrerClub(Club club) {
    Util.checkObject(club);
    if (this.maximumClubAtteint())
      return false;
    this.club = club;
    club.ajouterJoueur(this);
    return true;
  }

  public boolean supprimerClub() {
    if (this.club == null)
      return false;
    Club ex = this.club;
    this.club = null;
    if (this.equipe != null)
      this.supprimerEquipe();
    ex.supprimerJoueur(this);
    return true;
  }

  public Club getClub() throws MinimumMultiplicityException {
    if (!minimumClubGaranti())
      throw new MinimumMultiplicityException();
    return this.club;
  }

  public boolean maximumClubAtteint() {
    return this.club != null;
  }

  public boolean minimumClubGaranti() {
    return this.club != null;
  }

  // gestion de l'association Equipe – Joueur

  public boolean enregistrerEquipe(Equipe equipe) {
    Util.checkObject(equipe);
    if (this.maximumEquipeAtteint())
      return false;
    this.equipe = equipe;
    equipe.ajouterJoueur(this);
    return true;
  }

  public boolean supprimerEquipe() {
    if (this.equipe == null)
      return false;
    Equipe ex = this.equipe;
    this.equipe = null;
    ex.supprimerJoueur(this);
    return true;
  }

  public Equipe getEquipe() {
    return equipe;
  }

  public boolean maximumEquipeAtteint() {
    return this.equipe != null;
  }


}
