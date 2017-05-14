package domaine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Commande implements Iterable<LigneCommande>{
	
	private int num;
	private LocalDateTime date;
	private static int numero;
	private Client client;
	private List<LigneCommande> lignesCommandes;
	
	public Commande(Client client) {
		super();
		this.num = Commande.numero;
		Commande.numero++;
		this.date = LocalDateTime.now();
		this.client = client;
		lignesCommandes = new ArrayList<>();
		if(!this.client.ajouterCommandeEnCours(this))
			throw new IllegalArgumentException("La commande n'a pas pû être ajoutée au client");
	}

	public Commande(Commande commande) {
		this.num = commande.num;
		this.date = commande.date;
		this.client = commande.client;
		lignesCommandes = new ArrayList<>();
		for(LigneCommande li : commande.lignesCommandes){
			this.lignesCommandes.add(li);
		}
	}

	public int getNum() {
		return num;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Client getClient() {
		return client;
	}

	@Override
	public Iterator<LigneCommande> iterator() {
		return this.lignesCommandes.iterator();
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
		Commande other = (Commande) obj;
		if (num != other.num)
			return false;
		return true;
	}
	
	public boolean ajouterLigneCommande(Pizza pizza, int qte) {
		for(LigneCommande li : this.lignesCommandes) {
			if(li.getPizza().equals(pizza))
				return false;
		}
		return this.lignesCommandes.add(new LigneCommande(qte, pizza));
	}
	
	public double montantTotal(){
		double montant = 0;
		for(LigneCommande li : this.lignesCommandes) {
			montant += li.montantTotal();
		}
		return montant;
	}
	
	public String detailler(){
		String lignes = "";
		for(LigneCommande li : this.lignesCommandes) {
			lignes += li.getPizza().getTitre() + " " + li.getQuantite() + " fois au prix de : " + li.getPrixUnitaire() + "\n";
		}
		return lignes;
	}

	@Override
	public String toString() {
		return "Commande N°" + this.num;
	}

	
}
