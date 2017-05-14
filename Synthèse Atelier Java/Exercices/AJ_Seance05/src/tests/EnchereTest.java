package tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;

public class EnchereTest {
  
  private Enchere enchere1;
  private Utilisateur util2;
  private Objet objet1;
  private LocalDateTime time;
  private Utilisateur util1;

  @Before
  public void setUp() throws Exception {
    time = LocalDateTime.now();
    util1 = new Utilisateur("Maniet", "Antoine", "antoine.maniet@student.vinci.be");
    util2 = new Utilisateur("Maniet", "Alexandre", "alexandre.maniet@student.vinci.be");
    objet1 = new Objet("yoyo", util1);
    enchere1 = new Enchere(objet1, time.minusDays(1), 5.0, util2);
  }

  @Test
  public void testGetEncherisseur1() {
    assertEquals(util2, enchere1.getEncherisseur());
  }
  
  @Test
  public void testGetEncherisseur2() {
    assertEquals(util2, enchere1.getEncherisseur());
    assertNotSame(util2, enchere1.getEncherisseur());
  }
  
  @Test
  public void testEquals1() {
    Objet objet1 = new Objet("de", util1);
    Objet objet2 = new Objet("da", util1);
    Utilisateur utilisateur = new Utilisateur("Jinai", "Jinai", "Jinai");
    Enchere enchere1 = new Enchere(objet1, time, 6.0, utilisateur);
    Enchere enchere2 = new Enchere(objet2, time, 5.0, utilisateur);
    assertEquals(enchere1, enchere2);
  }
  
  @Test
  public void testEquals2() {
    Objet objet1 = new Objet("de", util1);
    Objet objet2 = new Objet("da", util1);
    Utilisateur utilisateur = new Utilisateur("Jinai", "Jinai", "Jinai");
    Enchere enchere1 = new Enchere(objet1, time, 6.0, utilisateur);
    Enchere enchere2 = new Enchere(objet2, time.minusDays(1), 5.0, utilisateur);
    assertNotEquals(enchere1, enchere2);
  }
  
  @Test
  public void testEquals3() {
    Objet objet1 = new Objet("de", util1);
    Objet objet2 = new Objet("da", util1);
    Utilisateur utilisateur1 = new Utilisateur("Jinai", "Jinai", "Jinai");
    Utilisateur utilisateur2 = new Utilisateur("Jinai", "Logiciel", "ERROR_418");
    Enchere enchere1 = new Enchere(objet1, time, 6.0, utilisateur1);
    Enchere enchere2 = new Enchere(objet2, time, 5.0, utilisateur2);
    assertNotEquals(enchere1, enchere2);
  }
  
  @Test
  public void testEquals4() {
    Objet objet1 = new Objet("de", util1);
    Objet objet2 = new Objet("da", util1);
    Utilisateur utilisateur1 = new Utilisateur("Jinai", "Jinai", "Jinai");
    Enchere enchere1 = new Enchere(objet1, time, 6.0, utilisateur1);
    Enchere enchere2 = new Enchere(objet2, time, 5.0, utilisateur1);
    assertEquals(enchere1, enchere2);
    assertEquals(enchere1.hashCode(), enchere2.hashCode());
  }
}
