package chengdu.domaine;

import static chengdu.util.Util.*;

public class Lot {
	private int quantite;
	private double prixUnitaire;
	private int numero;
	private static int nbLots = 0;
	private boolean enVente = false;
	private Personne proprietaire;

	public Lot(int quantite, Personne proprietaire) {
		checkPositive(quantite);
		checkObject(proprietaire);
		this.quantite = quantite;
		this.proprietaire = proprietaire;
		this.proprietaire.ajouterLot(this);
		synchronized (Lot.class) {
			numero = ++nbLots;
		}
	}

	public synchronized boolean isEnVente() {
		return enVente;
	}

	public synchronized void setEnVente(boolean enVente) {
		this.enVente = enVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public synchronized double getPrixUnitaire() {
		return prixUnitaire;
	}

	public synchronized Personne getProprietaire() {
		return proprietaire;
	}

	public synchronized void setProprietaire(Personne proprietaire) {
		checkObject(proprietaire);
		this.proprietaire = proprietaire;

	}

	public synchronized void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public int getNumero() {
		return numero;
	}

	@Override
	public synchronized String toString() {
		return "numéro " + numero + " au prix de " + prixUnitaire;
	}
}
