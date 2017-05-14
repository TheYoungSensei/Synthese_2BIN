package domaine;

import java.util.Iterator;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;

public class EquipeMock implements Equipe {

  int numero;
  Division division;
  Club club;

  boolean enregistrerDivision;
  Division enregistrerDivisionExpectedParam;
  boolean enregistrerDivisionCallExpected;
  boolean enregistrerDivisionCall;

  boolean supprimerDivision;
  Division supprimerDivisionExpectedParam;
  boolean supprimerDivisionCallExpected;
  boolean supprimerDivisionCall;


  public EquipeMock(int numero, Division division, Club club, boolean enregistrerDivision,
      Division enregistrerDivisionExpectedParam, boolean enregistrerDivisionCallExpected) {
    super();
    this.numero = numero;
    this.division = division;
    this.club = club;
    this.enregistrerDivision = enregistrerDivision;
    this.enregistrerDivisionExpectedParam = enregistrerDivisionExpectedParam;
    this.enregistrerDivisionCallExpected = enregistrerDivisionCallExpected;
  }



  public EquipeMock(int numero, Division division, Club club, boolean enregistrerDivision,
      Division enregistrerDivisionExpectedParam, boolean enregistrerDivisionCallExpected,
      boolean supprimerDivision, Division supprimerDivisionExpectedParam,
      boolean supprimerDivisionCallExpected) {
    super();
    this.numero = numero;
    this.division = division;
    this.club = club;
    this.enregistrerDivision = enregistrerDivision;
    this.enregistrerDivisionExpectedParam = enregistrerDivisionExpectedParam;
    this.enregistrerDivisionCallExpected = enregistrerDivisionCallExpected;
    this.supprimerDivision = supprimerDivision;
    this.supprimerDivisionExpectedParam = supprimerDivisionExpectedParam;
    this.supprimerDivisionCallExpected = supprimerDivisionCallExpected;
  }



  public boolean verify() {
    return this.enregistrerDivisionCall == enregistrerDivisionCallExpected
        && enregistrerDivisionExpectedParam == division
        && supprimerDivisionCall == supprimerDivisionCallExpected
        && supprimerDivisionExpectedParam == division;
  }

  @Override
  public int getNumero() {
    return this.numero;
  }

  @Override
  public double moyenneEquipe() {
    return 0;
  }

  @Override
  public Club getClub() {
    return this.club;
  }

  @Override
  public boolean enregistrerDivision(Division division) {
    this.division = division;
    this.enregistrerDivisionCall = true;
    return this.enregistrerDivision;
  }

  @Override
  public boolean supprimerDivision() {
    this.division = division;
    this.supprimerDivisionCall = true;
    return this.supprimerDivision;
  }

  @Override
  public Division getDivision() throws MinimumMultiplicityException {
    return division;
  }

  @Override
  public boolean maximumDivisionAtteint() {
    return true;
  }

  @Override
  public boolean minimumDivisionGaranti() {
    return true;
  }

  @Override
  public boolean ajouterJoueur(Joueur joueur) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean supprimerJoueur(Joueur joueur) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean contient(Joueur joueur) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int nombreDeJoueurs() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Iterator<Joueur> joueurs()
      throws RenseignementInsuffisantException, MinimumMultiplicityException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean minimumJoueursGaranti() throws RenseignementInsuffisantException {
    // TODO Auto-generated method stub
    return false;
  }

}
