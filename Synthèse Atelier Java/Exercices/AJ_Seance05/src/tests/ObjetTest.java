package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domaine.Enchere;
import domaine.Objet;
import domaine.Utilisateur;

public class ObjetTest {
  
  private Utilisateur util1;
  private Utilisateur util2;
  private Utilisateur util3;
  private Objet objet1;
  private Objet objet2;
  private Objet objet3;
  private Enchere enchere1;
  private Enchere enchere3;
  private Enchere enchere4;
  
  @Before
  public void setUp() throws Exception {
    util1 = new Utilisateur("Maniet", "Antoine", "antoine.maniet@student.vinci.be");
    util2 = new Utilisateur("Meur", "Damien", "damien.meur@student.vinci.be");
    util3 = new Utilisateur("Maniet", "Alexandre", "alexandre.maniet@student.vinci.be");
    objet1 = new Objet("yoyo", util1);
    objet2 = new Objet("baguette", util1);
    enchere1 = new Enchere(objet2, LocalDateTime.now().minusDays(2), 4.0, util1);
    new Enchere(objet2, LocalDateTime.now().minusDays(1), 5.0, util2);
    enchere3 = new Enchere(objet2, LocalDateTime.now(), 6.0, util3); 
    objet3 = new Objet("arbuste", util1);
    enchere4 = new Enchere(objet3, LocalDateTime.now().minusDays(1), 5.0, util2);
    util2.ajouterObjetAchete(objet3);
  }
  
  @Test (expected = NullPointerException.class)
  public void testObjet1() {
    new Objet(null, util1);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testObjet2() {
    new Objet("", util1);
  }
  
  @Test (expected = NullPointerException.class)
  public void testObjet3() {
    new Objet("fail", null);
  }
  
  @Test
  public void testObjet4() {
    if(new Objet("yolo1", util1).getNum() + 1 != new Objet("yolo2", util2).getNum())
      fail("Les numéros ne sont pas incrémentaux");
  }
  
  @Test
  public void testGetDescription() {
    assertEquals("yoyo", objet1.getDescription());
  }
  
  @Test
  public void testGetVendeur1() {
    assertEquals(util1, objet1.getVendeur());
  }
  
  @Test
  public void testGetVendeur2() {
    assertNotSame(objet1.getVendeur(), util1);
  }
  
  @Test
  public void testEnchere1() {
    if(!objet2.encheres().contains(enchere1))
      fail("L'Enchere n'est pas ajoutée automatiquement");
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testEnchere2(){
    new Enchere(objet2, LocalDateTime.now().minusDays(1), 4.0, util3);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testEnchere3(){
    new Enchere(objet2, LocalDateTime.now(), 4.0, util3);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testEnchere4(){
    new Enchere(objet2, LocalDateTime.now(), 2.0, util3);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testEnchere5(){
    new Enchere(objet2, LocalDateTime.now(), 3.0, util3);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testEnchere6(){
    new Enchere(objet3, LocalDateTime.now().plusDays(3), 6.0, util3);
  }
  
  @Test
  public void testEnchere7() {
    assertNull(objet1.meilleureEnchere());
  }
  
  @Test
  public void testEnchere8() {
    assertEquals(enchere3, objet2.meilleureEnchere());
  }
  
  @Test
  public void testEnchere9() {
    assertEquals(0, objet1.prixDeVente(), 0);
  }
  
  @Test
  public void testEnchere10() {
    assertEquals(0, objet2.prixDeVente(), 0);
  }
  
  @Test
  public void testEnchere11(){
    assertEquals(5.0, objet3.prixDeVente(), 0);
  }
  
  @Test
  public void testEnchere12() {
    List<Enchere> encheres = new ArrayList<Enchere>();
    encheres.add(enchere4);
    assertEquals(encheres, objet3.encheres());
  }
  
  @Test (expected = UnsupportedOperationException.class)
  public void testEnchere13() {
    objet3.encheres().remove(enchere4);
    assertEquals(true, objet3.encheres().contains(enchere4));
  }
  
  @Test
  public void testClone1() {
    Objet clone = objet1.clone();
    assertEquals(objet1, clone);
    assertNotSame(objet1, clone);
  }
  
  @Test
  public void testClone2() {
    Objet clone = objet1.clone();
    assertEquals(objet1.getDescription(), clone.getDescription());
    assertEquals(objet1.getVendeur(), clone.getVendeur());
    assertEquals(objet1.encheres(), clone.encheres());
  }
  
  @Test
  public void testClone3() {
    Objet clone = objet1.clone();
    Enchere enchere = new Enchere(clone, LocalDateTime.now(), 3.0, util2);
    assertEquals(true, clone.encheres().contains(enchere));
    assertEquals(false, objet1.encheres().contains(enchere));
  }
  
  @Test
  public void testHashCode() {
    Objet equivalent = objet1.clone();
    assertEquals(equivalent, objet1);
    assertEquals(objet1.hashCode(), equivalent.hashCode(), 0);
  }
}
