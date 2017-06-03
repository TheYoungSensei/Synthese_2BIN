package domaine;
import static util.Util.*;
public class LigneCommande {
	private int quantite;
	private double prixUnitaire;
	private Pizza pizza;

	public LigneCommande(int quantite, Pizza pizza) {
		super();
		checkPositive(quantite);
		checkObject(pizza);
		this.quantite = quantite;
		this.pizza = pizza;
		this.prixUnitaire=pizza.calculerPrix();
	}
	public double calculerTotal() {		
		return prixUnitaire*quantite;
	}
	public int getQuantite() {
		return quantite;
	}
	public double getPrixUnitaire() {
		return prixUnitaire;
	}
	public Pizza getPizza() {
		return pizza;
	}
	@Override
	public String toString() {
		return "LigneCommande ["+quantite+" " + pizza.getTitre() + "  à "+ prixUnitaire+"€]";
	}

}
