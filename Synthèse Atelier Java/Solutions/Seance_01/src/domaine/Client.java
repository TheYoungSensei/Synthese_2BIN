package domaine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exceptions.NoCommandeEnCoursException;

import static util.Util.*;

public class Client implements Iterable<Commande> {
	private String nom;
	private String prenom;
	private int numero;
	private String tel;
	private Commande commandeEnCours;
	private List<Commande> commandesPassees = new ArrayList<Commande>();

	public Client(int numero, String nom, String prenom, String tel) {
		super();
		checkString(nom);
		checkString(prenom);
		checkString(tel);
		this.numero = numero;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public int getNumero() {
		return numero;
	}

	public String getTel() {
		return tel;
	}

	@Override
	public Iterator<Commande> iterator() {
		return commandesPassees.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
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
		Client other = (Client) obj;
		if (numero != other.numero)
			return false;
		return true;
	}

	public boolean ajouter(Commande commande) {
		checkObject(commande);
		if (this.commandeEnCours != null)
			return false;
		if (this != commande.getClient())
			return false;
		this.commandeEnCours = commande;
		return true;
	}

	public void cloturer() throws NoCommandeEnCoursException {
		if (this.commandeEnCours == null)
			throw new NoCommandeEnCoursException();
		this.commandesPassees.add(this.commandeEnCours);
		this.commandeEnCours = null;
	}

	public Commande getCommandeEnCours() {
		return commandeEnCours;
	}
	@Override
	public String toString() {
		return "Client [numero=" + numero + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + tel + "]";
	}
}
