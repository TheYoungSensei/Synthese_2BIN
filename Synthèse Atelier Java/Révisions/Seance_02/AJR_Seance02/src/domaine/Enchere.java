package domaine;

import java.time.LocalDateTime;

import util.Util;

public class Enchere {

  private LocalDateTime localDateTime;
  private double montant;
  private Utilisateur encherisseur;
  private Objet objet;

  public Enchere(Objet objet, LocalDateTime localDateTime, double montant,
      Utilisateur encherisseur) {
    super();
    Util.checkObject(localDateTime);
    Util.checkPositive(montant);
    Util.checkObject(encherisseur);
    Util.checkObject(objet);
    this.localDateTime = localDateTime;
    this.montant = montant;
    this.encherisseur = encherisseur;
    this.objet = objet;
    if (!objet.ajouterEnchere(this))
      throw new IllegalArgumentException();
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public double getMontant() {
    return montant;
  }

  public Utilisateur getEncherisseur() {
    return encherisseur;
  }

  public Objet getObjet() {
    return objet;
  }



}
