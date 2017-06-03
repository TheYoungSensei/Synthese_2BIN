package domaine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static util.Util.*;

public class Commande implements Iterable<LigneCommande> {
	private int num;
	private LocalDateTime date;
	private static int numero;
	private Client client;
	private List<LigneCommande> lignesCommandes = new ArrayList<>();

	public Commande(Client client) {
		checkObject(client);
		this.date = LocalDateTime.now();
		this.client = client;
		this.num = ++numero;
		if (!client.ajouter(this))
			throw new IllegalArgumentException("le client a déjà une commande en cours");
	}

	public int getNum() {
		return num;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public static int getNumero() {
		return numero;
	}

	public Client getClient() {
		return client;
	}

	public boolean ajouter(Pizza pizza, int quantite) {
		checkObject(pizza);
		checkPositive(quantite);
		LigneCommande li = null;
		for (LigneCommande ligneCommande : lignesCommandes) {
			if (ligneCommande.getPizza().equals(pizza)) {
				li = ligneCommande;
			}
		}
		if (li != null)
			return mettreAJourLigne(quantite, li);
		return lignesCommandes.add(new LigneCommande(quantite, pizza));
	}

	private boolean mettreAJourLigne(int quantite, LigneCommande ligneCommande) {
		checkObject(ligneCommande);
		checkPositive(quantite);
		int qt = ligneCommande.getQuantite();
		lignesCommandes.remove(ligneCommande);
		LigneCommande ligneCommande2 = new LigneCommande(qt + quantite, ligneCommande.getPizza());
		lignesCommandes.add(ligneCommande2);
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
		Commande other = (Commande) obj;
		if (num != other.num)
			return false;
		return true;
	}

	@Override
	public Iterator<LigneCommande> iterator() {
		return lignesCommandes.iterator();
	}

	@Override
	public String toString() {
		String encours = "";
		if (client.getCommandeEnCours() == this)
			encours = "en cours ";
		return "Commande " + encours + " [num=" + num + ", client=" + client + ", date=" + date + "]";
	}

	public double calculerTotal() {
		double total = 0;
		for (LigneCommande ligneCommande : lignesCommandes) {
			total += ligneCommande.calculerTotal();
		}
		return total;

	}

	public String detailler() {
		String detail = "Détails de la commande " + num + " (total :" + calculerTotal() + " €)\n";
		for (LigneCommande ligneCommande : lignesCommandes) {
			detail += ligneCommande + "\n";
		}
		return detail;
	}

}
