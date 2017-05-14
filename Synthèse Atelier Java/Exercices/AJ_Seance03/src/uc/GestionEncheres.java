package uc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;
import exceptions.EnchereInexistanteException;
import exceptions.ObjetInexistantException;
import exceptions.UtilisateurInexistantException;
import util.Util;

public class GestionEncheres {

  private List<Objet> objetsEnVente = new ArrayList<Objet>();
  private Set<Objet> objetsVendus = new HashSet<Objet>();
  private Map<Integer, Utilisateur> utilisateurs = new HashMap<Integer, Utilisateur>();
  private Map<LocalDate, SortedSet<Enchere>> encheres =
      new HashMap<LocalDate, SortedSet<Enchere>>();
  private Comparator<Enchere> comparator = new Comparator<Enchere>() {

    @Override
    public int compare(Enchere enchere1, Enchere enchere2) {
      return (int) (enchere1.getEncherisseur().getNum() - enchere2.getEncherisseur().getNum()
          + enchere1.getLocalDateTime().compareTo(enchere2.getLocalDateTime()));
    }
  };

  private static class Holder {
    private final static GestionEncheres instance = new GestionEncheres();
  }

  private GestionEncheres() {

  }

  public static GestionEncheres getInstance() {
    return Holder.instance;
  }

  private Objet rechercherObjet(Objet objet) throws ObjetInexistantException {
    Util.checkObject(objet);
    int index = this.objetsEnVente.indexOf(objet);
    if (index == -1)
      throw new ObjetInexistantException();
    return this.objetsEnVente.get(index);
  }

  private Utilisateur rechercherUtilisateur(Utilisateur utilisateur)
      throws UtilisateurInexistantException {
    Util.checkObject(utilisateur);
    Utilisateur u = this.utilisateurs.get(utilisateur.getNum());
    if (u == null) {
      throw new UtilisateurInexistantException();
    }
    return u;
  }

  public Utilisateur inscrire(String nom, String prenom, String mail) {
    Utilisateur utilisateur = new Utilisateur(nom, prenom, mail);
    this.utilisateurs.put(utilisateur.getNum(), utilisateur);
    return utilisateur.clone();
  }

  public Objet mettreEnVente(String description, Utilisateur utilisateur)
      throws UtilisateurInexistantException {
    Util.checkString(description);
    Utilisateur util = rechercherUtilisateur(utilisateur);
    Objet objet = new Objet(description, util);
    this.objetsEnVente.add(objet);
    return objet.clone();
  }

  public Enchere encherir(Objet objet, Utilisateur encherisseur, double montant,
      LocalDateTime date) {
    // Util.checkNumerique(Long.toString((long) montant));
    Util.checkPositive(montant);
    Util.checkObject(date);
    Objet monObjet = rechercherObjet(objet);
    Utilisateur monUtil = rechercherUtilisateur(encherisseur);
    try {
      LocalDate dateEnchere = date.toLocalDate();
      SortedSet<Enchere> encheresDuJour = this.encheres.get(dateEnchere);
      if(encheresDuJour != null) {
        for(Enchere e : encheresDuJour) {
          if(e.getLocalDateTime().equals(date) && e.getEncherisseur().equals(monUtil))
            return null;
        }
      }
      Enchere enchere = new Enchere(monObjet, date, montant, monUtil);
      if(encheresDuJour == null) {
        encheresDuJour = new TreeSet<>(comparator);
        encheres.put(dateEnchere, encheresDuJour);
      }
      encheresDuJour.add(enchere);
      return enchere;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  public boolean accepter(Objet objet) {
    Objet monObjet = rechercherObjet(objet);
    Enchere enchere = monObjet.meilleureEnchere();
    if (enchere == null)
      throw new EnchereInexistanteException();
    Utilisateur util = utilisateurs.get(enchere.getEncherisseur().getNum());
    if (!util.ajouterObjetAchete(monObjet)) {
      return false;
    }
    this.objetsEnVente.remove(monObjet);
    return this.objetsVendus.add(monObjet);
  }

  public List<Objet> listerObjetEnVente() {
    List<Objet> copie = new ArrayList<Objet>();
    objetsEnVente.forEach((o) -> copie.add(o.clone()));
    return copie;
  }

  public Set<Objet> fournirObjetsVendus() {
    Set<Objet> copie = new HashSet<Objet>();
    objetsVendus.forEach(o -> copie.add(o.clone()));
    return copie;
  }

  public List<Enchere> listerEncheresDUnObjet(Objet objet) throws ObjetInexistantException {
    Objet monObjet = rechercherObjet(objet);
    return monObjet.encheres();
  }

  public List<Enchere> listerEncheresDunObjet(Objet objet, LocalDate date)
      throws ObjetInexistantException {
    Objet monObjet = rechercherObjet(objet);
    return monObjet.encheres(date);
  }

  public SortedSet<Objet> listerObjetsVendus(Utilisateur acheteur, Utilisateur vend)
      throws UtilisateurInexistantException {
    Utilisateur monAcheteur = rechercherUtilisateur(acheteur);
    Utilisateur monVendeur = rechercherUtilisateur(vend);
    return monAcheteur.objetsAchetes(monVendeur);
  }

  public Enchere fournirMeilleureEnchere(Objet objet)
      throws ObjetInexistantException, EnchereInexistanteException {
    Objet monObjet = rechercherObjet(objet);
    return monObjet.meilleureEnchere();
  }

  public SortedSet<Enchere> fournirEnchere(LocalDate date) {
    Util.checkObject(date);
    SortedSet<Enchere> encheresDuJour = encheres.get(date);
    if(encheresDuJour == null) {
       return new TreeSet<Enchere>(comparator);
    }
    return Collections.unmodifiableSortedSet(encheresDuJour);
  }

  public Set<Utilisateur> fournirEncherisseurDuJour() {
    LocalDate aujourdhui = LocalDate.now();
    Set<Utilisateur> encherisseurs = new HashSet<Utilisateur>();
    SortedSet<Enchere> encheresDuJour = encheres.get(aujourdhui);
    if(encheresDuJour == null) {
      return encherisseurs;
    }
    encheresDuJour.forEach((e) -> encherisseurs.add(e.getEncherisseur()));
    return encherisseurs;
  }

  public Set<Objet> fournirObjetschetes(Utilisateur utilisateur)
      throws UtilisateurInexistantException {
    Utilisateur util = rechercherUtilisateur(utilisateur);
    return util.objetsAchetes();
  }

}
