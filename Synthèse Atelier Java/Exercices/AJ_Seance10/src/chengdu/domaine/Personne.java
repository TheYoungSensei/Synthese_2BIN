package chengdu.domaine;

import static chengdu.util.Util.checkObject;
import static chengdu.util.Util.checkPositive;
import static chengdu.util.Util.checkPositiveOrNul;
import static chengdu.util.Util.checkString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import chengdu.exceptions.LotDejaEnVenteException;
import chengdu.exceptions.ThisIsNotMyBatchException;

public class Personne {
  private String nom;
  private double soldeCompte;
  private Set<Lot> mesLots = new HashSet<Lot>();

  public Personne(String nom, int soldeCompte) {
    checkString(nom);
    checkPositiveOrNul(soldeCompte);
    this.nom = nom;
    this.soldeCompte = soldeCompte;
  }

  public double getSoldeCompte() {
    return soldeCompte;
  }

  public void setSoldeCompte(double soldeCompte) {
    checkPositiveOrNul(soldeCompte);
    this.soldeCompte = soldeCompte;
  }

  public String getNom() {
    return nom;
  }

  boolean ajouterLot(Lot lot) {
    checkObject(lot);
    if (this.contientLot(lot))
      return false;
    this.mesLots.add(lot);
    return true;
  }

  boolean supprimerLot(Lot lot) {
    checkObject(lot);
    if (!this.contientLot(lot))
      return false;
    mesLots.remove(lot);
    return true;

  }

  public boolean contientLot(Lot lot) {
    checkObject(lot);
    return mesLots.contains(lot);
  }

  public Iterator<Lot> mesLots() {
    return Collections.unmodifiableSet(mesLots).iterator();
  }

  /**
   * Lance un thread vendeur pour un lot. Celui-ci va diminuer progressivement le prix du lot
   * jusqu'à atteindre le prixPlancher.
   * 
   * @param lot Lot à vendre. Il doit appartenir à la personne
   * @param prixInitial
   * @param montantDiminution Diminution du prix à chaque intervalle
   * @param prixPlancher
   * @param intervalleTemps Intervalle de décrémentation du prix
   * @return Le thread vendeur si on a pu le créer
   * @throws LotDejaEnVenteException si le lot est déjà en vente
   * @throws ArgumentInvalideException si le lot est null
   * @throws ThisIsNotMyBatchException si le lot ne nous appartient pas
   */
  public Thread mettreUnLotEnVente(Lot lot, double prixInitial, double montantDiminution,
      double prixPlancher, int intervalleTemps)
      throws LotDejaEnVenteException, ThisIsNotMyBatchException {
    Thread vendeur =
        new Thread(new Vendeur(lot, prixInitial, montantDiminution, prixPlancher, intervalleTemps));
    vendeur.start();
    return vendeur;
  }

  private class Vendeur implements Runnable {

    private Lot lotEnVente;
    private double prixInitial;
    private int intervalleTemps;
    private double montantDiminution;
    private double prixPlancher;

    public Vendeur(Lot lotEnVente, double prixInitial, double montantDiminution,
        double prixPlancher, int intervalleTemps)
        throws ThisIsNotMyBatchException, LotDejaEnVenteException {
      checkObject(lotEnVente);
      checkPositive(intervalleTemps);
      checkPositive(montantDiminution);
      checkPositive(prixPlancher);
      if (prixInitial < prixPlancher)
        throw new IllegalArgumentException();
      // TODO lancer une ThisIsNotMyBatchException si le lot mis en vente n'appartient pas à la
      // personne qui l'a mis en vente
      if (!lotEnVente.getProprietaire().equals(Personne.this))
        throw new ThisIsNotMyBatchException();
      if (lotEnVente.isEnVente())
        throw new LotDejaEnVenteException();
      lotEnVente.setEnVente(true);
      this.lotEnVente = lotEnVente;
      this.prixInitial = prixInitial;
      this.intervalleTemps = intervalleTemps;
      this.montantDiminution = montantDiminution;
      this.prixPlancher = prixPlancher;
    }

    @Override
    public void run() {
      synchronized (lotEnVente) {
        lotEnVente.setPrixUnitaire(prixInitial);
        Bourse.INSTANCE.ajouterLot(lotEnVente);
      }
      System.out.println("Mise en vente du lot numéro " + lotEnVente.getNumero()
          + " au prix initial de " + prixInitial);
      do {
        try {
          Thread.sleep(intervalleTemps);
        } catch (InterruptedException e) {
          break;
        }
        if (Bourse.INSTANCE.contientLot(lotEnVente)
            && lotEnVente.getProprietaire().equals(Personne.this)
            && lotEnVente.getPrixUnitaire() - montantDiminution >= prixPlancher) {
          synchronized (lotEnVente) {
            lotEnVente.setPrixUnitaire(lotEnVente.getPrixUnitaire() - montantDiminution);
          }
          System.out.println("Diminution du prix du lot numéro " + lotEnVente.getNumero()
              + " au prix de " + lotEnVente.getPrixUnitaire());
        } else {
          break;
        }
      } while (!Thread.interrupted());
      if (Bourse.INSTANCE.contientLot(lotEnVente)
          && lotEnVente.getProprietaire().equals(Personne.this)) {
        System.out.println("Lot numéro " + lotEnVente.getNumero() + " retiré de la vente");
        synchronized (lotEnVente) {
          Bourse.INSTANCE.supprimerLot(lotEnVente);
          lotEnVente.setEnVente(false);
        }
        System.out.println("Lot " + lotEnVente + " retiré de la vente");
      }
    }
  }

  /**
   * Lance un thread acheteur.
   * 
   * @param quantite
   * @param prixUnitaireMax
   * @return le thread de l'acheteur
   */
  public Thread lancerUnAcheteur(int quantite, double prixUnitaireMax) {
    Thread acheteur = new Thread(new Acheteur(quantite, prixUnitaireMax));
    acheteur.start();
    return acheteur;
  }

  private class Acheteur implements Runnable {

    private int quantite;
    private double prixUnitaireMax;

    public Acheteur(int quantite, double prixUnitaireMax) {
      checkPositive(quantite);
      checkPositive(prixUnitaireMax);
      this.quantite = quantite;
      this.prixUnitaireMax = prixUnitaireMax;
    }

    @Override
    public void run() {
      do {
        Iterator<Lot> lots = Bourse.INSTANCE.lotsEnvente();
        while (lots.hasNext()) {
          Lot lot;
          lot = lots.next();
          if (!lot.getProprietaire().equals(Personne.this)
              && lot.getPrixUnitaire() <= this.prixUnitaireMax && lot.getQuantite() < quantite
              && Personne.this.getSoldeCompte()
                  - (lot.getPrixUnitaire() * lot.getQuantite()) >= 0) {
            Bourse.INSTANCE.supprimerLot(lot);
            double total = (lot.getPrixUnitaire() * lot.getQuantite());
            synchronized (lot.getProprietaire()) {
              lot.getProprietaire().soldeCompte += total;
              Personne.this.soldeCompte -= total;
              lot.getProprietaire().supprimerLot(lot);
              lot.setProprietaire(Personne.this);
              Personne.this.ajouterLot(lot);
            }
            quantite -= lot.getQuantite();
            System.out.println(
                "Achat du lot numéro " + lot.getNumero() + " par Emmeline au prix de " + total);
            if (quantite == 0) {
              Thread.currentThread().interrupt();
            }
          }

        }
        try {
          Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
        }
      } while (!Thread.interrupted());
    }
  }
}
