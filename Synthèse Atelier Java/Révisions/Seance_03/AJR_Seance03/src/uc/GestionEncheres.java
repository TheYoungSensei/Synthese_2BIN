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
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;
import exceptions.EnchereInexistanteException;
import exceptions.ObjetInexistantException;
import exceptions.UtilisateurInexistantException;
import util.Util;

public class GestionEncheres {

  private static Comparator<Enchere> comp =
      Comparator.comparing((Enchere e) -> e.getLocalDateTime())
          .thenComparing((Enchere e) -> e.getEncherisseur().getNum());

  private List<Objet> objetsEnVente = new ArrayList<Objet>();
  private Set<Objet> objetsVendus = new HashSet<Objet>();
  private Map<Integer, Utilisateur> utilisateurs = new HashMap<Integer, Utilisateur>();
  private SortedMap<LocalDate, SortedSet<Enchere>> encheres =
      new TreeMap<LocalDate, SortedSet<Enchere>>();

  private static class Holder {
    private final static GestionEncheres instance = new GestionEncheres();
  }

  private GestionEncheres() {

  };

  public static GestionEncheres getInstance() {
    return Holder.instance;
  }

  private Objet rechercherObjet(Objet objet) throws ObjetInexistantException {
    Util.checkObject(objet);
    int indice = objetsEnVente.indexOf(objet);
    if (indice >= 0)
      return objetsEnVente.get(indice);
    throw new ObjetInexistantException();
  }

  private Utilisateur rechercherUtilisateur(Utilisateur utilisateur)
      throws UtilisateurInexistantException {
    Util.checkObject(utilisateur);
    Utilisateur ust = utilisateurs.get(utilisateur.getNum());
    if (ust == null)
      throw new UtilisateurInexistantException();
    return ust;
  }

  public Utilisateur inscrire(String nom, String prenom, String mail) {
    Utilisateur utilisateur = new Utilisateur(nom, prenom, mail);
    utilisateurs.put(utilisateur.getNum(), utilisateur);
    return utilisateur.clone();
  }

  public Objet mettreEnVente(String description, Utilisateur utilisateur)
      throws UtilisateurInexistantException {
    Util.checkString(description);
    Utilisateur ust = rechercherUtilisateur(utilisateur);
    Objet objet = new Objet(description, ust);
    objetsEnVente.add(objet);
    return objet.clone();
  }

  public Enchere encherir(Objet objet, Utilisateur encherisseur, double montant, LocalDateTime date)
      throws ObjetInexistantException, UtilisateurInexistantException {
    Util.checkPositive(montant);
    Util.checkObject(date);
    Utilisateur ust = rechercherUtilisateur(encherisseur);
    Objet ost = rechercherObjet(objet);
    try {
      LocalDate dateEnchere = date.toLocalDate();
      SortedSet<Enchere> encheresDuJour = encheres.get(dateEnchere);
      if (encheresDuJour != null) {
        for (Enchere e : encheresDuJour) {
          if (e.getLocalDateTime().equals(date) && e.getEncherisseur().equals(ust))
            return null;
        }
      }
      Enchere enchere = new Enchere(ost, date, montant, ust);
      if (encheresDuJour == null) {
        encheresDuJour = new TreeSet<>(comp);
        encheres.put(dateEnchere, encheresDuJour);
      }
      encheresDuJour.add(enchere);
      return enchere;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  public boolean accepter(Objet objet)
      throws ObjetInexistantException, EnchereInexistanteException {
    Objet ost = rechercherObjet(objet);
    Enchere enchere = ost.meilleureEnchere();
    if (enchere == null)
      throw new EnchereInexistanteException();
    Utilisateur encherisseur = utilisateurs.get(enchere.getEncherisseur().getNum());
    if (!encherisseur.ajouterObjetAchete(ost))
      return false;
    objetsEnVente.remove(ost);
    objetsVendus.add(ost);
    return true;
  }

  public List<Objet> listerObjetsEnVente() {
    List<Objet> copie = new ArrayList<Objet>();
    objetsEnVente.forEach((o) -> copie.add(o.clone()));
    return copie;
  }

  public Set<Objet> fournirObjetsVendus() {
    Set<Objet> copie = new HashSet<Objet>();
    objetsVendus.forEach((o) -> copie.add(o.clone()));
    return copie;
  }

  public List<Enchere> listerEncheresDUnObjet(Objet objet) throws ObjetInexistantException {
    Objet ost = rechercherObjet(objet);
    return ost.encheres();
  }

  public List<Enchere> listerEncheresDUnObjet(Objet objet, LocalDate date)
      throws ObjetInexistantException {
    Objet ost = rechercherObjet(objet);
    return ost.encheres(date);
  }

  public SortedSet<Objet> listerObjetsVendus(Utilisateur acheteur, Utilisateur vendeur)
      throws UtilisateurInexistantException {
    Utilisateur ast = rechercherUtilisateur(acheteur);
    Utilisateur vst = rechercherUtilisateur(vendeur);
    return ast.objetsAchetes(vendeur);
  }

  public Enchere fournirMeilleureEnchere(Objet objet) throws ObjetInexistantException {
    Objet ost = rechercherObjet(objet);
    return ost.meilleureEnchere();
  }

  public SortedSet<Enchere> fournirEnchere(LocalDate date) {
    Util.checkObject(date);
    SortedSet<Enchere> encheresDuJour = encheres.get(date);
    if (encheresDuJour == null)
      return new TreeSet<>(comp);
    return Collections.unmodifiableSortedSet(encheresDuJour);
  }

  public Set<Utilisateur> fournirEncherisseurDuJour() {
    LocalDate aujourdhui = LocalDate.now();
    Set<Utilisateur> encherisseurs = new HashSet<Utilisateur>();
    SortedSet<Enchere> enchereDuJour = encheres.get(aujourdhui);
    if (enchereDuJour == null)
      return encherisseurs;
    enchereDuJour.forEach((e) -> encherisseurs.add(e.getEncherisseur()));
    return encherisseurs;
  }

  public SortedSet<Objet> fournirObjetsAchetes(Utilisateur utilisateur)
      throws UtilisateurInexistantException {
    Utilisateur ust = rechercherUtilisateur(utilisateur);
    return ust.objetsAchetes();
  }



}
