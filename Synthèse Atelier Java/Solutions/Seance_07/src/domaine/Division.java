package domaine;

import java.util.Iterator;

import exceptions.MinimumMultiplicityException;

public interface Division {

	String getNom();

	int getNombreJoueursMinimumParEquipe();

	//TODO gestion de l'association Equipe � Division 
	boolean ajouterEquipe(Equipe equipe);

	boolean supprimerEquipe(Equipe equipe);

	boolean contientEquipe(Equipe equipe);

	Iterator<Equipe> equipes() throws MinimumMultiplicityException;

	int nombreDEquipes();

	boolean maximumEquipesAtteint();

	boolean minimumEquipesGaranti();

}