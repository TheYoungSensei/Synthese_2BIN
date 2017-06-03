package tarifs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static util.Util.*;

public class Tarif implements Cloneable,Serializable {

	private static final long serialVersionUID = -2464416983421475148L;
	private double priseEnCharge, prixAuKilometre;
	private Map<TypeReduction, Double> reductions;

	public static enum TypeReduction {
		TARIF_PLEIN, FAMILLE_NOMBREUSE, SENIOR, VIPO, WEEK_END;
	}

	public Tarif(double priseEnCharge, double prixAuKilometre) {
		checkPositive(priseEnCharge);
		checkPositive(prixAuKilometre);
		this.priseEnCharge = priseEnCharge;
		this.prixAuKilometre = prixAuKilometre;
		this.reductions = new HashMap<>();
		for (TypeReduction reduction : TypeReduction.values()) {
			reductions.put(reduction, 1.0);
		}
	}

	public double getPriseEnCharge() {
		return priseEnCharge;
	}

	public double getPrixAuKilometre() {
		return prixAuKilometre;
	}

	public void definirReduction(TypeReduction r, double coeff) {
		checkObject(r);
		if (r == TypeReduction.TARIF_PLEIN)
			throw new IllegalArgumentException();
		if (coeff <= 0 || coeff > 1)
			throw new IllegalArgumentException();
		reductions.put(r, coeff);
	}

	public double getReduction(TypeReduction reduction) {
		checkObject(reduction);
		return this.reductions.get(reduction);
	}

	@Override
	public String toString() {
		String reduc = "";
		for (Entry<TypeReduction,Double> e : reductions.entrySet()) {
			reduc += e.getKey() + " : " + e.getValue() + "\n";
		}
		return "Prise en charge : " + priseEnCharge + " euros\n" + "Prix au km : " + prixAuKilometre + " euros\n"
				+ reduc;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Tarif clone() {
		Tarif t;
		try {
			t = (Tarif) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		t.reductions = (Map<TypeReduction, Double>) ((HashMap<TypeReduction, Double>) reductions).clone();// les
																									// clés
																									// et
																									// les
																									// valeurs
																									// sont
																									// toutes
																									// 2
																									// immuables
		return t;
	}
}
