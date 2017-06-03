package domaine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import domaine.Joueur.Niveau;
import exceptions.MinimumMultiplicityException;

import static util.Util.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;
public class Club {
	public static final int MIN_EQUIPE = 1;
	
	private String nom;
	//TODO Ajouter les champs liés aux associations
	private Set<Joueur> joueurs = new HashSet<Joueur>();
	private List<Equipe> equipes = new ArrayList<Equipe>();
	
	public Club(String nom) {
		checkString(nom);
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	//méthodes business
	
	// Cette méthode renvoie une map avec, comme clé, un niveau et, comme valeur, la liste des joueurs du club de ce niveau.
	// Il ne faut pas reprendre les joueurs non classés.
	public Map<Niveau, List<Joueur>> joueursParNiveau(){
		return joueurs.stream().filter(j->j.getNiveau() != Niveau.NON_CLASSE).collect(groupingBy(Joueur::getNiveau));
	}
	
	//Cette méthode renvoie l'ensemble des joueurs du club n'appartenant pas à ue équipe et classé par ordre décroissant de leur note elo
	public List<Joueur> joueursSansEquipe(){
		return joueurs.stream().filter(j->j.getEquipe()==null).sorted(comparing(Joueur::getElo).reversed()).collect(toList());
	}
	

	//TODO gestion de l'association Equipe - Club 
	
	public boolean ajouterEquipe(Equipe equipe) {
		if (this.contientEquipe(equipe)) return false;
		if (equipe.getClub() != this) return false;
		equipes.add(equipe);
		return true;
	}
	
	public int nombreDEquipes() {
		return equipes.size();
	}
	
	public boolean contientEquipe(Equipe equipe){
		return equipes.contains(equipe);
	}
	
	public Iterator<Equipe> equipes() {
		return Collections.unmodifiableList(equipes).iterator();
	}

	
	//TODO gestion de l'association Club - Joueur 
	public boolean ajouterJoueur(Joueur joueur) {
		if (contientJoueur(joueur)) return false;
		try {
			if (joueur.maximumClubAtteint()&&joueur.getClub()!= this) return false;
		} catch (MinimumMultiplicityException e) {
			throw new InternalError();
		}
		joueurs.add(joueur);
		joueur.enregistrerClub(this);
		return true;
	}

	public boolean supprimerJoueur(Joueur joueur) {
		if (!contientJoueur(joueur)) return false;
		joueurs.remove(joueur);
		joueur.supprimerClub();
		return true;
	}

	public boolean contientJoueur(Joueur joueur){
		checkObject(joueur);
		return joueurs.contains(joueur);
	}
	
	public int nombreDeJoueurs(){
		return joueurs.size();
	}

	public Iterator<Joueur> iterator(){
		return Collections.unmodifiableSet(joueurs).iterator();
	}


}
