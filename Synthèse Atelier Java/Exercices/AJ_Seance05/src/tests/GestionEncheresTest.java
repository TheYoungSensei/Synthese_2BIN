package tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

  private GestionEncheres gestionEncheres;
  private Utilisateur utilisateur1;
  private Utilisateur utilisateur2;
  private LocalDateTime time;
  private Utilisateur utilisateur3;
  private Utilisateur utilisateur4;
  
  @Before
  public void setUp() throws Exception {
    gestionEncheres = GestionEncheres.getInstance();
    utilisateur1 = new Utilisateur("Jinai", "Logiciel", "ERROR_418");
    utilisateur2 = gestionEncheres.inscrire("Jinai", "Logiciel", "EROR_418");
    utilisateur3 = gestionEncheres.inscrire("Jimmy", "Pas la", "Mais tu es ou");
    utilisateur4 = gestionEncheres.inscrire("Plus", "inspi", "pls");
    time = LocalDateTime.now();
  }
 
  @Test (expected = UtilisateurInexistantException.class)
  public void testMettreEnVente1() throws UtilisateurInexistantException {
    gestionEncheres.mettreEnVente("yoyo", utilisateur1);
  }
  
  @Test
  public void testMettreEnVente2() throws UtilisateurInexistantException {
    Objet objet2 = gestionEncheres.mettreEnVente("yoyo", utilisateur2);
    assertEquals(objet2, gestionEncheres.listerObjetsEnVente().get(gestionEncheres.listerObjetsEnVente().indexOf(objet2)));
    assertNotSame(objet2, gestionEncheres.listerObjetsEnVente().get(gestionEncheres.listerObjetsEnVente().indexOf(objet2)));
  }
  
  @Test
  public void testMettreEnVente3() throws UtilisateurInexistantException {
    Objet objet3 = gestionEncheres.mettreEnVente("yoyo", utilisateur2);
    assertEquals(true, gestionEncheres.listerObjetsEnVente().contains(objet3));
  }
  
  @Test (expected = UtilisateurInexistantException.class)
  public void testEncherir1() throws ObjetInexistantException, UtilisateurInexistantException {
    Objet objet = gestionEncheres.mettreEnVente("yoyo", utilisateur2);
    gestionEncheres.encherir(objet, utilisateur1, 5.0, time);
  }
  
  @Test (expected = ObjetInexistantException.class)
  public void testEncherir2() throws ObjetInexistantException, UtilisateurInexistantException {
    Objet objet = new Objet("grosbo", utilisateur2);
    gestionEncheres.encherir(objet, utilisateur2, 5.0, time);
  }
  
  @Test
  public void testEncherir3() throws UtilisateurInexistantException, ObjetInexistantException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    Objet objet2 = gestionEncheres.mettreEnVente("apple", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    assertNull(gestionEncheres.encherir(objet2, utilisateur3, 5.0, time));
  }
  
  @Test
  public void testEncherir4() throws UtilisateurInexistantException, ObjetInexistantException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    Objet objet2 = gestionEncheres.mettreEnVente("apple", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    assertNull(gestionEncheres.encherir(objet2, utilisateur3, 6.0, time));
  }
  
  @Test
  public void testEncherir5() throws UtilisateurInexistantException, ObjetInexistantException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    assertNull(gestionEncheres.encherir(objet1, utilisateur4, 5.0, time.plusDays(1)));
  }
  
  @Test
  public void testEncherir6() throws UtilisateurInexistantException, ObjetInexistantException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    Enchere enchere = gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    assertTrue(gestionEncheres.listerEncheresDUnObjet(objet1).contains(enchere));
  }
  
  @Test
  public void testEncherir7() throws UtilisateurInexistantException, ObjetInexistantException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    Enchere enchere = gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    assertTrue(gestionEncheres.fournirEnchere(time.toLocalDate()).contains(enchere));
  }
  
  @Test (expected = ObjetInexistantException.class)
  public void testEncherir8() throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    gestionEncheres.accepter(objet1);
    gestionEncheres.encherir(objet1, utilisateur4, 6.0, time);
  }
  
  @Test (expected = EnchereInexistanteException.class)
  public void accepter1() throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.accepter(objet1);
  }
  
  @Test
  public void accepter2() throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    assertTrue(gestionEncheres.accepter(objet1));
  }
  
  @Test
  public void accepter3() throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur4, 6.0, time.plusDays(6));
    assertTrue(gestionEncheres.accepter(objet1));
    assertTrue(gestionEncheres.fournirObjetsAchetes(utilisateur4).stream().collect(Collectors.toSet()).contains(objet1));
  }
  
  @Test
  public void accepter4() throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    gestionEncheres.accepter(objet1);
    assertFalse(gestionEncheres.listerObjetsEnVente().contains(objet1));
  }
  
  @Test
  public void accepter5() throws UtilisateurInexistantException, ObjetInexistantException, EnchereInexistanteException {
    Objet objet1 = gestionEncheres.mettreEnVente("banane", utilisateur2);
    gestionEncheres.encherir(objet1, utilisateur3, 5.0, time);
    gestionEncheres.accepter(objet1);
    assertTrue(gestionEncheres.fournirObjetsVendus().contains(objet1));
  }
  
  
  
  
  
  
}
