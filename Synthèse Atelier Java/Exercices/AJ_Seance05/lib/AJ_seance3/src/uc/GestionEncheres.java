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

import static util.Util.*;
import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;
import exceptions.EnchereInexistanteException;
import exceptions.ObjetInexistantException;
import exceptions.UtilisateurInexistantException;

public class GestionEncheres {

	private static Comparator<Enchere> comp = Comparator.comparing((Enchere e) -> e.getLocalDateTime())
			.thenComparing((Enchere e) -> e.getEncherisseur().getNum());
	private List<Objet> objetsEnVente = new ArrayList<Objet>();
	private Set<Objet> objetsVendus = new HashSet<Objet>();
	private Map<Integer, Utilisateur> utilisateurs = new HashMap<Integer, Utilisateur>();
	private SortedMap<LocalDate, SortedSet<Enchere>> encheres = new TreeMap<LocalDate, SortedSet<Enchere>>();

	private static class Holder {
		private final static GestionEncheres instance = new GestionEncheres();
	}

	private GestionEncheres() {
	};

	public static GestionEncheres getInstance() {
		return Holder.instance;
	}

	private Objet rechercherObjet(Objet objet) throws ObjetInexistantException {
		checkObject(objet);
		int indice = objetsEnVente.indexOf(objet);
		if (indice >= 0)
			return objetsEnVente.get(indice);
		throw new ObjetInexistantException();
	}

	private Utilisateur rechercherUtilisateur(Utilisateur utilisateur) throws UtilisateurInexistantException {
		checkObject(utilisateur);
		Utilisateur uSt = utilisateurs.get(utilisateur.getNum());
		if (uSt == null)
			throw new UtilisateurInexistantException();
		return uSt;

	}

	public Utilisateur inscrire(String nom, String prenom, String mail) {
		Utilisateur utilisateur = new Utilisateur(nom, prenom, mail);
		utilisateurs.put(utilisateur.getNum(), utilisateur);
		return utilisateur.clone(); // renvoie un clone et conserve la bonne
									// copie dans utilisateurs
	}

	public Objet mettreEnVente(String description, Utilisateur utilisateur) throws UtilisateurInexistantException {
		checkString(description);
		Utilisateur uSt = rechercherUtilisateur(utilisateur);
		Objet objet = new Objet(description, uSt);
		objetsEnVente.add(objet);
		return objet.clone(); // renvoie un clone et conserve la bonne copie
								// dans objetsEnVente
	}

	public Enchere encherir(Objet objet, Utilisateur encherisseur, double montant, LocalDateTime date)
			throws ObjetInexistantException, UtilisateurInexistantException {

		checkPositive(montant);
		checkObject(date);
		Utilisateur uSt = rechercherUtilisateur(encherisseur);
		Objet oSt = rechercherObjet(objet);
		try {
			LocalDate dateEnchere = date.toLocalDate();
			SortedSet<Enchere> encheresDuJour = encheres.get(dateEnchere);
			if (encheresDuJour != null) {
				// parcours des enchères de la même localdatetime pour le même
				// utilisateur pour ne pas ajouter l'enchère car elle ne
				// s'ajoutera pas dans la map à cause du equals/hashCode
				for (Enchere e : encheresDuJour) {
					if (e.getLocalDateTime().equals(date) && e.getEncherisseur().equals(uSt))
						return null;
				}
			}
			Enchere enchere = new Enchere(oSt, date, montant, uSt);
			if (encheresDuJour == null) {
				encheresDuJour = new TreeSet<>(comp);
				encheres.put(dateEnchere, encheresDuJour);
			}
			encheresDuJour.add(enchere);
			return enchere; // enchere immuable
		} catch (IllegalArgumentException e) {
			return null; // Exception lancée par le constructeur d'enchère
		}

	}

	public boolean accepter(Objet objet) throws ObjetInexistantException, EnchereInexistanteException {
		Objet oSt = rechercherObjet(objet);
		Enchere enchere = oSt.meilleureEnchere();
		if (enchere == null)
			throw new EnchereInexistanteException();
		Utilisateur encherisseur = utilisateurs.get(enchere.getEncherisseur().getNum());
		if (!encherisseur.ajouterObjetAchete(oSt))
			return false;
		objetsEnVente.remove(oSt);
		objetsVendus.add(oSt);
		return true;

	}

	public List<Objet> listerObjetsEnVente() {
		List<Objet> copie = new ArrayList<Objet>();
		objetsEnVente.forEach((o) -> copie.add(o.clone()));
		return objetsEnVente;
	}

	public Set<Objet> fournirObjetsVendus() {
		Set<Objet> copie = new HashSet<Objet>();
		objetsVendus.forEach((o) -> copie.add(o.clone()));
		return copie;
	}

	public List<Enchere> listerEncheresDUnObjet(Objet objet) throws ObjetInexistantException {
		Objet oSt = rechercherObjet(objet);
		return oSt.encheres();
	}

	public List<Enchere> listerEncheresDUnObjet(Objet objet, LocalDate date) throws ObjetInexistantException {
		Objet oSt = rechercherObjet(objet);
		return oSt.encheres(date);
	}

	public SortedSet<Objet> listerObjetsVendus(Utilisateur acheteur, Utilisateur vendeur)
			throws UtilisateurInexistantException {
		Utilisateur aSt = rechercherUtilisateur(acheteur);
		Utilisateur vSt = rechercherUtilisateur(vendeur);
		return aSt.objetsAchetes(vSt); // déjà cloné dans Utilisateur
	}

	public Enchere fournirMeilleureEnchere(Objet objet) throws ObjetInexistantException {
		Objet oSt = rechercherObjet(objet);
		return oSt.meilleureEnchere(); // enchère immuable
	}

	// renvoie un ensemble vide s'il n'y a pas d'enchere
	public SortedSet<Enchere> fournirEnchere(LocalDate date) {
		checkObject(date);
		SortedSet<Enchere> encheresDuJour = encheres.get(date);
		if (encheresDuJour == null)
			return new TreeSet<Enchere>(comp);
		return Collections.unmodifiableSortedSet(encheresDuJour); // enchère
																	// immuable
	}

	// renvoie un ensemble vide s'il n'y a pas d'enchere
	public Set<Utilisateur> fournirEncherisseurDuJour() {
		LocalDate aujourdhui = LocalDate.now();
		Set<Utilisateur> encherisseurs = new HashSet<Utilisateur>();
		SortedSet<Enchere> enchereDuJour = encheres.get(aujourdhui);
		if (enchereDuJour == null)
			return encherisseurs;
		enchereDuJour.forEach((e) -> encherisseurs.add(e.getEncherisseur()));
		return encherisseurs; // déjà cloné dans Enchère
	}

	public SortedSet<Objet> fournirObjetsAchetes(Utilisateur utilisateur) throws UtilisateurInexistantException {
		Utilisateur uSt = rechercherUtilisateur(utilisateur);
		return uSt.objetsAchetes(); // déjà cloné dans Utilisateur
	}

}
