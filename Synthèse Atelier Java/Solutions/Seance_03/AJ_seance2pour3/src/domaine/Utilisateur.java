package domaine;

import static util.Util.checkObject;
import static util.Util.checkString;

import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Utilisateur implements Cloneable {
	private static Comparator<Objet> compObjets = new Comparator<Objet>() {

		@Override
		public int compare(Objet o1, Objet o2) {
			double delta = o1.meilleureEnchere().getMontant() - o2.meilleureEnchere().getMontant();
			if (delta == 0)
				delta = o2.getNum()-o1.getNum();			
			return (delta>0? -1:(delta==0? 0:1));
			}
	};
	private int num;
	private String nom;
	private String prenom;
	private String mail;
	private SortedSet<Objet> objetsAchetes = new TreeSet<>(compObjets);
	private static int numeroSuivant = 1;

	public int getNum() {
		return num;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getMail() {
		return mail;
	}

	public Utilisateur(String nom, String prenom, String mail) {
		super();
		checkString(nom);
		checkString(prenom);
		checkString(mail);
		this.num = numeroSuivant++;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
	}

	public SortedSet<Objet> objetsAchetes() {
		SortedSet<Objet> objets = new TreeSet<>(compObjets);
		for (Objet objet : objetsAchetes) {
			objets.add(objet.clone());
			// il faut cloner les objets car accesseur et cela n'est pas géré
			// par clone()
		}
		return Collections.unmodifiableSortedSet(objets);
	}

	public SortedSet<Objet> objetsAchetes(Utilisateur vendeur) {
		checkObject(vendeur);
		SortedSet<Objet> objets = new TreeSet<>(compObjets);
		for (Objet objet : objetsAchetes) {
			if (objet.getVendeur().equals(vendeur))
				objets.add(objet.clone());
			// il faut cloner les objets car accesseur et cela n'est pas géré
			// par clone()
		}
		return Collections.unmodifiableSortedSet(objets);
	}

	public boolean ajouterObjetAchete(Objet objet) {
		checkObject(objet);
		Enchere meilleureEnc = objet.meilleureEnchere();
		if (meilleureEnc == null)
			return false;
		if (objetsAchetes.contains(objet))
			return false;
		if (!meilleureEnc.getEncherisseur().equals(this))
			return false;
		this.objetsAchetes.add(objet);
		return true;
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
		Utilisateur other = (Utilisateur) obj;
		if (num != other.num)
			return false;
		return true;
	}

	@Override
	public Utilisateur clone() {
		try {
			Utilisateur utilisateur = (Utilisateur) super.clone();
			utilisateur.objetsAchetes = new TreeSet<>(compObjets);
			for (Objet objet : this.objetsAchetes) {
				utilisateur.objetsAchetes.add(objet);
			}
			// il ne faut pas cloner car pour accéder aux objets on utilise
			// objetsAchetes ou objetsAchetes(vendeur) qui se chargent de
			// cloner les objets.

			return utilisateur;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
