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

  public synchronized double getSoldeCompte() {
    return soldeCompte;
  }

  public synchronized void setSoldeCompte(double soldeCompte) {
    checkPositiveOrNul(soldeCompte);
    this.soldeCompte = soldeCompte;
  }

  public String getNom() {
    return nom;
  }

  synchronized boolean ajouterLot(Lot lot) {
    checkObject(lot);
    if (this.contientLot(lot))
      return false;
    this.mesLots.add(lot);
    return true;
  }

  synchronized boolean supprimerLot(Lot lot) {
    checkObject(lot);
    if (!this.contientLot(lot))
      return false;
    mesLots.remove(lot);
    return true;

  }

  public synchronized boolean contientLot(Lot lot) {
    checkObject(lot);
    return mesLots.contains(lot);
  }

  public synchronized Iterator<Lot> mesLots() {
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
    Vendeur vendeur =
        new Vendeur(lot, prixInitial, montantDiminution, prixPlancher, intervalleTemps);
    Thread threadVendeur = new Thread(vendeur);
    threadVendeur.start();
    return threadVendeur;
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
      synchronized (lotEnVente) {
        if (lotEnVente.getProprietaire() != Personne.this)
          throw new ThisIsNotMyBatchException();
        if (lotEnVente.isEnVente())
          throw new LotDejaEnVenteException();
        lotEnVente.setEnVente(true);
      }
      this.lotEnVente = lotEnVente;
      this.prixInitial = prixInitial;
      this.intervalleTemps = intervalleTemps;
      this.montantDiminution = montantDiminution;
      this.prixPlancher = prixPlancher;
    }

    @Override
    public void run() {
      lotEnVente.setPrixUnitaire(prixInitial);
      Bourse.INSTANCE.ajouterLot(lotEnVente);
      System.out.println("Mise en vente du lot numéro " + lotEnVente.getNumero()
          + " au prix initial de " + prixInitial);
      do {
        try {
          Thread.sleep(intervalleTemps);
        } catch (InterruptedException e) {
          break;
        }
        synchronized (lotEnVente) {
          if (lotEnVente.isEnVente() && lotEnVente.getProprietaire() == Personne.this
              && lotEnVente.getPrixUnitaire() - montantDiminution >= prixPlancher) {
            lotEnVente.setPrixUnitaire(lotEnVente.getPrixUnitaire() - montantDiminution);
            System.out.println("Diminution du prix du lot numéro " + lotEnVente.getNumero()
                + " au prix de " + lotEnVente.getPrixUnitaire());
          } else {
            break;
          }
        }
      } while (!Thread.currentThread().isInterrupted());
      synchronized (lotEnVente) {
        if (lotEnVente.isEnVente() && lotEnVente.getProprietaire() == Personne.this) {
          System.out.println("Lot numéro " + lotEnVente.getNumero() + " retiré de la vente");
          Bourse.INSTANCE.supprimerLot(lotEnVente);
          lotEnVente.setEnVente(false);
          System.out.println("Lot " + lotEnVente + " retiré de la vente");
        }
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
    Acheteur acheteur = new Acheteur(quantite, prixUnitaireMax);
    Thread threadAcheteur = new Thread(acheteur);
    threadAcheteur.start();
    return threadAcheteur;
  }

  private class Acheteur implements Runnable {

    private int quantiteRestante;
    private double prixUnitaireMax;

    public Acheteur(int quantite, double prixUnitaireMax) {
      checkPositive(quantite);
      checkPositive(prixUnitaireMax);
      this.quantiteRestante = quantite;
      this.prixUnitaireMax = prixUnitaireMax;
    }

    public void run() {
      do {
        Iterator<Lot> lotsEnVente = Bourse.INSTANCE.lotsEnvente();
        while (lotsEnVente.hasNext() && !Thread.currentThread().isInterrupted()) {
          Lot lot = lotsEnVente.next();
          synchronized (lot) {
            if (lot.isEnVente() && lot.getProprietaire() != Personne.this
                && lot.getQuantite() <= quantiteRestante
                && lot.getPrixUnitaire() <= prixUnitaireMax) {
              acheterLot(lot);
              if (quantiteRestante == 0)
                return;
            }
          }
          try {
            Thread.sleep((int) (Math.random() * 1001));
          } catch (InterruptedException e) {
            return;
          }
        }
      } while (!Thread.currentThread().isInterrupted());
    }

    public void acheterLot(Lot lot) {
      double cout = lot.getQuantite() * lot.getPrixUnitaire();
      if (cout > soldeCompte)
        return;
      Personne.this.setSoldeCompte(getSoldeCompte() - cout);
      Personne.this.ajouterLot(lot);
      lot.setEnVente(false);
      Bourse.INSTANCE.supprimerLot(lot);
      Personne ex = lot.getProprietaire();
      synchronized (ex) { // Pas vraiment obligatoire (méthode déjà synchronized).
        ex.setSoldeCompte(ex.getSoldeCompte() + cout);
        ex.supprimerLot(lot);
      }
      lot.setProprietaire(Personne.this);
      quantiteRestante -= lot.getQuantite();
      soldeCompte = soldeCompte - cout;
      System.out.println("Achat du lot numéro " + lot.getNumero() + " par " + Personne.this.getNom()
          + " au prix de " + lot.getPrixUnitaire());
    }
  }



}
