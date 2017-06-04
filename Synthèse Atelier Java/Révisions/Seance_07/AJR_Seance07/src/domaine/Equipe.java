package domaine;

import java.util.Iterator;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;

public interface Equipe {

	int getNumero();

	//m�thode business
	//Cela calcule et renvoie la moyenne des notes elo des joueurs de l'�quipe
	//La m�thode renvoie -1 s'il n'y a pas de joueur dans l'�quipe
	double moyenneEquipe();

	//TODO gestion de l'association Equipe - Club 
	Club getClub();

	boolean enregistrerDivision(Division division);

	boolean supprimerDivision();

	Division getDivision() throws MinimumMultiplicityException;

	boolean maximumDivisionAtteint();

	boolean minimumDivisionGaranti();

	boolean ajouterJoueur(Joueur joueur);

	boolean supprimerJoueur(Joueur joueur);

	boolean contient(Joueur joueur);

	int nombreDeJoueurs();

	Iterator<Joueur> joueurs() throws RenseignementInsuffisantException, MinimumMultiplicityException;

	boolean minimumJoueursGaranti() throws RenseignementInsuffisantException;

}