package domaine;

import java.util.Iterator;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;

public class EquipeStub implements Equipe {
	
	private int numero;
	private Division division;
	private Club club;
	private boolean supprimerDivision;
	private boolean maximumDivisionAtteint;
	private boolean enregistrerDivision;
	
	public EquipeStub(int numero, Club club, Division division, boolean supprimerDivision, boolean maximumDivisionAtteint, boolean enregistrerDivision) {
		this.numero = numero;
		this.club = club;
		this.division = division;
		this.supprimerDivision = supprimerDivision;
		this.maximumDivisionAtteint = maximumDivisionAtteint;
		this.enregistrerDivision = enregistrerDivision;
	}

	@Override
	public int getNumero() {
		return this.numero;
	}

	@Override
	public double moyenneEquipe(){
		return -1;
	}
	
	@Override
	public Club getClub() {
		return this.club;
	}

	@Override
	public boolean enregistrerDivision(Division division){
		return this.enregistrerDivision;
	}
	
	@Override
	public boolean supprimerDivision(){
		return this.supprimerDivision;
	}
	
	@Override
	public Division getDivision() throws MinimumMultiplicityException{
		return this.division;
	}
	
	@Override
	public boolean maximumDivisionAtteint(){
		return this.maximumDivisionAtteint;
	}
	
	@Override
	public boolean minimumDivisionGaranti(){
		return false;
	}

	@Override
	public boolean ajouterJoueur(Joueur joueur) {
		return false;
	}
	
	@Override
	public boolean supprimerJoueur(Joueur joueur) {
		return false;
	}
	
	@Override
	public boolean contient(Joueur joueur){
		return false;
	}
	
	@Override
	public int nombreDeJoueurs(){
		return 0;
	}
	
	@Override
	public Iterator<Joueur> joueurs() throws RenseignementInsuffisantException, MinimumMultiplicityException {
		return null;
	}
	
	@Override
	public boolean minimumJoueursGaranti() throws RenseignementInsuffisantException{
		return false;
	}

}