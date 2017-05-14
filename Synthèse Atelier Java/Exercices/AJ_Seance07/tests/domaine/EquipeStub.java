package domaine;

import java.util.Iterator;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;

public class EquipeStub implements Equipe {

  int numero;
  Division division;
  Club club;
  boolean supprimerDivision;
  boolean maximumDivisionAtteint;
  boolean enregistrerDivision;

  public EquipeStub(int numero, Division division, Club club, boolean supprimerDivision,
      boolean maximumDivisionAtteint, boolean enregistrerDivision) {
    super();
    this.numero = numero;
    this.division = division;
    this.club = club;
    this.supprimerDivision = supprimerDivision;
    this.maximumDivisionAtteint = maximumDivisionAtteint;
    this.enregistrerDivision = enregistrerDivision;
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
    return this.enregistrerDivision;
  }

  @Override
  public boolean supprimerDivision() {
    return this.supprimerDivision;
  }

  @Override
  public Division getDivision() throws MinimumMultiplicityException {
    return division;
  }

  @Override
  public boolean maximumDivisionAtteint() {
    return this.maximumDivisionAtteint;
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
