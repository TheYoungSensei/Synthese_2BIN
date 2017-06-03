package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domaine.Club;
import domaine.ClubStub;
import domaine.Division;
import domaine.DivisionImpl;
import domaine.EquipeMock;

public class TestDivisionMock {

	private Division division;
	private Club club;
	
	@Before
	public void setUp() throws Exception {
		this.division = new DivisionImpl("Division 1", 15);
		this.club = new ClubStub();
	}
	
	private void amenerALEtat(int etat, Division division) {
		for (int i = 1; i <= etat; i++) {
			this.division.ajouterEquipe(new EquipeMock(i, this.division, this.club, false, true, this.division, false, false, false));
		}
	}
	
	@Test
	public void TestDivisionTC1(){
		EquipeMock equipe = new EquipeMock(1, null, this.club, false, true, this.division, false, false, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 1);
		equipe.verify();
	}
	
	@Test
	public void TestDivisionTC2(){
		amenerALEtat(1, this.division);
		EquipeMock equipe = new EquipeMock(2, null, this.club, false, true, this.division, false, false, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 2);
		equipe.verify();
	}
	
	@Test
	public void TestDivisionTC3(){
		amenerALEtat(12, this.division);
		EquipeMock equipe = new EquipeMock(13, null, this.club, false, false, this.division, false, false, false);
		assertFalse(this.division.ajouterEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 12);
		equipe.verify();
	}
	
	@Test
	public void TestDivisionTC4(){
		Division autreDivision = new DivisionImpl("Division 2", 15);
		EquipeMock equipe = new EquipeMock(1, autreDivision, this.club, true, false, this.division, false, false, false);
		assertFalse(this.division.ajouterEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 0);
		equipe.verify();
	}
	
	@Test
	public void TestDivisionTC5(){
		EquipeMock equipe = new EquipeMock(1, this.division, this.club, false, true, this.division, false, false, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 1);
		equipe.verify();
	}

}
