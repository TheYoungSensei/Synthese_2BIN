package domaine;

import static util.Util.checkString;

public class Domaine {
  private String intitule;

  public Domaine(String intitule) {
    checkString(intitule);
    this.intitule = intitule;
  }

  public String getIntitule() {
    return intitule;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((intitule == null) ? 0 : intitule.hashCode());
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
    Domaine other = (Domaine) obj;
    if (intitule == null) {
      if (other.intitule != null)
        return false;
    } else if (!intitule.equals(other.intitule))
      return false;
    return true;
  }



}
