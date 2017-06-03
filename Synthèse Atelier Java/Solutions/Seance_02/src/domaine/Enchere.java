package domaine;

import java.time.LocalDateTime;
import static util.Util.*;

public class Enchere {
	private LocalDateTime localDateTime;
	private double montant;
	private Utilisateur encherisseur;
	private Objet objet;

	public Enchere( Objet objet,LocalDateTime localDateTime, double montant, Utilisateur encherisseur) {
		super();
		checkObject(localDateTime);
		checkPositive(montant);
		checkObject(encherisseur);
		this.localDateTime = localDateTime;
		this.montant = montant;
		this.encherisseur = encherisseur;
		this.objet = objet;
		if (!objet.ajouterEnchere(this)) throw new IllegalArgumentException();
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public double getMontant() {
		return montant;
	}

	public Utilisateur getEncherisseur() {
		return encherisseur;
	}

	public Objet getObjet() {
		return objet;
	}
}
