package util;

public interface Util {
	static void checkObject(Object o) {
		if (o == null)
			throw new IllegalArgumentException("L'objet ne peut pas être null");
	}

	static void checkString(String s) {
		checkObject(s);
		if (s.matches("\\s*"))
			throw new IllegalArgumentException(
					"La chaîne ne peut pas être vide");
	}

	static void checkNumerique(String s) {
		checkString(s);
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"La chaîne doit être un nombre valide");
		}
	}

	static void checkPositive(double nombre) {
		if (nombre <= 0)
			throw new IllegalArgumentException(
					"La valeur ne peut pas être négative ou nulle");

	}
}
