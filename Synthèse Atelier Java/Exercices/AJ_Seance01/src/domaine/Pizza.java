package domaine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Pizza implements Iterable<Ingredient> {

	private String description;
	private String titre;
	public static final double PRIX_BASE = 4;
	private List<Ingredient> ingredients;
	
	public Pizza(String description, String titre, List<Ingredient> ingredients) {
		super();
		this.description = description;
		this.titre = titre;
		this.ingredients = new ArrayList<Ingredient>();
		for(Ingredient ingredient : ingredients) {
			if(!this.ingredients.contains(ingredient)){
				this.ingredients.add(ingredient);
			} else {
				throw new IllegalArgumentException("Ingrédient déjà présent pour cette pizza");
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public String getTitre() {
		return titre;
	}
	
	@Override
	public Iterator<Ingredient> iterator() {
		return this.ingredients.iterator();
	}
	
	public boolean ajouterIngredient(Ingredient ingredient) {
		if(!this.ingredients.contains(ingredient)){
			return this.ingredients.add(ingredient);
		} else {
			return false;
		}
	}
	
	public boolean supprimerIngredient(Ingredient ingredient) {
		return this.ingredients.remove(ingredient);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		Pizza other = (Pizza) obj;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}
	
	public double calculerPrix(){
		double prix = this.PRIX_BASE;
		for(Ingredient ingredient : this.ingredients) {
			prix += ingredient.getPrix();
		}
		return prix;
	}
	
}
