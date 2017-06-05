package uc;

import java.time.LocalDateTime;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;

public class TestDomaine {

  public static void main(String[] args) {
    Utilisateur usr = new Utilisateur("Leconte", "Emmeline", "emmeline.leconte@vinci.be");
    System.out.println("Utilisateur__________________________________");
    System.out.println("1." + usr.clone().equals(usr) + " (TRUE attendu)");
    System.out.println("2." + (usr.clone() == usr) + " (FALSE attendu)");

    System.out.println(
        "3." + usr.objetsAchetes().equals(usr.clone().objetsAchetes()) + " (TRUE attendu)");
    System.out.println(
        "4." + ((usr.objetsAchetes()) == usr.clone().objetsAchetes()) + " (FALSE attendu)");
    System.out.println("\nObjet________________________________________");
    Objet obj = new Objet("test", usr);
    System.out.println("5." + obj.clone().equals(obj) + " (TRUE attendu)");
    System.out.println("6." + (obj.clone() == obj) + " (FALSE attendu)");

    System.out.println("7." + obj.clone().getVendeur().equals(usr) + " (TRUE attendu)");
    System.out.println("8." + (obj.clone().getVendeur() == usr) + " (FALSE attendu)");

    System.out.println("9." + obj.encheres().equals(obj.clone().encheres()) + " (TRUE attendu)");
    System.out.println("10." + ((obj.encheres()) == obj.clone().encheres()) + " (FALSE attendu)");
    System.out.println("\nEnchere______________________________________");
    Utilisateur usr2 = new Utilisateur("Leconte", "Emmeline", "emmeline.leconte@vinci.be");
    Enchere ench = new Enchere(obj, LocalDateTime.now(), 100, usr2);

    System.out.println("11." + (ench instanceof Cloneable) + " (FALSE attendu)");
    System.out
        .println("12." + ench.getEncherisseur().equals(ench.getEncherisseur()) + " (TRUE attendu)");
    System.out
        .println("13." + (ench.getEncherisseur() == ench.getEncherisseur()) + " (FALSE attendu)");

    System.out.println("14." + ench.getObjet().equals(ench.getObjet()) + " (TRUE attendu)");
    System.out.println("15." + (ench.getObjet() == ench.getObjet()) + " (FALSE attendu)");
  }

}
