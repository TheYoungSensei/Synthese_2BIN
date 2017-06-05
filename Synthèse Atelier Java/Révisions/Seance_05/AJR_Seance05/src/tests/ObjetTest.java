package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;

public class ObjetTest {

  private Utilisateur u1, u2, u3;
  private Objet o1, o2, o3;
  private Enchere e1, e2, e3, e4;

  @Before
  public void setUp() throws Exception {
    u1 = new Utilisateur("Leconte", "Emmeline", "emmeline.leconte@vinci.be");
    u2 = new Utilisateur("Leleux", "Laurent", "laurent.leleux@vinci.be");
    u3 = new Utilisateur("Legrand", "Anthony", "anthony.legrand@vinci.be");
    o1 = new Objet("Poussette combinée 3 en 1", u1);
    o2 = new Objet("Bibliothèque", u1);
    o3 = new Objet("Vélo de ville mixte", u1);
    e1 = new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 10), 100, u2);
    e2 = new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 15), 120, u3);
    e3 = new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 20), 150, u2);
    e4 = new Enchere(o3, LocalDateTime.of(2017, 3, 16, 12, 10), 200, u2);
    u2.ajouterObjetAchete(o3);
  }

  @Test(expected = NullPointerException.class)
  public void testObjet1() {
    new Objet(null, u1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testObjet2() {
    new Objet("   ", u1);
  }

  @Test(expected = NullPointerException.class)
  public void testObjet3() {
    new Objet("bureau", null);
  }

  @Test
  public void testObjet4() {
    assertEquals(o1.getNum() + 1, o2.getNum());
    assertEquals(o2.getNum() + 1, o3.getNum());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Poussette combinée 3 en 1", o1.getDescription());
  }

  @Test
  public void testGetVendeur1() {
    assertEquals(u1, o1.getVendeur());
  }

  @Test
  public void testGetVendeur2() {
    assertNotSame(o1.getVendeur(), o1.getVendeur());
  }

  @Test
  public void testEnchere1() {
    assertTrue(o2.encheres().contains(e1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnchere2() {
    new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 19), 200, u3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnchere3() {
    new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 20), 200, u3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnchere4() {
    new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 25), 145, u3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnchere5() {
    new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 25), 150, u3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnchere6() {
    new Enchere(o3, LocalDateTime.of(2017, 3, 16, 12, 25), 250, u3);
  }

  @Test
  public void testEnchere7() {
    assertNull(o1.meilleureEnchere());
  }

  @Test
  public void testEnchere8() {
    assertEquals(e3, o2.meilleureEnchere());
  }

  @Test
  public void testEnchere9() {
    assertEquals(0, o1.prixDeVente(), 0);
  }

  @Test
  public void testEnchere10() {
    assertEquals(0, o2.prixDeVente(), 0);
  }

  @Test
  public void testEnchere11() {
    assertEquals(200, o3.prixDeVente(), 0);
  }

  @Test
  public void testEnchere12() {
    List<Enchere> listeAttendue = new ArrayList<Enchere>();
    listeAttendue.add(e1);
    listeAttendue.add(e2);
    listeAttendue.add(e3);
    assertEquals(listeAttendue, o2.encheres());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEnchere13() {
    List<Enchere> liste = o2.encheres();
    liste.remove(e2);
  }

  @Test
  public void testClone1() {
    Objet cloneO2 = o2.clone();
    assertEquals(o2, cloneO2);
    assertNotSame(o2, cloneO2);
  }

  @Test
  public void testClone2() {
    Objet cloneO2 = o2.clone();
    assertEquals(o2.getDescription(), cloneO2.getDescription());
    assertEquals(o2.getVendeur(), cloneO2.getVendeur());
    assertEquals(o2.encheres(), cloneO2.encheres());
  }

  @Test
  public void testClone3() {
    Objet cloneO2 = o2.clone();
    Enchere e5 = new Enchere(cloneO2, LocalDateTime.of(2017, 3, 20, 12, 0), 300, u3);
    assertFalse(o2.encheres().contains(e5));
    assertTrue(cloneO2.encheres().contains(e5));
  }

  @Test
  public void testHashCode() {
    Objet cloneO2 = o2.clone();
    assertEquals(o2.hashCode(), cloneO2.hashCode());
  }

}
