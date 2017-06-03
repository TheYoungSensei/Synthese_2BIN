package domaine;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static util.Util.checkObject;
import static util.Util.checkString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domaine.JoueurImpl.Niveau;
import exceptions.MinimumMultiplicityException;
public class ClubImpl implements Club {
	public static final int MIN_EQUIPE = 1;
	
	private String nom;
	//TODO Ajouter les champs li�s aux associations
	private Set<Joueur> joueurs = new HashSet<Joueur>();
	private List<Equipe> equipes = new ArrayList<Equipe>();
	
	public ClubImpl(String nom) {
		checkString(nom);
		this.nom = nom;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Club#getNom()
	 */
	@Override
	public String getNom() {
		return nom;
	}
	
	//m�thodes business
	
	// Cette m�thode renvoie une map avec, comme cl�, un niveau et, comme valeur, la liste des joueurs du club de ce niveau.
	// Il ne faut pas reprendre les joueurs non class�s.
	/* (non-Javadoc)
	 * @see domaine.Club#joueursParNiveau()
	 */
	@Override
	public Map<Niveau, List<Joueur>> joueursParNiveau(){
		return joueurs.stream().filter(j->j.getNiveau() != Niveau.NON_CLASSE).collect(groupingBy(Joueur::getNiveau));
	}
	
	//Cette m�thode renvoie l'ensemble des joueurs du club n'appartenant pas � ue �quipe et class� par ordre d�croissant de leur note elo
	/* (non-Javadoc)
	 * @see domaine.Club#joueursSansEquipe()
	 */
	@Override
	public List<Joueur> joueursSansEquipe(){
		return joueurs.stream().filter(j->j.getEquipe()==null).sorted(comparing(Joueur::getElo).reversed()).collect(toList());
	}
	

	//TODO gestion de l'association Equipe - Club 
	
	/* (non-Javadoc)
	 * @see domaine.Club#ajouterEquipe(domaine.Equipe)
	 */
	@Override
	public boolean ajouterEquipe(Equipe equipe) {
		if (this.contientEquipe(equipe)) return false;
		if (equipe.getClub() != this) return false;
		equipes.add(equipe);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see domaine.Club#nombreDEquipes()
	 */
	@Override
	public int nombreDEquipes() {
		return equipes.size();
	}
	
	/* (non-Javadoc)
	 * @see domaine.Club#contientEquipe(domaine.Equipe)
	 */
	@Override
	public boolean contientEquipe(Equipe equipe){
		return equipes.contains(equipe);
	}
	
	/* (non-Javadoc)
	 * @see domaine.Club#equipes()
	 */
	@Override
	public Iterator<Equipe> equipes() {
		return Collections.unmodifiableList(equipes).iterator();
	}

	
	//TODO gestion de l'association Club - Joueur 
	/* (non-Javadoc)
	 * @see domaine.Club#ajouterJoueur(domaine.Joueur)
	 */
	@Override
	public boolean ajouterJoueur(Joueur joueur) {
		if (contientJoueur(joueur)) return false;
		try {
			if (joueur.maximumClubAtteint()&&joueur.getClub()!= this) return false;
		} catch (MinimumMultiplicityException e) {
			throw new InternalError();
		}
		joueurs.add(joueur);
		joueur.enregistrerClub(this);
		return true;
	}

	/* (non-Javadoc)
	 * @see domaine.Club#supprimerJoueur(domaine.Joueur)
	 */
	@Override
	public boolean supprimerJoueur(Joueur joueur) {
		if (!contientJoueur(joueur)) return false;
		joueurs.remove(joueur);
		joueur.supprimerClub();
		return true;
	}

	/* (non-Javadoc)
	 * @see domaine.Club#contientJoueur(domaine.Joueur)
	 */
	@Override
	public boolean contientJoueur(Joueur joueur){
		checkObject(joueur);
		return joueurs.contains(joueur);
	}
	
	/* (non-Javadoc)
	 * @see domaine.Club#nombreDeJoueurs()
	 */
	@Override
	public int nombreDeJoueurs(){
		return joueurs.size();
	}

	/* (non-Javadoc)
	 * @see domaine.Club#iterator()
	 */
	@Override
	public Iterator<Joueur> iterator(){
		return Collections.unmodifiableSet(joueurs).iterator();
	}


}
