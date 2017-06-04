package domaine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;

import static util.Util.*;

public class EquipeImpl implements Equipe {
	
	private int numero;
	//TODO Ajouter les champs li�s aux associations
	private Division division;
	private Club club;
	private Set<Joueur> joueurs = new HashSet<Joueur>();
	
	public EquipeImpl(Club club) {
		checkObject(club);
		this.club = club;
		this.numero = club.nombreDEquipes() +1;
		club.ajouterEquipe(this);
	}

	/* (non-Javadoc)
	 * @see domaine.Equipe#getNumero()
	 */
	@Override
	public int getNumero() {
		return numero;
	}

	//m�thode business
	//Cela calcule et renvoie la moyenne des notes elo des joueurs de l'�quipe
	//La m�thode renvoie -1 s'il n'y a pas de joueur dans l'�quipe
	/* (non-Javadoc)
	 * @see domaine.Equipe#moyenneEquipe()
	 */
	@Override
	public double moyenneEquipe(){
		return joueurs.stream().mapToDouble(Joueur::getElo).average().orElse(-1);
	}
	
	
	//TODO gestion de l'association Equipe - Club 
	/* (non-Javadoc)
	 * @see domaine.Equipe#getClub()
	 */
	@Override
	public Club getClub() {
		return club;
	}
	
	//TODO gestion de l'association Equipe � Division 

	/* (non-Javadoc)
	 * @see domaine.Equipe#enregistrerDivision(domaine.Division)
	 */
	@Override
	public boolean enregistrerDivision(Division division){
		if (maximumDivisionAtteint()) return false;
		if (division.maximumEquipesAtteint() && ! division.contientEquipe(this)) return false;
		this.division = division;
		division.ajouterEquipe(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#supprimerDivision()
	 */
	@Override
	public boolean supprimerDivision(){
		if (this.division == null) return false;
		Division ex = this.division;
		this.division = null;
		division.supprimerEquipe(this);
		return true;
	}
	/* (non-Javadoc)
	 * @see domaine.Equipe#getDivision()
	 */
	@Override
	public Division getDivision() throws MinimumMultiplicityException{
		if (!minimumDivisionGaranti()) throw new MinimumMultiplicityException();
		return this.division;
	}
	/* (non-Javadoc)
	 * @see domaine.Equipe#maximumDivisionAtteint()
	 */
	@Override
	public boolean maximumDivisionAtteint(){
		return this.division != null;
	}
	/* (non-Javadoc)
	 * @see domaine.Equipe#minimumDivisionGaranti()
	 */
	@Override
	public boolean minimumDivisionGaranti(){
		return this.division == null;
	}

	//TODO gestion de l'association Equipe � Joueur 
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#ajouterJoueur(domaine.Joueur)
	 */
	@Override
	public boolean ajouterJoueur(Joueur joueur) {
		if (contient(joueur)) return false;
		if (!club.contientJoueur(joueur)) return false;
		if (joueur.maximumEquipeAtteint() && joueur.getEquipe() != this) return false;
		joueurs.add(joueur);
		joueur.enregistrerEquipe(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#supprimerJoueur(domaine.Joueur)
	 */
	@Override
	public boolean supprimerJoueur(Joueur joueur) {
		if (!contient(joueur)) return false;
		joueurs.remove(joueur);
		joueur.supprimerEquipe();		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#contient(domaine.Joueur)
	 */
	@Override
	public boolean contient(Joueur joueur){
		checkObject(joueur);
		return joueurs.contains(joueur);
	}
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#nombreDeJoueurs()
	 */
	@Override
	public int nombreDeJoueurs(){
		return joueurs.size();
	}
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#joueurs()
	 */
	@Override
	public Iterator<Joueur> joueurs() throws RenseignementInsuffisantException, MinimumMultiplicityException {
		if (!minimumJoueursGaranti()) throw new MinimumMultiplicityException();
		return Collections.unmodifiableSet(joueurs).iterator();
	}
	
	/* (non-Javadoc)
	 * @see domaine.Equipe#minimumJoueursGaranti()
	 */
	@Override
	public boolean minimumJoueursGaranti() throws RenseignementInsuffisantException{
		if (this.division == null) throw new RenseignementInsuffisantException();
		return nombreDeJoueurs() >= division.getNombreJoueursMinimumParEquipe();
	}





}
