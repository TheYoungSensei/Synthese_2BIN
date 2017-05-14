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

  public static enum Niveau {
    NON_CLASSE(0, "NC"), DEBUTANT(1000, "D"), JOUEUR_CONFIRME(1600, "JC"), MAITRE_FIDE(2300,
        "FM"), MAITRE_INTERNATIONAL(2400, "IM"), GRAND_MAITRE_INTERNATIONAL(2500, "IGM");
    double coteMinimale;
    String abreviation;

    private Niveau(double cote, String abr) {
      coteMinimale = cote;
      abreviation = abr;
    }

    public double getCoteMinimale() {
      return coteMinimale;
    }

    public String getAbreviation() {
      return abreviation;
    }

    public String toString() {
      return abreviation;
    }
  }



  // Constructeur pour un joueur classé
  public Joueur(String nom, String prenom, int elo) {
    this.nom = nom;
    this.prenom = prenom;
    setElo(elo);
  }

  // Constructeur pour un joueur non classé
  public Joueur(String nom, String prenom) {
    this.nom = nom;
    this.prenom = prenom;
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

  public Niveau getNiveau() {
    return niveau;
  }

  @Override
  public String toString() {
    return prenom + " " + nom + "(" + niveau.toString() + ")";
  }

  public void setElo(int elo) {
    this.elo = elo;
    for (Niveau niv : Niveau.values()) {
      if (niv.coteMinimale < this.elo)
        this.niveau = niv;
    }
  }


  /*
   * Gestion de l'association Club - Joueur
   */

  public boolean minimumClubGaranti() {
    return this.club != null;
  }

  public Club getClub() throws MinimumMultiplicityException {
    if (!minimumClubGaranti())
      throw new MinimumMultiplicityException();
    return club;
  }

  public boolean maximumClubAtteint() {
    return this.club != null;
  }

  public boolean enregistrerClub(Club c) {
    Util.checkObject(c);
    if (this.club == c)
      return false;
    if (maximumClubAtteint())
      return false;
    if (c.maximumJoueursAtteints() && !c.contientJoueur(this))
      return false;
    this.club = c;
    c.ajouterJoueur(this);
    return true;
  }

  public boolean supprimerClub() {
    if (this.club == null)
      return false;
    Club club = this.club;
    this.club = null;
    club.supprimerJoueur(this);
    return true;
  }

  /*
   * Gestion de l'association Equipe – Joueur
   */

  public boolean minimumEquipeGaranti() {
    return this.equipe != null;
  }

  public Equipe getEquipe() throws MinimumMultiplicityException {
    if (!minimumEquipeGaranti())
      throw new MinimumMultiplicityException();
    return this.equipe;
  }
  
  public boolean maximumEquipeAtteint() {
    return this.equipe != null;
  }
  
  public boolean enregistrerEquipe(Equipe equipe) {
    Util.checkObject(equipe);
    if(this.equipe == equipe)
      return false;
    if(maximumEquipeAtteint())
      return false;
    if(equipe.maximumJoueursAtteint() && !equipe.contientJoueur(this))
      return false;
    this.equipe = equipe;
    equipe.ajouterJoueur(this);
    return true;
  }
  
  public boolean supprimerEquipe() {
    if(this.equipe == null)
      return false;
    Equipe ex = this.equipe;
    this.equipe = null;
    ex.supprimerJoueur(this);
    return true;
  }

}
