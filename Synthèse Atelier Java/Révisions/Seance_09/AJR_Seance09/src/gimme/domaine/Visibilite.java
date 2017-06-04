package gimme.domaine;

public enum Visibilite {
	PUBLIC('+'), PRIVATE('-'), PACKAGE('~'), PROTECTED('#');
	private char valeur;

	Visibilite(char c) {
		valeur = c;
	}

	public char getValeur() {
		return valeur;
	}
}
