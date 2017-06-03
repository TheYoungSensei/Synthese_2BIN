package gimme.domaine;

public class Propriete {
	private String nom;
	private boolean deClasse;
	private boolean abstrait;
	private Visibilite visibilite;

	public Propriete(String nom, boolean deClasse, boolean abstrait,
			Visibilite visibilite) {
		super();
		this.nom = nom;
		this.deClasse = deClasse;
		this.abstrait = abstrait;
		this.visibilite = visibilite;
	}

	public Propriete(String nom, boolean abstrait) {
		super();
		this.nom = nom;
		this.abstrait = abstrait;
	}

	public Propriete(String nom, boolean deClasse, Visibilite visibilite) {
		super();
		this.nom = nom;
		this.deClasse = deClasse;
		this.abstrait = false;
		this.visibilite = visibilite;
	}

	public String getNom() {
		return nom;
	}

	public boolean isDeClasse() {
		return deClasse;
	}

	public boolean isAbstrait() {
		return abstrait;
	}

	public Visibilite getVisibilite() {
		return visibilite;
	}

}
