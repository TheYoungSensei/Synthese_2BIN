package domaine;

import static util.Util.checkObject;
import static util.Util.checkString;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import domaine.Employe.NiveauDeCompetence;
import util.Util;

public class Projet {
  private String titre;
  private String description;
  private Set<Employe> employes = new HashSet<Employe>();
  private Set<Domaine> domaines = new HashSet<Domaine>();

  public Projet(String titre, String description) {
    checkString(titre);
    checkString(description);
    this.titre = titre;
    this.description = description;
  }

  public boolean ajouterDomaine(Domaine domaine) {
    checkObject(domaine);
    return domaines.add(domaine);
  }

  public Set<Domaine> domaines() {
    return (Set<Domaine>) ((HashSet<Domaine>) domaines).clone();
  }


  // renvoie la liste des employés du projet par niveau de compétence dans le domaine passé en
  // paramètre
  // VOUS DEVEZ UTILISER LES STREAMS POUR IMPLÉMENTER CETTE MÉTHODE !!
  public Map<NiveauDeCompetence, List<Employe>> employesParNiveau(Domaine domaine) {
    checkObject(domaine);
    Comparator<Employe> comp = new Comparator<Employe>() {

      @Override
      public int compare(Employe e1, Employe e2) {
        return e1.trouverNiveau(domaine).ordinal() - e2.trouverNiveau(domaine).ordinal();
      }

    };
    return employes.stream().filter(e -> e.trouverNiveau(domaine) != null).sorted(comp)
        .collect(Collectors.groupingBy((Employe e) -> e.trouverNiveau(domaine)));
  }


  // gestion de l'association avec Employe

  public boolean ajouterEmploye(Employe employe) {
    Util.checkObject(employe);
    if (!employe.possedeUneCompetence(domaines))
      return false;
    if (contientEmploye(employe))
      return false;
    if (employe.maximumProjetAtteint() && !employe.contientProjet(this))
      return false;
    employes.add(employe);
    employe.ajouterProjet(this);
    return true;
  }

  public boolean supprimerEmploye(Employe employe) {
    if (!contientEmploye(employe))
      return false;
    return true;
  }

  public boolean contientEmploye(Employe employe) {
    checkObject(employe);
    return employes.contains(employe);
  }

  public Iterator<Employe> employes() {
    return Collections.unmodifiableSet(employes).iterator();
  }

  public int nombreDEmployes() {
    return employes.size();
  }



}
