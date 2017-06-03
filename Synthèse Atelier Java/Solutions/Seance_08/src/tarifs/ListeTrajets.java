package tarifs;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import exceptions.TrajetInconnuException;
import static util.Util.*;

public class ListeTrajets {
	private static String nomFichierParDefaut = "trajets.ser";
	private SortedSet<Trajet> trajets = new TreeSet<>();

	public double getDistance(String gareDepart, String gareArrivee) throws TrajetInconnuException {
		Trajet t = null;
		t = new Trajet(gareDepart, gareArrivee, 0.1);
		for (Trajet tt : trajets) {
			if (tt.equals(t))
				return tt.getDistance();
		}
		throw new TrajetInconnuException();
		
		//return trajets.stream().filter(tr->tr.equals(t)).map(Trajet :: getDistance).findFirst().orElseThrow(TrajetInconnuException::new);
	}

	public boolean contient(Trajet t) {
		checkObject(t);
		return trajets.contains(t);
	}

	public boolean ajouterTrajet(Trajet t) {
		checkObject(t);
		return trajets.add(t);
	}

	public boolean supprimerTrajet(Trajet t) {
		checkObject(t);
		return trajets.remove(t);
	}

	public Iterator<Trajet> trajets() {
		return Collections.unmodifiableSortedSet(trajets).iterator();
	}

	public Iterator<String> garesDArrivee() {
		SortedSet<String> gares = new TreeSet<String>();
		for (Trajet t : trajets) {
			gares.add(t.getGareArrivee());
		}
		return gares.iterator();
		//   return trajets.stream().map(Trajet :: getGareArrivee).distinct().sorted().iterator();
	}

	public String toString() {
		String res = "";
		for (Trajet t : trajets) {
			res += t + "\n";
		}
		return res;
	}
	
	public boolean serialize(String nomFichier) throws IOException {
		checkString(nomFichier);
		Path p = FileSystems.getDefault().getPath(nomFichier);
		if (Files.exists(p) && !Files.isWritable(p))
			return false;
		try (OutputStream out = Files.newOutputStream(p); ObjectOutputStream o = new ObjectOutputStream(out);) {
			o.writeObject(trajets);
		}
		return true;
	}
	
	public boolean serialize() throws IOException {
		return serialize(nomFichierParDefaut);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean deserialize(String nomFichier) throws IOException, ClassNotFoundException {
		checkString(nomFichier);
		Path p = FileSystems.getDefault().getPath(nomFichier);
		if (Files.exists(p) && !Files.isReadable(p))
			return false;
		try (InputStream in = Files.newInputStream(p); ObjectInputStream o = new ObjectInputStream(in);) {
			Assembly.getListeTrajets().trajets = (SortedSet<Trajet>) o.readObject();
		}
		return true;
	   }
	
	public static boolean deserialize() throws IOException, ClassNotFoundException {
		return deserialize(nomFichierParDefaut);
	}
}
