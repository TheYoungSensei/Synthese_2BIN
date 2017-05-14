package tarifs;

import static util.Util.checkPositive;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Tarif implements Cloneable, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final double ONE = 1;


  public enum TypeReduction {

    TARIF_PLEIN, FAMILLE_NOMBREUSE, SENIOR, VIPO, WEEK_END;
  }

  private double priseEnCharge, prixAuKilometre;

  private Map<TypeReduction, Double> reductions;

  public Tarif(double priseEnCharge, double prixAuKilometre) {
    checkPositive(priseEnCharge);
    checkPositive(prixAuKilometre);
    this.priseEnCharge = priseEnCharge;
    this.prixAuKilometre = prixAuKilometre;
    reductions = new HashMap<TypeReduction, Double>();
    for (TypeReduction type : TypeReduction.values()) {
      reductions.put(type, ONE);
    }
  }

  public double getPriseEnCharge() {
    return priseEnCharge;
  }

  public double getPrixAuKilometre() {
    return prixAuKilometre;
  }

  @Override
  public String toString() {
    String reduc = "";
    for (TypeReduction type : TypeReduction.values()) {
      reduc += type.name().toString() + " : " + reductions.get(type);
    }
    return "Prise en charge : " + priseEnCharge + " euros\n" + "Prix au km : " + prixAuKilometre
        + " euros\n" + reduc;
  }


  public void definirReduction(TypeReduction type, double coeff) {
    reductions.put(type, coeff);
  }


  public Double getReduction(TypeReduction type) {
    return reductions.get(type);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Object clone() {
    Tarif t = null;
    try {
      t = (Tarif) super.clone();
      t.reductions =
          (Map<TypeReduction, Double>) ((HashMap<TypeReduction, Double>) this.reductions).clone();
    } catch (CloneNotSupportedException clo) {
      clo.printStackTrace();
    }
    return t;
  }



}
