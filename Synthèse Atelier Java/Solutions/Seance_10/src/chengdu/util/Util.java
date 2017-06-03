package chengdu.util;

public interface Util {
	public static void checkObject(Object o) {
		if (o == null)
			throw new IllegalArgumentException("objet null");
	}

	public static void checkString(String s) {
		checkObject(s);
		if (s.matches("\\s*"))
			throw new IllegalArgumentException("chaîne vide");
	}

	public static void checkNumerique(String s) {
		checkString(s);
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("numéro invalide");
		}
	}

	public static void checkPositive(double nombre) {
		if (nombre <= 0)
			throw new IllegalArgumentException("valeur négative ou nulle");

	}
	
	public static void checkPositiveOrNul(double nombre){
		if (nombre < 0)
			throw new IllegalArgumentException("valeur négative");
	}
}
