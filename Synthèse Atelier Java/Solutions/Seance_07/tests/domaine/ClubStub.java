package domaine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import domaine.JoueurImpl.Niveau;

public class ClubStub implements Club {

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Niveau, List<Joueur>> joueursParNiveau() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Joueur> joueursSansEquipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ajouterEquipe(Equipe equipe) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int nombreDEquipes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contientEquipe(Equipe equipe) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Equipe> equipes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ajouterJoueur(Joueur joueur) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimerJoueur(Joueur joueur) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contientJoueur(Joueur joueur) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int nombreDeJoueurs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Joueur> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
