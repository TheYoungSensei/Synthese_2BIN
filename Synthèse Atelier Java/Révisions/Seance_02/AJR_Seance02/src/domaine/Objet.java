package domaine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Util;

public class Objet {

  private int num;
  private String description;
  private Utilisateur vendeur;
  private List<Enchere> encheres = new ArrayList<Enchere>();
  private static int numeroSuivant = 1;

  public Objet(String description, Utilisateur vendeur) {
    super();
    Util.checkString(description);
    Util.checkObject(vendeur);
    this.num = numeroSuivant++;
    this.description = description;
    this.vendeur = vendeur;
  }

  public int getNum() {
    return num;
  }

  public String getDescription() {
    return description;
  }

  public Utilisateur getVendeur() {
    return vendeur;
  }

  public double prixDeVente() {
    if (!estVendu())
      return 0;
    return meilleureEnchere().getMontant();
  }

  public Enchere meilleureEnchere() {
    if (encheres.isEmpty())
      return null;
    return encheres.get(encheres.size() - 1);
  }

  public boolean estVendu() {
    try {
      return meilleureEnchere().getEncherisseur().objetsAchetes().contains(this);
    } catch (NullPointerException e) {
      return false;
    }
  }

  protected boolean ajouterEnchere(Enchere enchere) {
    Util.checkObject(enchere);
    if (!enchere.getObjet().equals(this))
      return false;
    if (estVendu())
      return false;
    if (!encheres.isEmpty()) {
      Enchere meilleureEnchere = meilleureEnchere();
      if (meilleureEnchere.getMontant() >= enchere.getMontant())
        return false;
      if (!meilleureEnchere().getLocalDateTime().isBefore(enchere.getLocalDateTime()))
        return false;
    }
    this.encheres.add(enchere);
    return true;
  }

  public List<Enchere> encheres() {
    return Collections.unmodifiableList(encheres);
  }

  public List<Enchere> encheres(LocalDate date) {
    Util.checkObject(date);
    List<Enchere> liste = new ArrayList<Enchere>();
    for (Enchere enchere : encheres) {
      if (enchere.getLocalDateTime().toLocalDate().equals(date))
        liste.add(enchere);
    }
    return liste;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + num;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Objet other = (Objet) obj;
    if (num != other.num)
      return false;
    return true;
  }



}
