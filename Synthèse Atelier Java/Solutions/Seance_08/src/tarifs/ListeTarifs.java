package tarifs;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import exceptions.DateDejaPresenteException;
import exceptions.DateNonAutoriseeException;
import exceptions.TarifNonDisponibleException;
import tarifs.Tarif;
import tarifs.Trajet;
import tarifs.Tarif.TypeReduction;

import static java.util.Collections.*;
import static util.Util.*;

public class ListeTarifs {
	private static String nomFichierParDefaut = "tarifs.ser";
	private SortedMap<LocalDate, Tarif> tarifs;

	public ListeTarifs() {
		tarifs = new TreeMap<LocalDate, Tarif>(reverseOrder());
	}

	public Tarif getTarif(LocalDate date) {
		checkObject(date);
		for (Entry<LocalDate, Tarif> e : tarifs.entrySet()) {
			if (!e.getKey().isAfter(date))
				return e.getValue();
		}
		return null;
	}

	public double getPrix(Trajet trajet, TypeReduction typeReduction, LocalDate dateVoyage) throws TarifNonDisponibleException {
		checkObject(trajet);
		checkObject(typeReduction);
		checkObject(dateVoyage);
		Tarif tarif = getTarif(dateVoyage);
		if (tarif == null)
			throw new TarifNonDisponibleException();
		double prix = tarif.getPriseEnCharge()
				+ tarif.getReduction(typeReduction) * tarif.getPrixAuKilometre() * trajet.getDistance();
		return prix;
	}
	public void creerTarif(LocalDate date, Tarif tarif)
			throws  DateNonAutoriseeException,
			DateDejaPresenteException {
		checkObject(tarif);
		checkObject(date);

		if (!(date.isAfter(LocalDate.now())))
			throw new DateNonAutoriseeException();
		if (tarifs.containsKey(date))
			throw new DateDejaPresenteException();
		tarifs.put(date, tarif.clone());
	}

	public boolean modifierTarif(LocalDate date, Tarif nouveauTarif)
			throws DateNonAutoriseeException {
		checkObject(nouveauTarif);
		checkObject(date);
	
		if (!(date.isAfter(LocalDate.now())))
			throw new DateNonAutoriseeException();
		if (!tarifs.containsKey(date))
			return false;
		tarifs.put(date, nouveauTarif.clone());
		return true;
	}
	
	public String toString () {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
		String res = "";
		for (Entry<LocalDate, Tarif> e : tarifs.entrySet()){
			res += "Tarif du " + formatter.format(e.getKey()) + "\n" + e.getValue();
		}
		return res;
	}
	
	public boolean serialize(String nomFichier) throws IOException {
		checkString(nomFichier);
		Path p = FileSystems.getDefault().getPath(nomFichier);
		if (Files.exists(p) && !Files.isWritable(p))
			return false;
		try (OutputStream out = Files.newOutputStream(p); ObjectOutputStream o = new ObjectOutputStream(out);) {
			o.writeObject(tarifs);
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
			Assembly.getListeTarifs().tarifs = (SortedMap<LocalDate, Tarif>) o.readObject();
		}
		return true;
	   }
	
	public static boolean deserialize() throws IOException, ClassNotFoundException {
		return deserialize(nomFichierParDefaut);
	}

}
