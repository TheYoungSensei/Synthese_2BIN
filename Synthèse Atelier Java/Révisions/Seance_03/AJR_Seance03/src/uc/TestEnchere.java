package uc;

import java.time.LocalDateTime;
import java.util.List;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;
import exceptions.EnchereInexistanteException;
import exceptions.ObjetInexistantException;
import exceptions.UtilisateurInexistantException;

public class TestEnchere {
	public static void main(String[] args)
			throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {

		GestionEncheres instance = GestionEncheres.getInstance();
		Utilisateur user1 = instance.inscrire("Leconte", "Emmeline", "emmeline.leconte@ipl.be");
		instance.inscrire("Dupont", "Annick", "annick.dupont@ipl.be");
		Utilisateur user2 = instance.inscrire("Damas", "Christophe", "Christophe.Damas@ipl.be");
		Utilisateur user3 = instance.inscrire("Frank", "Bernard", "Bernard.Frank@ipl.be");

		Objet ob1 = instance.mettreEnVente("bibliothèque", user1);
		Enchere ench1 = instance.encherir(ob1, user2, 50, LocalDateTime.of(2015, 2, 7, 13, 48));
		
		System.out.println(ench1.getObjet().ajouterEnchere(new Enchere(ench1.getObjet(), LocalDateTime.of(2015, 2, 8, 13, 48), 150, user3))+" (TRUE attendu)");
		System.out.println(ench1.getObjet().encheres().size()+" devrait être 1");
		
		
		instance.encherir(ob1, user3, 200, LocalDateTime.of(2015, 2, 9, 13, 48));
		Enchere en = instance.encherir(ob1, user2, 200, LocalDateTime.of(2015, 2, 9, 13, 50));
		if (en != null)
			System.out.println("KO car en doit etre NULL car en  même montant");	

		en = instance.encherir(ob1, user2, 250, LocalDateTime.of(2015, 2, 9, 13, 48));
		if (en != null)
			System.out.println("KO car en doit etre NULL car en même temps");	

		List<Enchere> encheres = instance.listerEncheresDUnObjet(ob1);
		for (Enchere e : encheres) {
			System.out.print("Encherisseur : " + e.getEncherisseur().getPrenom() + " " + e.getEncherisseur().getNom());
			System.out.println(" Montant de l'enchère : " + e.getMontant());
		}
		Objet ob2 = instance.mettreEnVente("bureau", user1);
		instance.encherir(ob2, user2, 150, LocalDateTime.of(2015, 2, 8, 13, 50));
		System.out.println(instance.accepter(ob2) + " = TRUE car objet vendu");

		Objet ob3 = instance.mettreEnVente("table", user1);
		instance.encherir(ob3, user2, 100, LocalDateTime.of(2015, 2, 9, 13, 50));
		System.out.println(instance.accepter(ob3) + " = TRUE car objet vendu");

		System.out.println(instance.fournirObjetsAchetes(user2).size() + " devrait etre 2");

	}
}
