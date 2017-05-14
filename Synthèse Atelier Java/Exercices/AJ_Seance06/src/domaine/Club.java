package domaine;


import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import domaine.Joueur.Niveau;
import exceptions.MinimumMultiplicityException;
import util.Util;


public class Club {
  public static final int MIN_EQUIPE = 1;

  private String nom;
  private List<Equipe> equipes;
  private Set<Joueur> joueurs;

  public Club(String nom) {
    this.nom = nom;
  }

  public String getNom() {
    return nom;
  }

  // méthodes business

  // Cette méthode renvoie une map avec, comme clé, un niveau et, comme valeur, la liste des joueurs
  // du club de ce niveau.
  // Il ne faut pas reprendre les joueurs non classés.
  public Map<Niveau, List<Joueur>> joueursParNiveau() {
    return this.joueurs.stream().filter(j -> j.getNiveau() != Niveau.NON_CLASSE)
        .collect(Collectors.groupingBy((Joueur j) -> j.getNiveau()));
  }

  // Cette méthode renvoie l'ensemble des joueurs du club n'appartenant pas à une équipe et classés
  // par ordre décroissant de leur note elo
  public List<Joueur> joueursSansEquipe() {
    return this.joueurs.stream().filter(j -> {
      try {
        j.getEquipe();
      } catch (MinimumMultiplicityException e) {
        return true;
      }
      return false;
    }).sorted(Comparator.comparing(Joueur::getElo)).collect(Collectors.toList());
  }


  /*
   * Gestion de l'association Club - Equipe.
   */

  public boolean maximumEquipeAtteint() {
    return false;
  }

  public boolean minimumEquipeGaranti() {
    return this.equipes.size() >= MIN_EQUIPE;
  }

  public boolean contientEquipe(Equipe equipe) {
    return this.equipes.contains(equipe);
  }

  public int ajouterEquipe(Equipe equipe) {
    if (!this.contientEquipe(equipe)) {
      this.equipes.add(equipe);
      return equipes.size();
    } else {
      return -1;
    }
  }

  public int nombreEquipes() {
    return this.equipes.size();
  }

  /*
   * public boolean supprimerEquipe(Equipe equipe) { if(!this.contientEquipe(equipe)) return false;
   * this.equipes.remove(equipe); equipe.supprimerClub(); return true; }
   */

  public Iterator<Equipe> equipes() throws MinimumMultiplicityException {
    if (!minimumEquipeGaranti())
      throw new MinimumMultiplicityException();
    return Collections.unmodifiableList(this.equipes).iterator();
  }

  /*
   * Gestion de l'association Club - Joueur
   */

  public boolean contientJoueur(Joueur j) {
    return joueurs.contains(j);
  }

  public Iterator<Joueur> joueurs() {
    return Collections.unmodifiableSet(joueurs).iterator();
  }

  public int nombreJoueurs() {
    return joueurs.size();
  }

  public boolean maximumJoueursAtteints() {
    return false;
  }

  public boolean minimumJoueursGaranti() {
    return true;
  }

  public boolean ajouterJoueur(Joueur j) {
    Util.checkObject(j);
    if (this.contientJoueur(j))
      return false;
    if (this.maximumJoueursAtteints())
      return false;
    try {
      if (j.maximumClubAtteint() && j.getClub() != this)
        return false;
    } catch (MinimumMultiplicityException e) {
      throw new InternalError();
    }
    joueurs.add(j);
    j.enregistrerClub(this);
    return true;
  }

  public boolean supprimerJoueur(Joueur j) {
    Util.checkObject(j);
    if (!this.contientJoueur(j))
      return false;
    joueurs.remove(j);
    j.supprimerClub();
    return true;
  }
}
