package tarifs;

import java.io.IOException;
import java.time.LocalDate;
import tarifs.Tarif.TypeReduction;
import exceptions.DateDejaPresenteException;
import exceptions.DateNonAutoriseeException;
import exceptions.ExistanteException;
import exceptions.TarifNonDisponibleException;

public class Main {

	public static void main(String[] args)  {

		try {
			new Assembly();
		} catch (ExistanteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Trajet tj1, tj2;
		Tarif tr1, tr2;
		Billet b1 = null, b2 = null;
		ListeTrajets ltr = Assembly.getListeTrajets();
		System.out.println(ltr);
		ListeTarifs ltf = Assembly.getListeTarifs();
		LocalDate date;

		tj1 = Assembly.createTrajet("gare1", "gare2", 11.5);
		/**/tj2 = Assembly.createTrajet("gare1", "gare3", 22.5);
		tr1 = Assembly.createTarif(2.5, 1.1);
		tr2 = Assembly.createTarif(2.7, 1.3);
		tr2.definirReduction(TypeReduction.FAMILLE_NOMBREUSE, .5);
		tr2.definirReduction(TypeReduction.SENIOR, .4);
		tr2.definirReduction(TypeReduction.VIPO, .2);
		tr2.definirReduction(TypeReduction.WEEK_END, .6);
		ltr.ajouterTrajet(tj1);
		ltr.ajouterTrajet(tj2);

		System.out.println(tj1);
		System.out.println(tj2);/**/
		System.out.println(ltr.toString());

		/**/date = LocalDate.of(2017, 9, 5);
		try {
			ltf.creerTarif(date, tr1);
			System.out.println("ok1");
			ltf.creerTarif(date, tr1);
		} catch (DateNonAutoriseeException e) {
			System.out.println("DateNonAutoriseeException");
		} catch (DateDejaPresenteException e) {
			System.out.println("DateDejaPresenteException");
		} /**/

		System.out.println(ltf.toString());

		/**/date = LocalDate.of(2017, 5, 24);
		try {
			ltf.creerTarif(date, tr2);
		} catch (DateNonAutoriseeException e) {
			System.out.println("DateNonAutoriseeException");
		} catch (DateDejaPresenteException e) {
			System.out.println("DateDej‡PresenteException");
		} /**/

		System.out.println(ltf.toString());/**/

		try {
			b1 = Assembly.createBillet(tj1, TypeReduction.TARIF_PLEIN, LocalDate.of(2017, 5, 24));
			b2 = Assembly.createBillet(tj1, TypeReduction.VIPO, LocalDate.of(2017, 5, 24));
		} catch (TarifNonDisponibleException e) {
			System.out.println("TarifNonDisponibleException");
		}
		System.out.println(b1);
		System.out.println(b2);

		/**/
		try {
			ltr.serialize();
			ltf.serialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**/

	}

}
