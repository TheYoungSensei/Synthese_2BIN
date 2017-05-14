package domaine;

import java.util.ArrayList;
import java.util.List;

import exceptions.NoCommandeEnCoursException;

public class Client {
	
	private int numero;
	private String nom;
	private String prenom;
	private String tel;
	private Commande commandeEnCours;
	private List<Commande> commandesPassees;
	
	public Client(int numero, String nom, String prenom, String tel) {
		super();
		this.numero = numero;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.commandesPassees = new ArrayList<Commande>();
	}

	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getTel() {
		return tel;
	}

	public Commande getCommandeEnCours() {
		return commandeEnCours;
	}
	
	public boolean ajouterCommandeEnCours(Commande commande) {
		if(this.commandeEnCours == null){
			this.commandeEnCours = commande;
			return true;
		}	else {
			return false;
		}
	}
	
	public boolean cloturerCommandeEnCours() {
		if(this.commandeEnCours == null)
			throw new NoCommandeEnCoursException();
		this.commandesPassees.add(new Commande(this.commandeEnCours));
		this.commandeEnCours = null;
		return true;
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
	
	
}
