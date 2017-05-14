package tarifs;

import static util.Util.checkObject;

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
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import exceptions.DateDejaPresenteException;
import exceptions.DateNonAutoriseeException;
import exceptions.TarifNonDisponibleException;
import tarifs.Tarif.TypeReduction;

public class ListeTarifs {

  private Map<LocalDate, Tarif> tarifs = new TreeMap<LocalDate, Tarif>(Collections.reverseOrder());
  private static final String NOM_FICHIER = "ListeTarifs.ser";

  public ListeTarifs() {}

  public Tarif getTarif(LocalDate date) {
    checkObject(date);
    for (Entry<LocalDate, Tarif> entry : tarifs.entrySet()) {
      if (entry.getKey().isBefore(date))
        return entry.getValue();
    }
    return null;
  }

  public double getPrix(Trajet trajet, TypeReduction typeReduction, LocalDate dateVoyage)
      throws TarifNonDisponibleException {
    checkObject(trajet);
    checkObject(typeReduction);
    checkObject(dateVoyage);
    Tarif tarif = getTarif(dateVoyage);
    if (tarif == null)
      throw new TarifNonDisponibleException();
    return trajet.getDistance() * tarif.getPrixAuKilometre() * tarif.getReduction(typeReduction)
        + tarif.getPriseEnCharge();
  }

  public void creerTarif(LocalDate date, Tarif tarif)
      throws DateNonAutoriseeException, DateDejaPresenteException, CloneNotSupportedException {
    checkObject(tarif);
    checkObject(date);
    if (date.equals(LocalDate.now()) || date.isBefore(LocalDate.now()))
      throw new DateNonAutoriseeException();
    if (tarifs.containsKey(date))
      throw new DateDejaPresenteException();
    tarifs.put(date, (Tarif) tarif.clone());
  }

  public boolean modifierTarif(LocalDate date, Tarif nouveauTarif)
      throws DateNonAutoriseeException, CloneNotSupportedException {
    checkObject(nouveauTarif);
    checkObject(date);
    if (date.equals(LocalDate.now()) || date.isBefore(LocalDate.now()))
      throw new DateNonAutoriseeException();
    boolean present = this.tarifs.containsKey(date);
    this.tarifs.put(date, (Tarif) nouveauTarif.clone());
    return present;
  }

  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
    String res = "";
    for (LocalDate date : tarifs.keySet()) {
      res += formatter.format(date) + " : " + tarifs.get(date);
    }
    return res;
  }

  public boolean serialize(String nomFichier) {
    Path p = FileSystems.getDefault().getPath(nomFichier);
    try (OutputStream out = Files.newOutputStream(p);
        ObjectOutputStream o = new ObjectOutputStream(out);) {
      o.writeObject(this.tarifs);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean deserialize(String nomFichier) {
    Path p = FileSystems.getDefault().getPath(nomFichier);
    try (InputStream in = Files.newInputStream(p);
        ObjectInputStream o = new ObjectInputStream(in);) {
      Assembly.getInstance().getListeTarifs().tarifs = (TreeMap<LocalDate, Tarif>) o.readObject();
      return true;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean serialize() {
    Assembly.getInstance().getListeTarifs();
    Path p = FileSystems.getDefault().getPath(ListeTarifs.NOM_FICHIER);
    try (OutputStream out = Files.newOutputStream(p);
        ObjectOutputStream o = new ObjectOutputStream(out);) {
      o.writeObject(this.tarifs);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean deserialize() {
    Assembly.getInstance().getListeTarifs();
    Path p = FileSystems.getDefault().getPath(ListeTarifs.NOM_FICHIER);
    try (InputStream in = Files.newInputStream(p);
        ObjectInputStream o = new ObjectInputStream(in);) {
      Assembly.getInstance().getListeTarifs().tarifs = (TreeMap<LocalDate, Tarif>) o.readObject();
      return true;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }



}
