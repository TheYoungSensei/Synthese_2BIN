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
import domaine.EquipeMock;
import domaine.EquipeStub;
import exceptions.MinimumMultiplicityException;

public class TestDivisionMock {

  private Club club;
  private Division division;

  @Before
  public void setUp() {
    this.club = new ClubImpl("test");
    this.division = new DivisionImpl("test", 1);
  }

  @Test
  public void testDivisionTC1() {
    EquipeMock equipe = new EquipeMock(0, division, club, true, division, true);
    assertEquals(0, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC2() {
    amenerALEtat(1, division);
    EquipeMock equipe = new EquipeMock(0, division, club, true, division, true);
    assertEquals(1, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(2, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC3() {
    amenerALEtat(12, division);
    EquipeMock equipe = new EquipeMock(0, division, club, true, null, false);
    assertEquals(12, division.nombreDEquipes());
    assertFalse(division.ajouterEquipe(equipe));
    assertEquals(12, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC4() {
    amenerALEtat(0, division);
    EquipeMock equipe = new EquipeMock(0, new DivisionImpl("other", 1), club, true, null, false);
    assertEquals(0, division.nombreDEquipes());
    assertFalse(division.ajouterEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC5() {
    EquipeMock equipe = new EquipeMock(0, division, club, true, division, true);
    assertEquals(0, division.nombreDEquipes());
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC6() {
    EquipeMock equipe =
        new EquipeMock(0, division, club, true, division, true, true, division, true);
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
    assertTrue(division.supprimerEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC7() {
    amenerALEtat(1, division);
    EquipeMock equipe =
        new EquipeMock(0, division, club, true, division, true, true, division, true);
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(2, division.nombreDEquipes());
    assertTrue(division.supprimerEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC8() {
    amenerALEtat(0, division);
    EquipeMock equipe = new EquipeMock(0, division, club, true, null, false, true, null, false);
    assertEquals(0, division.nombreDEquipes());
    assertFalse(division.supprimerEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC9() {
    amenerALEtat(12, division);
    EquipeMock equipe =
        new EquipeMock(0, new DivisionImpl("other", 1), club, true, null, false, true, null, false);
    assertEquals(12, division.nombreDEquipes());
    assertFalse(division.supprimerEquipe(equipe));
    assertEquals(12, division.nombreDEquipes());
    assertTrue(equipe.verify());
  }

  @Test
  public void testDivisionTC10() {
    EquipeMock equipe =
        new EquipeMock(0, division, club, true, division, true, true, division, true);
    assertTrue(division.ajouterEquipe(equipe));
    assertEquals(1, division.nombreDEquipes());
    assertTrue(division.supprimerEquipe(equipe));
    assertEquals(0, division.nombreDEquipes());
    assertTrue(equipe.verify());
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
