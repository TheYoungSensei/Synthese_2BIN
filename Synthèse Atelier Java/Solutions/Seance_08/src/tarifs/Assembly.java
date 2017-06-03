package tarifs;

import java.time.LocalDate;

import tarifs.Tarif.TypeReduction;
import exceptions.ExistanteException;
import exceptions.TarifNonDisponibleException;

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

	/*
	 * ici se trouve le business rÃ©el de l'Assembly. Toutes les mÃ©thodes sont
	 * protected afin d'Ãªtre redÃ©finissable par les mocks objects
	 */
	
	protected Billet instanceCreateBillet(Trajet trajet, TypeReduction typeReduction) throws TarifNonDisponibleException {
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

	/*
	 * pour éviter de devoir écrire Assembly.getInstance() partout, ici sont
	 * définis des méthodes statiques sur Assembly qui appellent les méthodes
	 * correspondantes sur l'instance de l'Assembly
	 */
	public static ListeTrajets getListeTrajets() {
		return instance.instanceGetListeTrajets();
	}

	public static ListeTarifs getListeTarifs() {
		return instance.instanceGetListeTarifs();
	}
	
	public static Billet createBillet(Trajet trajet, TypeReduction tyepReduction)
			throws TarifNonDisponibleException {
		return instance.instanceCreateBillet(trajet, tyepReduction);
	}

	public static Billet createBillet(Trajet trajet, TypeReduction typeReduction, LocalDate dateVoyage)
			throws TarifNonDisponibleException {
		return instance.instanceCreateBillet(trajet, typeReduction, dateVoyage);
	}
	
	public static Tarif createTarif(double d, double e) {
		return instance.instanceCreateTarif(d, e);
	}

	public static Trajet createTrajet(String string, String string2, double d) {
		return instance.instanceCreateTrajet(string, string2, d);
	}

}
