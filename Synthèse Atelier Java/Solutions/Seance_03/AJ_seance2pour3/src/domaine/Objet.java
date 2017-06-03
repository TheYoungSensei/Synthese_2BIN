package domaine;

import static util.Util.checkObject;
import static util.Util.checkString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Objet implements Cloneable {
	private int num;
	private String description;
	private Utilisateur vendeur;
	private List<Enchere> encheres = new ArrayList<>();
	private static int numeroSuivant = 1;

	public Objet(String description, Utilisateur vendeur) {
		super();
		checkString(description);
		checkObject(vendeur);
		this.num = numeroSuivant++;
		this.description = description;
		this.vendeur = vendeur;
	}

	public int getNum() {
		return num;
	}

	public String getDescription() {
		return description;
	}

	public Utilisateur getVendeur() {
		return vendeur.clone();
	}

	public double prixDeVente() {
		if (!estVendu())
			return 0;
		return meilleureEnchere().getMontant();
	}

	public Enchere meilleureEnchere() {
		if (encheres.isEmpty())
			return null;
		return encheres.get(encheres.size() - 1);
	}

	public boolean estVendu() {
		try {
			return meilleureEnchere().getEncherisseur().objetsAchetes().contains(this);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean ajouterEnchere(Enchere enchere) {
		checkObject(enchere);
		if (!enchere.getObjet().equals(this))
			return false;
		if (estVendu())
			return false;
		if (!encheres.isEmpty()) {
			Enchere meilleureEnchere = meilleureEnchere();
			if (meilleureEnchere.getMontant() >= enchere.getMontant())
				return false;
			if (!meilleureEnchere.getLocalDateTime().isBefore(enchere.getLocalDateTime())) {
				return false;
			}
		}
		this.encheres.add(enchere);
		return true;
	}

	public List<Enchere> encheres() {
		return Collections.unmodifiableList(encheres);
	}

	public List<Enchere> encheres(LocalDate date) {
		checkObject(date);
		List<Enchere> liste = new ArrayList<Enchere>();
		for (Enchere e : encheres) {
			if (e.getLocalDateTime().toLocalDate().equals(date))
				liste.add(e);
		}
		return liste;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num;
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
		Objet other = (Objet) obj;
		if (num != other.num)
			return false;
		return true;
	}

	@Override
	public Objet clone() {
		try {
			Objet objet = (Objet) super.clone();
			objet.vendeur = this.vendeur; // inutile de cloner car ses
											// propriétés le sont
			objet.encheres = new ArrayList<>();
			for (Enchere enchere : encheres) {
				objet.encheres.add(enchere);
			}
			return objet;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
