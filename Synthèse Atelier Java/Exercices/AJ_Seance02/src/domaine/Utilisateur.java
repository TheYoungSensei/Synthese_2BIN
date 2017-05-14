package domaine;

import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Utilisateur {

	private static int numero = 0;
	private int num;
	private String nom;
	private String prenom;
	private String mail;
	private Comparator<Objet> comparator = new Comparator<Objet>(){

		@Override
		public int compare(Objet objetA, Objet objetB) {
			if(objetA.meilleurEnchere()==null || objetB.meilleurEnchere() == null)
				return objetA.getNum() - objetB.getNum();
			else {
				int retour = (int) (objetA.meilleurEnchere().getPrix() - objetB.meilleurEnchere().getPrix());
				if(retour == 0)return objetA.getNum() - objetB.getNum();
				else return retour;
			}
		}
		
	};
	private SortedSet<Objet> objetsAchetes = new TreeSet<Objet>(comparator);
	
	public Utilisateur(String nom, String prenom, String mail) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.num = numero++;
	}

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
	
	public boolean ajouterObjetAchete(Objet objet) {
		if(this.objetsAchetes.contains(objet))
			return false;
		if(!objet.meilleurEnchere().getEnchereur().equals(this))
			return false;
		return this.objetsAchetes.add(objet);
	}
	
	public SortedSet<Objet> objetsAchetes(){
		return Collections.unmodifiableSortedSet(this.objetsAchetes);
	}
	
	public SortedSet<Objet> objetsAchetes(Utilisateur vendeur) {
		SortedSet<Objet> vendusChez = new TreeSet<Objet>(comparator);
		for(Objet objet : this.objetsAchetes) {
			if(objet.getVendeur().equals(vendeur))
				vendusChez.add(objet);
		}
		return vendusChez;
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
	public String toString() {
		return "Utilisateur [nom=" + nom + ", prenom=" + prenom + "]";
	}
	
	
	
}
