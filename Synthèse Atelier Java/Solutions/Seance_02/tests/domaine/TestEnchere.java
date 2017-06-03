package domaine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;

public class TestEnchere {
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
		 new Enchere(objetVendu1,LocalDateTime.of(2015, 2, 7, 13, 48),
				50, acheteur);
		 new Enchere(objetVendu1,LocalDateTime.of(2015, 2, 8, 13, 48),
				75, acheteur2);
		try {
			new Enchere(objetVendu1,LocalDateTime.of(2015, 2, 8, 13, 48),
					150, acheteur);
			throw new InternalError("Il faut une exception car enchère exactement en même temps et au même montant ");
		} catch (IllegalArgumentException e1) {
			//c'est normal, enchère exactement en même temps et au même montant.
		}
		List<Enchere> encheres = objetVendu1.encheres();
		for (Enchere e : encheres){
			System.out.println("Encherisseur : " + e.getEncherisseur().getPrenom()+ " "+ e.getEncherisseur().getNom() );
			System.out.println("Montant de l'enchère : " + e.getMontant());
		}
		Objet objetVendu2 = new Objet("bureau", vendeur);
		
		new Enchere(objetVendu2,LocalDateTime.of(2015, 2, 8, 13, 50),
				150, acheteur);
		System.out.println(acheteur.ajouterObjetAchete(objetVendu2)?"objet acheté":"problème");
		
		Objet objetVendu3 = new Objet("table", vendeur2);
		 new Enchere(objetVendu3,LocalDateTime.of(2015, 2, 9, 13, 50),
				100, acheteur);
		System.out.println(acheteur.ajouterObjetAchete(objetVendu3)?"objet acheté":"problème");
		
		System.out.println("Il y a 2 objets achetés par cet acheteur -> "+acheteur.objetsAchetes().size());
		
		SortedSet<Objet> objAch = acheteur.objetsAchetes(vendeur);
		System.out.println("Il y a 1 objet acheté par cet acheteur à ce vendeur -> "+objAch.size());
		for (Objet o : objAch){
			System.out.println(o.getNum() + " " +o.getDescription() + " " + o.prixDeVente());
		}
	}
}
