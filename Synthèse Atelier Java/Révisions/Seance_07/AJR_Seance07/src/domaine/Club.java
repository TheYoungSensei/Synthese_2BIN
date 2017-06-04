package domaine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import domaine.JoueurImpl.Niveau;

public interface Club {

	String getNom();

	// Cette m�thode renvoie une map avec, comme cl�, un niveau et, comme valeur, la liste des joueurs du club de ce niveau.
	// Il ne faut pas reprendre les joueurs non class�s.
	Map<Niveau, List<Joueur>> joueursParNiveau();

	//Cette m�thode renvoie l'ensemble des joueurs du club n'appartenant pas � ue �quipe et class� par ordre d�croissant de leur note elo
	List<Joueur> joueursSansEquipe();

	boolean ajouterEquipe(Equipe equipe);

	int nombreDEquipes();

	boolean contientEquipe(Equipe equipe);

	Iterator<Equipe> equipes();

	//TODO gestion de l'association Club - Joueur 
	boolean ajouterJoueur(Joueur joueur);

	boolean supprimerJoueur(Joueur joueur);

	boolean contientJoueur(Joueur joueur);

	int nombreDeJoueurs();

	Iterator<Joueur> iterator();

}