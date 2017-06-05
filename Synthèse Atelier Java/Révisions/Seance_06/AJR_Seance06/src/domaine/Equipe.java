package domaine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.MinimumMultiplicityException;
import exceptions.RenseignementInsuffisantException;
import util.Util;

public class Equipe {

  private int numero;
  private Division division;
  private Club club;
  private Set<Joueur> joueurs = new HashSet<Joueur>();

  public Equipe(Club club) {
    Util.checkObject(club);
    this.club = club;
    this.numero = club.nombreDEquipes() + 1;
    club.ajouterEquipe(this);
  }

  public int getNumero() {
    return numero;
  }

  // méthode business
  // Cette méthode calcule et renvoie la moyenne des notes elo des joueurs de l'équipe
  // La méthode renvoie -1 s'il n'y a pas de joueur dans l'équipe
  public double moyenneEquipe() {
    return joueurs.stream().mapToDouble(Joueur::getElo).average().orElse(-1);
  }

  // gestion de l'association Equipe - Club

  public Club getClub() {
    return club;
  }

  // gestion de l'association Equipe – Division


  public boolean enregistrerDivision(Division division) {
    if (maximumDivisionAtteint())
      return false;
    if (division.maximumEquipesAtteint() && !division.contientEquipe(this))
      return false;
    this.division = division;
    division.ajouterEquipe(this);
    return true;
  }


  public boolean supprimerDivision() {
    if (this.division == null)
      return false;
    Division ex = this.division;
    this.division = null;
    ex.supprimerEquipe(this);
    return true;
  }

  public Division getDivision() throws MinimumMultiplicityException {
    if (!minimumDivisionGaranti())
      throw new MinimumMultiplicityException();
    return this.division;
  }


  public boolean maximumDivisionAtteint() {
    return this.division != null;
  }

  public boolean minimumDivisionGaranti() {
    return this.division != null;
  }

  // gestion de l'association Equipe – Joueur

  public boolean ajouterJoueur(Joueur joueur) {
    if (contient(joueur))
      return false;
    if (!club.contientJoueur(joueur))
      return false;
    if (joueur.maximumEquipeAtteint() && joueur.getEquipe() != this)
      return false;
    joueurs.add(joueur);
    joueur.enregistrerEquipe(this);
    return true;
  }

  public boolean supprimerJoueur(Joueur joueur) {
    if (!contient(joueur))
      return false;
    joueurs.remove(joueur);
    joueur.supprimerEquipe();
    return true;
  }

  public boolean contient(Joueur joueur) {
    Util.checkObject(joueur);
    return joueurs.contains(joueur);
  }

  public int nombreDeJoueurs() {
    return joueurs.size();
  }

  public Iterator<Joueur> joueurs()
      throws RenseignementInsuffisantException, MinimumMultiplicityException {
    if (!minimumJoueursGaranti())
      throw new MinimumMultiplicityException();
    return Collections.unmodifiableSet(joueurs).iterator();
  }

  private boolean minimumJoueursGaranti() throws RenseignementInsuffisantException {
    if (this.division == null)
      throw new RenseignementInsuffisantException();
    return nombreDeJoueurs() >= division.getNombreJoueursMinimumParEquipe();
  }


}
