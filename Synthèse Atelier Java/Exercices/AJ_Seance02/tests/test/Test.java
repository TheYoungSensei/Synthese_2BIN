package test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;
import util.Util;

public class Test implements Util {

	public static void main(String[] args) {
		Utilisateur vendeur = new Utilisateur("Leconte", "Emmeline",
				"emmeline.leconte@ipl.be");
		Utilisateur vendeur2 = new Utilisateur("Dupont", "Annick",
				"annick.dupont@ipl.be");
		Utilisateur acheteur = new Utilisateur("Damas", "Christophe",
				"Christophe.Damas@ipl.be");
		Utilisateur acheteur2 = new Utilisateur("Frank", "Bernard",
				"Bernard.Frank@ipl.be");
		Objet objetVendu1 = new Objet("bibliothèque", vendeur);
		Enchere enchere1_1 = new Enchere(50,LocalDateTime.of(2015, 2, 7, 13, 48), objetVendu1, acheteur);
		Enchere enchere1_2 = new Enchere(75,LocalDateTime.of(2015, 2, 8, 13, 48),
				objetVendu1, acheteur2);
		try {
			new Enchere(100,LocalDateTime.of(2015, 2, 8, 13, 48),
					objetVendu1, acheteur);
		} catch (IllegalArgumentException e1) {
			// c'est normal.
		}
		List<Enchere> encheres = objetVendu1.encheres(LocalDateTime.of(2015, 2, 8, 13, 48));
		for (Enchere e : encheres){
			System.out.println("Encherisseur : " + e.getEnchereur().getPrenom()+ " "+ e.getEnchereur().getNom() );
			System.out.println("Montant de l'enchère : " + e.getPrix());
		}
		Objet objetVendu2 = new Objet("bureau", vendeur);
		acheteur.ajouterObjetAchete(objetVendu1);
		Enchere enchere2 = new Enchere(150,LocalDateTime.of(2015, 2, 8, 13, 50),
				objetVendu2, acheteur);
		acheteur.ajouterObjetAchete(objetVendu2);
		Objet objetVendu3 = new Objet("table", vendeur2);
		Enchere enchere3 = new Enchere(100,LocalDateTime.of(2015, 2, 9, 13, 50),
				objetVendu3, acheteur);
		acheteur.ajouterObjetAchete(objetVendu3);
		SortedSet<Objet> objAch = acheteur.objetsAchetes(vendeur);
		for (Objet o : objAch){
			System.out.println(o.getNum() + " " +o.getDescription() + " " + o.prixDeVente());
		}
	}
	
}
