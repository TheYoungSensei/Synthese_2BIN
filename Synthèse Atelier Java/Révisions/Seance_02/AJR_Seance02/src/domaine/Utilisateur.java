package domaine;

import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import util.Util;

public class Utilisateur {

  private static Comparator<Objet> compObjets = new Comparator<Objet>() {

    @Override
    public int compare(Objet o1, Objet o2) {
      double delta = o1.meilleureEnchere().getMontant() - o2.meilleureEnchere().getMontant();
      if (delta == 0)
        delta = o2.getNum() - o1.getNum();
      return (delta > 0 ? -1 : (delta == 0 ? 0 : 1));
    }

  };

  private int num;
  private String nom;
  private String prenom;
  private String mail;
  private SortedSet<Objet> objetsAchetes = new TreeSet<>(compObjets);
  private static int numeroSuivant;

  public int getNum() {
    return num;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public String getMail() {
    return mail;
  }

  public Utilisateur(String nom, String prenom, String mail) {
    super();
    Util.checkString(nom);
    Util.checkString(prenom);
    Util.checkString(mail);
    this.num = numeroSuivant++;
    this.nom = nom;
    this.prenom = prenom;
    this.mail = mail;
  }

  public SortedSet<Objet> objetsAchetes() {
    return Collections.unmodifiableSortedSet(objetsAchetes);
  }

  public boolean ajouterObjetAchete(Objet objet) {
    Util.checkObject(objet);
    Enchere meilleurEnchere = objet.meilleureEnchere();
    if (meilleurEnchere == null)
      return false;
    if (objetsAchetes.contains(objet))
      return false;
    if (!meilleurEnchere.getEncherisseur().equals(this))
      return false;
    this.objetsAchetes.add(objet);
    return true;
  }

  public SortedSet<Objet> objetsAchetes(Utilisateur vendeur) {
    Util.checkObject(vendeur);
    SortedSet<Objet> objets = new TreeSet<>(compObjets);
    for (Objet objet : this.objetsAchetes) {
      if (objet.getVendeur().equals(vendeur))
        objets.add(objet);
    }
    return objets;
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
    Utilisateur other = (Utilisateur) obj;
    if (num != other.num)
      return false;
    return true;
  }



}
