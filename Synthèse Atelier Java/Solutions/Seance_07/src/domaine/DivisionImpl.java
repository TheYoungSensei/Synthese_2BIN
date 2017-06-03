package domaine;

import static util.Util.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import exceptions.MinimumMultiplicityException;

public class DivisionImpl implements Division {
	
	public static final int MAX_EQUIPE = 12;
	public static final int MIN_EQUIPE = 12;
	
	private String nom;
	private int nombreJoueursParEquipe;
	//TODO Ajouter les champs li�s aux associations
	private Set<Equipe> equipes = new HashSet<Equipe>();
	
	
	public DivisionImpl(String nom, int nombreJoueursMinimumParEquipe) {
		checkString(nom);
		checkPositive(nombreJoueursMinimumParEquipe);
		this.nom = nom;
		this.nombreJoueursParEquipe = nombreJoueursMinimumParEquipe;
	}

	/* (non-Javadoc)
	 * @see domaine.Division#getNom()
	 */
	@Override
	public String getNom() {
		return nom;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Division#getNombreJoueursMinimumParEquipe()
	 */
	@Override
	public int getNombreJoueursMinimumParEquipe() {
		return nombreJoueursParEquipe;
	}
	
	//TODO gestion de l'association Equipe � Division 
	/* (non-Javadoc)
	 * @see domaine.Division#ajouterEquipe(domaine.Equipe)
	 */
	@Override
	public boolean ajouterEquipe(Equipe equipe){
		if (contientEquipe(equipe)) return false;
		if (maximumEquipesAtteint() ) return false;
		try {
			if (equipe.maximumDivisionAtteint()&&equipe.getDivision()!=this) return false;
		} catch (MinimumMultiplicityException e) {
			throw new InternalError();
		}
		this.equipes.add(equipe);
		equipe.enregistrerDivision(this);
		return true;
	}
	 
	/* (non-Javadoc)
	 * @see domaine.Division#supprimerEquipe(domaine.Equipe)
	 */
	@Override
	public boolean supprimerEquipe(Equipe equipe){
		if (!contientEquipe(equipe)) return false;
		this.equipes.remove(equipe);
		equipe.supprimerDivision();
		return true;
	}

	/* (non-Javadoc)
	 * @see domaine.Division#contientEquipe(domaine.Equipe)
	 */
	@Override
	public boolean contientEquipe(Equipe equipe){
		checkObject(equipe);
		return equipes.contains(equipe);
	}
	/* (non-Javadoc)
	 * @see domaine.Division#equipes()
	 */
	@Override
	public Iterator<Equipe> equipes() throws MinimumMultiplicityException{
		if (!minimumEquipesGaranti()) throw new MinimumMultiplicityException();
		return Collections.unmodifiableSet(equipes).iterator();
	}
	
	/* (non-Javadoc)
	 * @see domaine.Division#nombreDEquipes()
	 */
	@Override
	public int nombreDEquipes(){
		return equipes.size();
	}
	
	/* (non-Javadoc)
	 * @see domaine.Division#maximumEquipesAtteint()
	 */
	@Override
	public boolean maximumEquipesAtteint(){
		return this.equipes.size() == MAX_EQUIPE;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Division#minimumEquipesGaranti()
	 */
	@Override
	public boolean minimumEquipesGaranti(){
		return this.equipes.size() >= MIN_EQUIPE;
	}

}
