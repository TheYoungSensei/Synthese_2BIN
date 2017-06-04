package tarifs;

import java.time.LocalDate;

import exceptions.ExistanteException;
import exceptions.TarifNonDisponibleException;
import tarifs.Tarif.TypeReduction;

public class Assembly {

  private ListeTrajets listeTrajets;
  private ListeTarifs listeTarifs;

  protected static Assembly instance;

  public Assembly() throws ExistanteException {
    if (instance != null)
      throw new ExistanteException();
    listeTrajets = new ListeTrajets();
    listeTarifs = new ListeTarifs();
    Assembly.instance = this;
  }

  protected Billet instanceCreateBillet(Trajet trajet, TypeReduction typeReduction)
      throws TarifNonDisponibleException {
    return new Billet(trajet, typeReduction);
  }

  protected Billet instanceCreateBillet(Trajet trajet, TypeReduction typeReduction,
      LocalDate dateVoyage) throws TarifNonDisponibleException {
    return new Billet(trajet, typeReduction, dateVoyage);
  }

  protected Tarif instanceCreateTarif(double d, double e) {
    return new Tarif(d, e);
  }

  protected Trajet instanceCreateTrajet(String string, String string2, double d) {
    return new Trajet(string, string2, d);
  }

  protected ListeTrajets instanceGetListeTrajets() {
    return listeTrajets;
  }

  protected ListeTarifs instanceGetListeTarifs() {
    return listeTarifs;
  }

  public static ListeTrajets getListeTrajets() {
    return instance.instanceGetListeTrajets();
  }

  public static ListeTarifs getListeTarifs() {
    return instance.instanceGetListeTarifs();
  }

  public static Billet createBillet(Trajet trajet, TypeReduction typeReduction)
      throws TarifNonDisponibleException {
    return instance.instanceCreateBillet(trajet, typeReduction);
  }

  public static Billet createBillet(Trajet trajet, TypeReduction typeReduction,
      LocalDate dateVoyage) throws TarifNonDisponibleException {
    return instance.instanceCreateBillet(trajet, typeReduction, dateVoyage);
  }

  public static Tarif createTarif(double d, double e) {
    return instance.instanceCreateTarif(d, e);
  }

  public static Trajet createTrajet(String string, String string2, double d) {
    return instance.instanceCreateTrajet(string, string2, d);
  }
}
