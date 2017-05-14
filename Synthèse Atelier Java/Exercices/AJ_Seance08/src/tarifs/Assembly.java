package tarifs;

public class Assembly {

  private ListeTrajets listeTrajets;
  private ListeTarifs listeTarifs;
  private static Assembly instance;

  public static Assembly getInstance() {
    return instance;
  }

  public Assembly() {
    Assembly.instance = this;
    this.listeTarifs = new ListeTarifs();
    this.listeTrajets = new ListeTrajets();
  }

  public ListeTarifs getListeTarifs() {
    return this.listeTarifs;
  }

  public ListeTrajets getListeTrajets() {
    return this.listeTrajets;
  }

}
