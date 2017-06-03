package domaine;

import domaine.JoueurImpl.Niveau;
import exceptions.MinimumMultiplicityException;

public interface Joueur {

	String getNom();

	String getPrenom();

	int getElo();

	void setElo(int elo);

	Niveau getNiveau();

	String toString();

	boolean enregistrerClub(Club club);

	boolean supprimerClub();

	Club getClub() throws MinimumMultiplicityException;

	boolean maximumClubAtteint();

	boolean minimumClubGaranti();

	boolean enregistrerEquipe(Equipe equipe);

	boolean supprimerEquipe();

	Equipe getEquipe();

	boolean maximumEquipeAtteint();

}