package domaine;

import java.time.LocalDateTime;
import static util.Util.*;

public class Enchere{
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
		return encherisseur.clone();
	}

	public Objet getObjet() {
		return objet.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encherisseur == null) ? 0 : encherisseur.hashCode());
		result = prime * result + ((localDateTime == null) ? 0 : localDateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enchere other = (Enchere) obj;
		if (encherisseur == null) {
			if (other.encherisseur != null)
				return false;
		} else if (!encherisseur.equals(other.encherisseur))
			return false;
		if (localDateTime == null) {
			if (other.localDateTime != null)
				return false;
		} else if (!localDateTime.equals(other.localDateTime))
			return false;
		return true;
	}

}
