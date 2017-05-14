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
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import exceptions.TrajetInconnuException;

public class ListeTrajets {

  private SortedSet<Trajet> trajets = new TreeSet<Trajet>();
  private static final String NOM_FICHIER = "ListeTrajets.ser";

  public double getDistance(String gareDepart, String gareArrivee) throws TrajetInconnuException {
    for (Trajet trajet : trajets) {
      if (trajet.getGareArrivee().equals(gareArrivee) && trajet.getGareDepart().equals(gareDepart))
        return trajet.getDistance();
    }
    throw new TrajetInconnuException();
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
    return trajets.iterator();
  }

  public Iterator<String> garesDArrivee() {
    return trajets.stream().map(Trajet::getGareArrivee).iterator();
  }

  public String toString() {
    String res = "";
    for (Trajet trajet : trajets) {
      res += "Trajet partant de " + trajet.getGareDepart() + " en direction de "
          + trajet.getGareArrivee() + ", distance : " + trajet.getDistance();
    }
    return res;
  }

  public boolean serialize(String nomFichier) {
    Path p = FileSystems.getDefault().getPath(nomFichier);
    try (OutputStream out = Files.newOutputStream(p);
        ObjectOutputStream o = new ObjectOutputStream(out);) {
      o.writeObject(this.trajets);
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
      Assembly.getInstance().getListeTrajets().trajets = (SortedSet<Trajet>) o.readObject();
      return true;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean serialize() {
    Path p = FileSystems.getDefault().getPath(ListeTrajets.NOM_FICHIER);
    try (OutputStream out = Files.newOutputStream(p);
        ObjectOutputStream o = new ObjectOutputStream(out);) {
      o.writeObject(this.trajets);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public static boolean deserialize() {
    Path p = FileSystems.getDefault().getPath(ListeTrajets.NOM_FICHIER);
    try (InputStream in = Files.newInputStream(p);
        ObjectInputStream o = new ObjectInputStream(in);) {
      Assembly.getInstance().getListeTrajets().trajets = (SortedSet<Trajet>) o.readObject();
      return true;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }



}
