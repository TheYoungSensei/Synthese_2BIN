package domaine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PizzaComposable extends Pizza{
	
	private LocalDateTime date;
	private Client createur;

	public PizzaComposable(Client createur) {
		super("Pizza de " + createur.getNom() + " " + createur.getPrenom(), "Pizza composable du client " + createur.getNumero(),
				new ArrayList<Ingredient>());
		this.date = LocalDateTime.now();
		this.createur = createur;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Client getCreateur() {
		return createur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((createur == null) ? 0 : createur.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PizzaComposable other = (PizzaComposable) obj;
		if (createur == null) {
			if (other.createur != null)
				return false;
		} else if (!createur.equals(other.createur))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	
}
