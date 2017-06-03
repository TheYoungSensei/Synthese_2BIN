package domaine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static util.Util.*;

public abstract class Pizza implements Iterable<Ingredient> {

	private String description;
	private String titre;
	public static final double PRIX_BASE = 4;
	private List<Ingredient> ingredients = new ArrayList<>();

	public Pizza(String titre, String description) {
		this(titre, description, new ArrayList<Ingredient>());
		this.description = description;
		this.titre = titre;
	}

	public Pizza(String titre, String description, ArrayList<Ingredient> ingredients2) {
		checkString(titre);
		checkString(description);
		checkObject(ingredients);
		
		this.ingredients = ingredients2;
	}

	public double calculerPrix() {
		double tot = PRIX_BASE;
		for (Ingredient ingredient : ingredients) {
			tot += ingredient.getPrix();
		}

		return tot;
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

	@Override
	public Iterator<Ingredient> iterator() {
		return ingredients.iterator();
	}

	public boolean ajouter(Ingredient ingredient) {
		checkObject(ingredient);
		if (ingredients.contains(ingredient))
			return false;
		return this.ingredients.add(ingredient);
	}

	public boolean supprimer(Ingredient ingredient) {
		checkObject(ingredient);
		return this.ingredients.remove(ingredient);
	}

	public String getDescription() {
		return description;
	}

	public String getTitre() {
		return titre;
	}

	public static double getPrixBase() {
		return PRIX_BASE;
	}

	@Override
	public String toString() {
		return "Pizza [titre=" + titre + ", description=" + description + "]";
	}

}
