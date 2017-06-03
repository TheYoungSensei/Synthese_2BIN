package domaine;

import java.util.ArrayList;
public class PizzaComposee extends Pizza {
	private double prix;
	private static int REMISE = 15;

	public PizzaComposee(String titre, String description, ArrayList<Ingredient> ingredients) {
		super(titre, description, ingredients);
		this.prix += Math.ceil(super.calculerPrix() * (100 - REMISE) / 100);	
	}

	@Override
	public boolean ajouter(Ingredient ingredient) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean supprimer(Ingredient ingredient) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public double calculerPrix() {
		return prix;
	}
}
