package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import domaine.Club;
import domaine.ClubStub;
import domaine.Division;
import domaine.DivisionImpl;
import domaine.Equipe;
import domaine.EquipeStub;
import exceptions.MinimumMultiplicityException;

public class TestDivisionStub {

	private Division division;
	private Club club;
	
	@Before
	public void setUp() throws Exception {
		this.division = new DivisionImpl("Division 1", 15);
		this.club = new ClubStub();
	}
	
	private void amenerALEtat(int etat, Division division) {
		for (int i = 1; i <= etat; i++) {
			this.division.ajouterEquipe(new EquipeStub(i, this.club, this.division, false, false, false));
		}
	}
	
	@Test
	public void TestDivisionTC1(){
		Equipe equipe = new EquipeStub(1, this.club, null, false, false, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 1);
	}
	
	@Test
	public void TestDivisionTC2(){
		amenerALEtat(1, this.division);
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 2);
	}
	
	@Test
	public void TestDivisionTC3(){
		amenerALEtat(1, this.division);
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		this.division.ajouterEquipe(equipe);
		assertFalse(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 2);
	}
	
	@Test
	public void TestDivisionTC4(){
		amenerALEtat(11, this.division);
		Equipe equipe = new EquipeStub(12, this.club, null, false, false, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 12);
	}
	
	@Test
	public void TestDivisionTC5(){
		amenerALEtat(12, this.division);
		Equipe equipe = new EquipeStub(13, this.club, null, false, false, false);
		assertFalse(this.division.ajouterEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 12);
	}
	
	@Test
	public void TestDivisionTC6(){
		Division autreDivision = new DivisionImpl("Division 2", 15);
		Equipe equipe = new EquipeStub(1, this.club, autreDivision, false, true, false);
		assertFalse(this.division.ajouterEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 0);
	}
	
	@Test
	public void TestDivisionTC7(){
		Equipe equipe = new EquipeStub(1, this.club, this.division, false, true, false);
		assertTrue(this.division.ajouterEquipe(equipe));
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 1);
	}
	
	@Test
	public void TestDivisionTC8(){
		amenerALEtat(2, this.division);
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		assertFalse(this.division.supprimerEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 2);
	}
	
	@Test
	public void TestDivisionTC9(){
		amenerALEtat(1, this.division);
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		this.division.ajouterEquipe(equipe);
		assertTrue(this.division.supprimerEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 1);
	}
	
	@Test
	public void TestDivisionTC10(){
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		this.division.ajouterEquipe(equipe);
		assertTrue(this.division.supprimerEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 0);
	}
	
	@Test
	public void TestDivisionTC11(){
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		assertFalse(this.division.supprimerEquipe(equipe));
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 0);
	}
	
	@Test
	public void TestDivisionTC12(){
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 0);
	}
	
	@Test
	public void TestDivisionTC13(){
		amenerALEtat(1, this.division);
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		assertFalse(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 1);
	}
	
	@Test
	public void TestDivisionTC14(){
		amenerALEtat(1, this.division);
		Equipe equipe = new EquipeStub(2, this.club, null, false, false, false);
		this.division.ajouterEquipe(equipe);
		assertTrue(this.division.contientEquipe(equipe));
		assertEquals(this.division.nombreDEquipes(), 2);
	}
	
	@Test
	public void TestDivisionTC15(){
		try {
			this.division.ajouterEquipe(null);
			fail("ajout d'une équipe --> Il fallait lancer une IllegalArgumentException");
		} catch(IllegalArgumentException ignore){ }
		assertEquals(this.division.nombreDEquipes(), 0);
	}
	
	@Test
	public void TestDivisionTC16(){
		amenerALEtat(1, this.division);
		try {
			this.division.supprimerEquipe(null);
			fail("suppression d'une équipe --> Il fallait lancer une IllegalArgumentException");
		} catch(IllegalArgumentException ignore){ }
		assertEquals(this.division.nombreDEquipes(), 1);
	}
	
	@Test
	public void TestDivisionTC17(){
		amenerALEtat(1, this.division);
		try {
			this.division.contientEquipe(null);
			fail("contient d'une équipe --> Il fallait lancer une IllegalArgumentException");
		} catch(IllegalArgumentException ignore) { }
		assertEquals(this.division.nombreDEquipes(), 1);
	}
	
	@Test(expected = MinimumMultiplicityException.class)
	public void TestDivisionTC18() throws MinimumMultiplicityException {
		this.division.equipes();
	}
	
	@Test(expected = MinimumMultiplicityException.class)
	public void TestDivisionTC19() throws MinimumMultiplicityException {
		amenerALEtat(1, this.division);
		this.division.equipes();
	}
	
	@Test(expected = MinimumMultiplicityException.class)
	public void TestDivisionTC20() throws MinimumMultiplicityException {
		amenerALEtat(11, this.division);
		this.division.equipes();
	}
	
	@Test
	public void TestDivisionTC21() throws MinimumMultiplicityException {
		Set<Equipe> equipes = new HashSet<Equipe>();
		for (int i = 1; i <= 12; i++) {
			Equipe e = new EquipeStub(i, this.club, this.division, false, false, false);
			this.division.ajouterEquipe(e);
			equipes.add(e);
		}
		Iterator<Equipe> it = this.division.equipes();
		Set<Equipe> set = new HashSet<Equipe>();
		while (it.hasNext()){
			Equipe e = it.next();
			set.add(e);
			assertTrue(equipes.contains(e));
		}
		assertEquals(12, set.size());
	}

}
