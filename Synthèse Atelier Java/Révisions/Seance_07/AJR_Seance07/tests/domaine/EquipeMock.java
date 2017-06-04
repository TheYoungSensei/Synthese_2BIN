package domaine;

import java.util.Iterator;

import org.junit.Assert;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;

public class EquipeMock implements Equipe {

  private int numero;
  private Division division;
  private Club club;

  private boolean maximumDivisionAtteint;

  private boolean enregistrerDivisionCallExpected;
  private Division enregistrerDivisionExpectedParam;
  private boolean enregistrerDivisionCall;
  private boolean enregistrerDivisionReturn;

  private boolean supprimerDivisionCallExpected;
  private boolean supprimerDivisionCall;
  private boolean supprimerDivisionReturn;

  public EquipeMock(int numero, Division division, Club club, boolean maximumDivisionAtteint,
      boolean enregistrerDivisionCallExpected, Division enregistrerDivisionExpectedParam,
      boolean enregistrerDivisionReturn, boolean supprimerDivisionCallExpected,
      boolean supprimerDivisionReturn) {
    super();
    this.numero = numero;
    this.division = division;
    this.club = club;
    this.maximumDivisionAtteint = maximumDivisionAtteint;
    this.enregistrerDivisionCallExpected = enregistrerDivisionCallExpected;
    this.enregistrerDivisionExpectedParam = enregistrerDivisionExpectedParam;
    this.enregistrerDivisionReturn = enregistrerDivisionReturn;
    this.supprimerDivisionCallExpected = supprimerDivisionCallExpected;
    this.supprimerDivisionReturn = supprimerDivisionReturn;
  }

  @Override
  public int getNumero() {
    return this.numero;
  }

  @Override
  public double moyenneEquipe() {
    return -1;
  }

  @Override
  public Club getClub() {
    return this.club;
  }

  @Override
  public boolean enregistrerDivision(Division division) {
    if (!this.enregistrerDivisionCallExpected) {
      Assert.fail("Appel Inatendu de enregistrerDivision");
    }
    if (this.enregistrerDivisionExpectedParam != division) {
      Assert.fail("Paramètre attendu incorrect dans enregistrerDivision");
    }
    this.enregistrerDivisionCall = true;
    return this.enregistrerDivisionReturn;
  }

  @Override
  public boolean supprimerDivision() {
    if (!this.supprimerDivisionCallExpected) {
      Assert.fail("Appel Inattendu de supprimerDivision");
    }
    this.supprimerDivisionCall = true;
    return this.supprimerDivisionReturn;
  }

  @Override
  public Division getDivision() throws MinimumMultiplicityException {
    return this.division;
  }

  @Override
  public boolean maximumDivisionAtteint() {
    return this.maximumDivisionAtteint;
  }

  @Override
  public boolean minimumDivisionGaranti() {
    return false;
  }

  @Override
  public boolean ajouterJoueur(Joueur joueur) {
    return false;
  }

  @Override
  public boolean supprimerJoueur(Joueur joueur) {
    return false;
  }

  @Override
  public boolean contient(Joueur joueur) {
    return false;
  }

  @Override
  public int nombreDeJoueurs() {
    return 0;
  }

  @Override
  public Iterator<Joueur> joueurs()
      throws RenseignementInsuffisantException, MinimumMultiplicityException {
    return null;
  }

  @Override
  public boolean minimumJoueursGaranti() throws RenseignementInsuffisantException {
    return false;
  }

  public void verify() {
    if (enregistrerDivisionCallExpected && !enregistrerDivisionCall) {
      Assert.fail("enregistrerDivision n'a pas été appellé");
    }
    if (supprimerDivisionCallExpected && !supprimerDivisionCall) {
      Assert.fail("supprimerDivision n'a pas été appellé");
    }
  }

}
