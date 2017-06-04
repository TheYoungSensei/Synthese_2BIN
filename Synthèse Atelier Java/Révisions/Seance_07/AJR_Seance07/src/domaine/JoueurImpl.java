package domaine;


import static util.Util.*;

import exceptions.MinimumMultiplicityException;

public class JoueurImpl implements Joueur {
	
	private String nom;
	private String prenom;
	private int elo;
	private Niveau niveau;
	//TODO Ajouter les champs li�s aux associations
	private Club club;
	private Equipe equipe;
	
	
	
	//TODO Cr�er l'�num�r�
	public enum Niveau{
		NON_CLASSE(0,"NC"),DEBUTANT(1000,"D"),JOUEUR_CONFIRME(1600,"JC"),MAITRE_FIDE(2300,"FM"),MAITRE_INTERNATIONAL(2400,"IM"),
		GRAND_MAITRE_INTERNATIONAL(2500,"IGM");
		private int coteMinimale;
		private String abreviation;
		private Niveau(int coteMin, String abreviation){
			this.coteMinimale = coteMin;
			this.abreviation = abreviation;
		}
		
		@Override
		public String toString() {
			return abreviation;
		}
	}
		
	public JoueurImpl(String nom, String prenom, int elo) {
		checkString(nom);
		checkString(prenom);
		setElo(elo);
		this.nom = nom;
		this.prenom = prenom;
	}
	
	//Constructeur pour un joueur non class�
	public JoueurImpl(String nom, String prenom){
		checkString(nom);
		checkString(prenom);
		this.nom = nom;
		this.prenom = prenom;
		this.niveau = Niveau.NON_CLASSE;
	}

	/* (non-Javadoc)
	 * @see domaine.Joueur#getNom()
	 */
	@Override
	public String getNom() {
		return nom;
	}

	/* (non-Javadoc)
	 * @see domaine.Joueur#getPrenom()
	 */
	@Override
	public String getPrenom() {
		return prenom;
	}

	/* (non-Javadoc)
	 * @see domaine.Joueur#getElo()
	 */
	@Override
	public int getElo() {
		return elo;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#setElo(int)
	 */
	@Override
	public void setElo(int elo) {
		if (elo < 1000) throw new IllegalArgumentException();
		for (Niveau n : Niveau.values()){
			if (elo >= n.coteMinimale){
				this.niveau = n;
			} else break;
		}
		this.elo = elo;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#getNiveau()
	 */
	@Override
	public Niveau getNiveau(){
		return niveau;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#toString()
	 */
	@Override
	public String toString() {
		return prenom + " " + nom + "(" + niveau.toString()+")";
	}

	//TODO gestion de l'association Club - Joueur 
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#enregistrerClub(domaine.Club)
	 */
	@Override
	public boolean enregistrerClub(Club club){
		checkObject(club);
		if (this.maximumClubAtteint()) return false;
		this.club = club;
		club.ajouterJoueur(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#supprimerClub()
	 */
	@Override
	public boolean supprimerClub(){
		if (this.club== null) return false;
		Club ex = this.club;
		this.club = null;
		if (this.equipe != null) this.supprimerEquipe();
		ex.supprimerJoueur(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#getClub()
	 */
	@Override
	public Club getClub() throws MinimumMultiplicityException{
		if (!minimumClubGaranti()) throw new MinimumMultiplicityException();
		return this.club;
	}
	/* (non-Javadoc)
	 * @see domaine.Joueur#maximumClubAtteint()
	 */
	@Override
	public boolean maximumClubAtteint(){
		return this.club != null;
	}
	/* (non-Javadoc)
	 * @see domaine.Joueur#minimumClubGaranti()
	 */
	@Override
	public boolean minimumClubGaranti(){
		return this.club != null;
	}
	
	//TODO gestion de l'association Equipe � Joueur 
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#enregistrerEquipe(domaine.Equipe)
	 */
	@Override
	public boolean enregistrerEquipe(Equipe equipe){
		checkObject(equipe);
		if (maximumEquipeAtteint()) return false;
		if (equipe.getClub()!= this.club) return false;
		this.equipe = equipe;
		equipe.ajouterJoueur(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#supprimerEquipe()
	 */
	@Override
	public boolean supprimerEquipe() {
		if (this.equipe == null) return false;
		Equipe ex = this.equipe;
		this.equipe = null;
		ex.supprimerJoueur(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Joueur#getEquipe()
	 */
	@Override
	public Equipe getEquipe() {
		return equipe;
	}
	/* (non-Javadoc)
	 * @see domaine.Joueur#maximumEquipeAtteint()
	 */
	@Override
	public boolean maximumEquipeAtteint(){
		return this.equipe != null;
	}

}
