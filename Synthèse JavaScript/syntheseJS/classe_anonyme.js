//instanciation d'une classe avec extension ce sera une classe anonyme
Personne etudiant=new Personne() {
	public String toString() {
		return "Etudiant "+super.toString();
	}
}
//equivalent a, sauf que ci-dessus la classe n'a pas le nom Etudiant mais est anonyme
class Etudiant extendsPersonne {
	public String toString() {
		return "Etudiant "+super.toString();
	}
}
Personne etudiant=new Etudiant();