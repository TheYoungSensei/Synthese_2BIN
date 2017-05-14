package domaine;

public class LigneCommande {
	
	private int quantite;
	private double prixUnitaire;
	private Pizza pizza;
	
	public LigneCommande(int quantite , Pizza pizza) {
		super();
		this.quantite = quantite;
		this.prixUnitaire = pizza.calculerPrix();
		this.pizza = pizza;
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
	
	public double montantTotal(){
		return this.prixUnitaire * this.quantite;
	}
}
