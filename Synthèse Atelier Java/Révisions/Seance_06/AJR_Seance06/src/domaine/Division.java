package domaine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.MinimumMultiplicityException;
import util.Util;

public class Division {

  public static final int MAX_EQUIPE = 12;
  public static final int MIN_EQUIPE = 12;

  private String nom;
  private int nombreJoueursMinimumParEquipe;
  private Set<Equipe> equipes = new HashSet<Equipe>();

  public Division(String nom, int nombreJoueursMinimumParEquipe) {
    Util.checkString(nom);
    Util.checkPositive(nombreJoueursMinimumParEquipe);
    this.nom = nom;
    this.nombreJoueursMinimumParEquipe = nombreJoueursMinimumParEquipe;
  }

  public String getNom() {
    return nom;
  }

  public int getNombreJoueursMinimumParEquipe() {
    return nombreJoueursMinimumParEquipe;
  }

  // gestion de l'association Equipe – Division


  public boolean ajouterEquipe(Equipe equipe) {
    if (contientEquipe(equipe))
      return false;
    if (maximumEquipesAtteint())
      return false;
    try {
      if (equipe.maximumDivisionAtteint() && equipe.getDivision() != this)
        return false;
    } catch (MinimumMultiplicityException e) {
      throw new InternalError();
    }
    this.equipes.add(equipe);
    equipe.enregistrerDivision(this);
    return true;
  }

  public boolean supprimerEquipe(Equipe equipe) {
    if (!contientEquipe(equipe))
      return false;
    this.equipes.remove(equipe);
    equipe.supprimerDivision();
    return true;
  }


  public boolean contientEquipe(Equipe equipe) {
    Util.checkObject(equipe);
    return equipes.contains(equipe);
  }

  public Iterator<Equipe> equipes() throws MinimumMultiplicityException {
    if (!minimumEquipesGaranti())
      throw new MinimumMultiplicityException();
    return Collections.unmodifiableSet(equipes).iterator();
  }

  public int nombreDEquipes() {
    return equipes.size();
  }


  public boolean maximumEquipesAtteint() {
    return this.equipes.size() == MAX_EQUIPE;
  }

  public boolean minimumEquipesGaranti() {
    return this.equipes.size() >= MIN_EQUIPE;
  }
}
