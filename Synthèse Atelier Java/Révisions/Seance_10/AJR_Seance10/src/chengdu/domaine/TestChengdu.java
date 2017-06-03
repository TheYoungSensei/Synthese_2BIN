package chengdu.domaine;

import chengdu.exceptions.LotDejaEnVenteException;
import chengdu.exceptions.ThisIsNotMyBatchException;

public class TestChengdu {

	/**
	 * @param args
	 * @throws LotDejaEnVenteException
	 * @throws ArgumentInvalideException
	 * @throws ThisIsNotMyBatchException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws LotDejaEnVenteException,
			ThisIsNotMyBatchException, InterruptedException {

		// Personnes

		Personne stephanie = new Personne("Stefanie", 3000);
		Personne emmeline = new Personne("Emmeline", 5000);
		Personne toto = new Personne("Toto", 3000);

		// Lots

		Lot lot1 = new Lot(500, stephanie);
		Lot lot2 = new Lot(500, stephanie);
		Lot lot3 = new Lot(500, emmeline);

		// Mise en vente
		Thread v1 = stephanie.mettreUnLotEnVente(lot1, 10, 1, 1, 2000);
		try {
			toto.mettreUnLotEnVente(lot2, 11, 2, 3, 3000);
		} catch (ThisIsNotMyBatchException e) {

			System.out
					.println(toto.getNom()
							+ " ne peut mettre en vente un lot qui ne lui appartient pas ("
							+ lot2.getNumero() + ")");
		}
		Thread v2 = emmeline.mettreUnLotEnVente(lot3, 15, 1, 5, 2000);

		// Achat
		Thread a1 = toto.lancerUnAcheteur(20000, 8);
		Thread a2 = emmeline.lancerUnAcheteur(100000, 8);
		Thread a3 = stephanie.lancerUnAcheteur(100000, 8);

		// On attend
		Thread.sleep(100000);

		// On tue les vendeurs et acheteurs restants
		v1.interrupt();
		v2.interrupt();
		a1.interrupt();
		a2.interrupt();
		a3.interrupt();

	}

}
