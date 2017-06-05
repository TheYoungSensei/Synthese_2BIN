package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;

public class EnchereTest {

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
    e1 = new Enchere(o1, LocalDateTime.of(2017, 3, 15, 12, 10), 100, u2);
    e2 = new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 10), 110, u2);
    e3 = new Enchere(o3, LocalDateTime.of(2017, 3, 15, 12, 15), 120, u2);
    e4 = new Enchere(o2, LocalDateTime.of(2017, 3, 15, 12, 15), 150, u3);
  }

  @Test
  public void testGetEncherisseur1() {
    assertEquals(u2, e1.getEncherisseur());
  }

  @Test
  public void testGetEncherisseur2() {
    assertNotSame(e1.getEncherisseur(), e1.getEncherisseur());
  }

  @Test
  public void testEqualsObject1() {
    assertEquals(e1, e2);
  }

  @Test
  public void testEqualsObject2() {
    assertNotEquals(e2, e3);
  }

  @Test
  public void testEqualsObject3() {
    assertNotEquals(e3, e4);
  }

  @Test
  public void testHashCode() {
    assertEquals(e1.hashCode(), e2.hashCode());
  }



}
