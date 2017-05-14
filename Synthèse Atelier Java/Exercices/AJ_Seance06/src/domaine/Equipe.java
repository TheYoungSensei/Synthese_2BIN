package domaine;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;
import util.Util;

public class Equipe {

  private int numero;
  private Division division;
  private Club club;
  private Set<Joueur> joueurs;
  private static int MINIMUM_JOUEURS = 1;

  public Equipe(Club club) {
    enregistrerClub(club);
    this.numero = club.ajouterEquipe(this);
  }


  public int getNumero() {
    return numero;
  }

  // méthode business
  // Cette méthode calcule et renvoie la moyenne des notes elo des joueurs de l'équipe
  // La méthode renvoie -1 s'il n'y a pas de joueur dans l'équipe
  public double moyenneEquipe() {
    if (this.joueurs.isEmpty())
      return -1;
    return joueurs.stream().mapToInt(j -> j.getElo()).average().getAsDouble();
  }


  /*
   * Gestion de l'association Equipe - Club
   */

  public boolean minimumClubGaranti() {
    return this.club != null;
  }

  public Club getClub() throws MinimumMultiplicityException {
    if (!minimumClubGaranti())
      throw new MinimumMultiplicityException();
    return this.club;
  }

  public boolean maximumClubAtteint() {
    return this.club != null;
  }

  private void enregistrerClub(Club club) {
    Util.checkObject(club);
    if (this.club == club)
      return;
    if (maximumClubAtteint())
      return;
    if (club.maximumEquipeAtteint() && !club.contientEquipe(this))
      return;
    this.club = club;
    club.ajouterEquipe(this);
  }

  /*
   * Méthodes permettant de gérer l'association Equipe - Division
   */

  public boolean maximumDivisionAtteint() {
    return this.division != null;
  }

  public boolean minimumDivisionGaranti() {
    return this.division != null;
  }

  public Division getDivision() throws MinimumMultiplicityException {
    if (!minimumDivisionGaranti())
      throw new MinimumMultiplicityException();
    return this.division;
  }

  public boolean enregistrerDivision(Division division) {
    Util.checkObject(division);
    if (this.division == division)
      return false;
    if (maximumDivisionAtteint())
      return false;
    if (division.maximumEquipesAtteint() && division.contientEquipe(this))
      return false;
    this.division = division;
    division.ajouterEquipe(this);
    return true;


  }

  public boolean supprimerDivision() {
    if (this.division == null)
      return false;
    Division div = this.division;
    this.division = null;
    div.supprimerEquipe(this);
    return true;

  }

  /*
   * Gestion de l'association Equipe – Joueur
   */

  public boolean contientJoueur(Joueur j) {
    return joueurs.contains(j);
  }

  public Iterator<Joueur> joueurs() throws RenseignementInsuffisantException, MinimumMultiplicityException {
    if (!minimumJoueursGaranti())
      throw new MinimumMultiplicityException();
    return Collections.unmodifiableSet(this.joueurs).iterator();
  }

  public int nombreJoueurs() {
    return this.joueurs.size();
  }

  public boolean maximumJoueursAtteint() {
    return false;
  }

  public boolean minimumJoueursGaranti() throws RenseignementInsuffisantException {
    try {
      this.getDivision();
    } catch (MinimumMultiplicityException e) {
      throw new RenseignementInsuffisantException();
    }
    return this.joueurs.size() >= MINIMUM_JOUEURS;
  }
  
  public boolean ajouterJoueur(Joueur j) {
    Util.checkObject(j);
    try {
      if(j.getClub().equals(this.club))
        return false;
    } catch (MinimumMultiplicityException e) {
      return false; //Auncun club pour le joueur.
    }
    if(this.contientJoueur(j))
      return false;
    if(this.maximumJoueursAtteint())
      return false;
    try {
      if (j.maximumEquipeAtteint() && j.getEquipe() != this)
        return false;
    } catch (MinimumMultiplicityException e) {
      throw new InternalError();
    }
    this.joueurs.add(j);
    j.enregistrerEquipe(this);
    return true;
  }

  public boolean supprimerJoueur(Joueur j) {
    Util.checkObject(j);
    if(!this.joueurs.contains(j))
      return false;
    joueurs.remove(j);
    j.supprimerEquipe();
    return true;
  }
}
