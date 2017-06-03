package exercices;

public class Etudiant{
	private String email;
	private String matricule;
	private String nom;
	private String prenom;
	
	public Etudiant(String email, String matricule) {
		super();
		this.email = email;
		this.matricule = matricule;
	}
	
	public String getEmail() {
		return email;
	}
	public String getMatricule() {
		return matricule;
	}

	public Etudiant(String toSplit) {
		super();
		String[] mots = toSplit.split(";");
		this.email = mots[3];
		this.matricule = mots[0];
		this.nom = mots[1];
		this.prenom = mots[2];
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nom+" "+ prenom+ " ["+ matricule +"] - "+email;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
}
