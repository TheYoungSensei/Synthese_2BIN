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
		numero = ++nbLots;
	}

	public boolean isEnVente() {
		return enVente;
	}

	public void setEnVente(boolean enVente) {
		this.enVente = enVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public double getPrixUnitaire() {
		return prixUnitaire;
	}

	public Personne getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Personne proprietaire) {
		checkObject(proprietaire);
		this.proprietaire = proprietaire;

	}

	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public int getNumero() {
		return numero;
	}

	@Override
	public String toString() {
		return "numéro " + numero + " au prix de " + prixUnitaire;
	}
}
