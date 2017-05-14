package domaine;

import java.util.List;

import javax.print.attribute.UnmodifiableSetException;

public class PizzaComposee extends Pizza{

	private double prix;
	private static final int REMISE = 15;
	
	public PizzaComposee(String description, String titre, List<Ingredient> ingredients) {
		super(description, titre, ingredients);
		this.prix = Math.ceil(super.calculerPrix() * (1 - (REMISE / 100)));
	}
	
	public int getRemise() {
		return REMISE;
	}

	@Override
	public double calculerPrix() {
		return this.prix;
	}

	@Override
	public boolean ajouterIngredient(Ingredient ingredient) {
		throw new UnsupportedOperationException("Il s'agit d'une pizza composée");
	}

	@Override
	public boolean supprimerIngredient(Ingredient ingredient) {
		throw new UnsupportedOperationException("Il s'agit d'une pizza composée");
	}
	
	
	
	
}
