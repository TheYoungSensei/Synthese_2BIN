package tarifs;

import static util.Util.checkObject;
import static util.Util.checkPositive;

import java.io.Serializable;


public class Trajet implements Comparable<Trajet>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String gareDepart;
  private String gareArrivee;
  private double distance;

  public Trajet(String gareDepart, String gareArrivee, double distance) {
    checkObject(gareDepart);
    checkObject(gareArrivee);
    checkPositive(distance);
    if (gareDepart.equals(gareArrivee))
      throw new IllegalArgumentException();
    this.gareDepart = gareDepart;
    this.gareArrivee = gareArrivee;
    this.distance = distance;
  }

  public double getDistance() {
    return distance;
  }

  @Override
  public int compareTo(Trajet o) {
    int res = this.gareDepart.compareTo(o.gareDepart);
    if (res != 0)
      return res;
    return this.gareArrivee.compareTo(o.gareArrivee);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((gareArrivee == null) ? 0 : gareArrivee.hashCode());
    result = prime * result + ((gareDepart == null) ? 0 : gareDepart.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Trajet other = (Trajet) obj;
    if (gareArrivee == null) {
      if (other.gareArrivee != null)
        return false;
    } else if (!gareArrivee.equals(other.gareArrivee))
      return false;
    if (gareDepart == null) {
      if (other.gareDepart != null)
        return false;
    } else if (!gareDepart.equals(other.gareDepart))
      return false;
    return true;
  }

  public String getGareDepart() {
    return gareDepart;
  }

  public String getGareArrivee() {
    return gareArrivee;
  }

  @Override
  public String toString() {
    return "De " + gareDepart + " ра " + gareArrivee + " : " + distance + " km";
  }

}
