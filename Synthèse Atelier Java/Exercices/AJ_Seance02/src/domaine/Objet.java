package domaine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Objet {
	
	private static int numero = 0;
	private int num;
	private String description;
	private Utilisateur vendeur;
	private List<Enchere> encheres = new ArrayList<Enchere>();
	
	public Objet(String description, Utilisateur vendeur) {
		super();
		this.description = description;
		this.vendeur = vendeur;
		this.num = numero++;
	}

	public int getNum() {
		return num;
	}

	public String getDescription() {
		return description;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}
	
	protected boolean ajouterEnchere(Enchere enchere) {
		if(this.encheres.isEmpty())
			return this.encheres.add(enchere);
		if(meilleurEnchere().getPrix() >= enchere.getPrix() || meilleurEnchere().getDate().isBefore(enchere.getDate()) || estVendu())
			return false;
		return this.encheres.add(enchere);
	}
	
	public List<Enchere> encheres() {
		return Collections.unmodifiableList(this.encheres);
	}
	
	public List<Enchere> encheres(LocalDateTime date) {
		List<Enchere> encheresDatees = new ArrayList<Enchere>();
		for(Enchere enchere : this.encheres) {
			if(enchere.getDate().equals(date))
				encheresDatees.add(enchere);
		}
		return encheresDatees;
	}
	
	public Enchere meilleurEnchere(){
		if(encheres.isEmpty())
			return null;
		return this.encheres.get(encheres.size() - 1);
	}
	
	public boolean estVendu(){
		return meilleurEnchere().getEnchereur().objetsAchetes().contains(this);
	}
	
	public double prixDeVente() {
		if(meilleurEnchere() != null){
			return meilleurEnchere().getPrix();
		} else {
			return 0;
		}
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
	
	

}
