package domaine;

import java.time.LocalDateTime;

public class Enchere {
	
	private double prix;
	private LocalDateTime date;
	private Objet objet;
	private Utilisateur enchereur;
	
	public Enchere(double prix, LocalDateTime date, Objet objet, Utilisateur enchereur) {
		super();
		this.prix = prix;
		this.date = date;
		this.objet = objet;
		objet.ajouterEnchere(this);
		this.enchereur = enchereur;
	}

	public double getPrix() {
		return prix;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Objet getObjet() {
		return objet;
	}

	public Utilisateur getEnchereur() {
		return enchereur;
	}

	@Override
	public String toString() {
		return "Enchere [prix=" + prix + ", date=" + date + ", objet=" + objet + ", enchereur=" + enchereur + "]";
	}
	
	
}
