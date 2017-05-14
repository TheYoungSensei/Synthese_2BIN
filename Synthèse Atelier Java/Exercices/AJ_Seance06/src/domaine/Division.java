package domaine;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import exceptions.MinimumMultiplicityException;
import util.Util;

public class Division {

  public static final int MAX_EQUIPE = 12;
  public static final int MIN_EQUIPE = 12;

  private String nom;
  private int nombreJoueursMinimumParEquipe;
  Set<Equipe> equipes;

  public Division(String nom, int nombreJoueursMinimumParEquipe) {
    this.nom = nom;
    this.nombreJoueursMinimumParEquipe = nombreJoueursMinimumParEquipe;
  }

  public String getNom() {
    return nom;
  }

  public int getNombreJoueursMinimumParEquipe() {
    return nombreJoueursMinimumParEquipe;
  }

  public boolean ajouterEquipe(Equipe equipe) {
    Util.checkObject(equipe);
    if (contientEquipe(equipe))
      return false;
    if (maximumEquipesAtteint())
      return false;
    try {
      if (equipe.maximumDivisionAtteint() && !equipe.getDivision().equals(this))
        return false;
    } catch (MinimumMultiplicityException mme) {
      throw new InternalError();
    }
    this.equipes.add(equipe);
    equipe.enregistrerDivision(this);
    return true;
  }

  public boolean supprimerEquipe(Equipe equipe) {
    Util.checkObject(equipe);
    if (!contientEquipe(equipe))
      return false;
    this.equipes.remove(equipe);
    equipe.supprimerDivision();
    return true;
  }

  public Iterator<Equipe> equipes() throws MinimumMultiplicityException {
    if (!minimumEquipeGaranti())
      throw new MinimumMultiplicityException();
    return Collections.unmodifiableSet(this.equipes).iterator();
  }

  public boolean contientEquipe(Equipe equipe) {
    return this.equipes.contains(equipe);
  }

  public boolean minimumEquipeGaranti() {
    return this.equipes.size() >= MIN_EQUIPE;
  }

  public boolean maximumEquipesAtteint() {
    return this.equipes.size() >= MAX_EQUIPE;
  }

  public int nombreEquipes() {
    return this.equipes.size();
  }


}
