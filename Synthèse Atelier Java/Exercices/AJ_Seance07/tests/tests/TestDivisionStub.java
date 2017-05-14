package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import domaine.Club;
import domaine.ClubImpl;
import domaine.Division;
import domaine.DivisionImpl;
import domaine.Equipe;
import domaine.EquipeStub;
import exceptions.MinimumMultiplicityException;

public class TestDivisionStub {

  private Club club;
  private Division division;

  @Before
  public void setUp() {
    this.club = new ClubImpl("test");
    this.division = new DivisionImpl("test", 1);
  }

  @Test
  public void testDivisionTC1() {
    assertEquals(0, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(new EquipeStub(0, division, club, true, true, true)));
    assertEquals(1, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC2() {
    amenerALEtat(1, division);
    assertEquals(1, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(new EquipeStub(0, division, club, true, true, true)));
    assertEquals(2, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC3() {
    amenerALEtat(1, division);
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(2, division.nombreDEquipes());
    assertFalse(division.ajouterEquipe(equipe));
    assertEquals(2, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC4() {
    amenerALEtat(11, division);
    assertEquals(11, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(new EquipeStub(0, division, club, true, true, true)));
    assertEquals(12, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC5() {
    amenerALEtat(12, division);
    assertEquals(12, division.nombreDEquipes());
    assertFalse(division.ajouterEquipe(new EquipeStub(0, division, club, true, true, true)));
    assertEquals(12, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC6() {
    assertEquals(0, division.nombreDEquipes());
    assertFalse(division
        .ajouterEquipe(new EquipeStub(0, new DivisionImpl("other", 1), club, true, true, true)));
    assertEquals(0, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC7() {
    assertEquals(0, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(new EquipeStub(0, division, club, true, true, true)));
    assertEquals(1, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC8() {
    amenerALEtat(2, division);
    assertEquals(2, division.nombreDEquipes());
    assertFalse(division.supprimerEquipe(
        new EquipeStub(division.nombreDEquipes() + 1, division, club, true, true, true)));
    assertEquals(2, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC9() {
    amenerALEtat(1, division);
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(2, division.nombreDEquipes());
    assertTrue(division.supprimerEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC10() {
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
    assertTrue(division.supprimerEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC11() {
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    assertEquals(0, division.nombreDEquipes());
    assertFalse(division.supprimerEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC12() {
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    assertEquals(0, division.nombreDEquipes());
    assertFalse(division.contientEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC13() {
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    amenerALEtat(1, division);
    assertEquals(1, division.nombreDEquipes());
    assertFalse(division.contientEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC14() {
    Equipe equipe = new EquipeStub(0, division, club, true, true, true);
    assertTrue(division.ajouterEquipe(equipe));
    amenerALEtat(2, division);
    assertEquals(2, division.nombreDEquipes());
    assertTrue(division.contientEquipe(equipe));
    assertEquals(2, division.nombreDEquipes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDivisionTC15() {
    assertEquals(0, division.nombreDEquipes());
    assertFalse(division.ajouterEquipe(null));
    assertEquals(0, division.nombreDEquipes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDivisionTC16() {
    amenerALEtat(1, division);
    assertEquals(1, division.nombreDEquipes());
    assertFalse(division.supprimerEquipe(null));
    assertEquals(1, division.nombreDEquipes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDivisionTC17() {
    amenerALEtat(1, division);
    assertEquals(1, division.nombreDEquipes());
    assertFalse(division.contientEquipe(null));
    assertEquals(1, division.nombreDEquipes());
  }

  @Test(expected = MinimumMultiplicityException.class)
  public void testDivisionTC18() throws MinimumMultiplicityException {
    amenerALEtat(0, division);
    assertEquals(0, division.nombreDEquipes());
    division.equipes();
    assertEquals(0, division.nombreDEquipes());
  }

  @Test(expected = MinimumMultiplicityException.class)
  public void testDivisionTC19() throws MinimumMultiplicityException {
    amenerALEtat(1, division);
    assertEquals(1, division.nombreDEquipes());
    division.equipes();
    assertEquals(1, division.nombreDEquipes());
  }

  @Test(expected = MinimumMultiplicityException.class)
  public void testDivisionTC20() throws MinimumMultiplicityException {
    amenerALEtat(11, division);
    assertEquals(11, division.nombreDEquipes());
    division.equipes();
    assertEquals(11, division.nombreDEquipes());
  }

  @Test
  public void testDivisionTC21() throws MinimumMultiplicityException {
    amenerALEtat(12, division);
    assertEquals(12, division.nombreDEquipes());
    division.equipes();
    assertEquals(12, division.nombreDEquipes());
  }

  private void amenerALEtat(int etat, Division division) {
    int etatActuel = division.nombreDEquipes();
    if (etatActuel > etat) {
      Iterator<Equipe> iterator = null;
      try {
        iterator = division.equipes();
      } catch (MinimumMultiplicityException e) {
        throw new RuntimeException();
      }
      while (etatActuel != etat) {
        while (iterator.hasNext()) {
          Equipe toSup = iterator.next();
          division.supprimerEquipe(toSup);
          etatActuel = division.nombreDEquipes();
        }
      }
    } else {
      while (etatActuel != etat) {
        Equipe toAdd = new EquipeStub(etatActuel++, division, club, true, true, true);
        division.ajouterEquipe(toAdd);
      }
    }
  }

}
