package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;
import exceptions.EnchereInexistanteException;
import exceptions.ObjetInexistantException;
import exceptions.UtilisateurInexistantException;
import uc.GestionEncheres;

public class GestionEncheresTest {

  private GestionEncheres instance = GestionEncheres.getInstance();
  private Utilisateur u1, u2, u3;
  private Objet o1, o2;
  private Enchere e1;

  @Before
  public void setUp() throws Exception {
    u1 = instance.inscrire("Leconte", "Emmeline", "emmeline.leconte@vinci.be");
    u2 = instance.inscrire("Leleux", "Laurent", "laurent.leleux@vinci.be");
    u3 = instance.inscrire("Legrand", "Anthony", "anthony.legrand@vinci.be");
    o1 = instance.mettreEnVente("Bibliothèque", u1);
    o2 = instance.mettreEnVente("Vélo Mixte", u1);
    e1 = instance.encherir(o1, u2, 150, LocalDateTime.of(2017, 3, 15, 12, 20));
  }

  @Test(expected = UtilisateurInexistantException.class)
  public void testMettreEnVente1() throws UtilisateurInexistantException {
    instance.mettreEnVente("Bibliothèque", new Utilisateur("a", "a", "a"));
  }

  @Test
  public void testMettreEnVente2() throws UtilisateurInexistantException, ObjetInexistantException {
    Enchere e2 = new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 25), 200, u3);
    assertFalse(instance.listerEncheresDUnObjet(o2).contains(e2));
  }

  @Test
  public void testMettreEnVente3() throws UtilisateurInexistantException, ObjetInexistantException {
    assertTrue(instance.listerObjetsEnVente().contains(o1));
  }

  @Test(expected = UtilisateurInexistantException.class)
  public void testEncherir1() throws UtilisateurInexistantException, ObjetInexistantException {
    instance.encherir(o2, new Utilisateur("a", "a", "a"), 100,
        LocalDateTime.of(2017, 3, 15, 12, 25));
  }

  @Test(expected = ObjetInexistantException.class)
  public void testEncherir2() throws UtilisateurInexistantException, ObjetInexistantException {
    instance.encherir(new Objet("Vélo", u1), u3, 100, LocalDateTime.of(2017, 3, 15, 12, 25));
  }

  @Test
  public void testEncherir3() throws UtilisateurInexistantException, ObjetInexistantException {
    assertNull(instance.encherir(o2, u2, 150, LocalDateTime.of(2017, 3, 15, 12, 20)));
  }

  @Test
  public void testEncherir4() throws UtilisateurInexistantException, ObjetInexistantException {
    assertNull(instance.encherir(o2, u2, 150, LocalDateTime.of(2017, 3, 15, 12, 20)));
    assertTrue(instance.listerEncheresDUnObjet(o2).isEmpty());
  }

  @Test
  public void testEncherir5a() throws UtilisateurInexistantException, ObjetInexistantException {
    assertNull(instance.encherir(o1, u3, 200, LocalDateTime.of(2017, 3, 15, 12, 20)));
  }

  @Test
  public void testEncherir5b() throws UtilisateurInexistantException, ObjetInexistantException {
    assertNull(instance.encherir(o1, u3, 200, LocalDateTime.of(2017, 3, 15, 12, 15)));
  }

  @Test
  public void testEncherir5c() throws UtilisateurInexistantException, ObjetInexistantException {
    assertNull(instance.encherir(o1, u3, 150, LocalDateTime.of(2017, 3, 15, 12, 25)));
  }

  @Test
  public void testEncherir5d() throws UtilisateurInexistantException, ObjetInexistantException {
    assertNull(instance.encherir(o1, u3, 145, LocalDateTime.of(2017, 3, 15, 12, 25)));
  }

  @Test
  public void testEncherir6() throws ObjetInexistantException {
    assertTrue(instance.listerEncheresDUnObjet(o1).contains(e1));
  }

  @Test
  public void testEncherir7() {
    assertTrue(instance.fournirEnchere(LocalDate.of(2017, 3, 15)).contains(e1));
  }

  @Test(expected = ObjetInexistantException.class)
  public void testEncherir8()
      throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    instance.accepter(o1);
    instance.encherir(o1, u3, 250, LocalDateTime.of(2017, 3, 25, 12, 25));
  }

  @Test(expected = EnchereInexistanteException.class)
  public void testAccepter1() throws ObjetInexistantException, EnchereInexistanteException {
    instance.accepter(o2);
  }

  @Test
  public void testAccepter2() throws ObjetInexistantException, EnchereInexistanteException {
    assertTrue(instance.accepter(o1));
  }

  @Test
  public void testAccepter3()
      throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    instance.accepter(o1);
    SortedSet<Objet> oAch = instance.fournirObjetsAchetes(u2);
    assertTrue(new HashSet<Objet>(instance.fournirObjetsAchetes(u2)).contains(o1));
  }

  @Test
  public void testAccepter4() throws ObjetInexistantException, EnchereInexistanteException {
    instance.accepter(o1);
    assertFalse(instance.listerObjetsEnVente().contains(o1));
  }

  @Test
  public void testAccepter15() throws ObjetInexistantException, EnchereInexistanteException {
    instance.accepter(o1);
    assertTrue(instance.fournirObjetsVendus().contains(o1));
  }



}
