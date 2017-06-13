package be.ipl.csacre15.partymanager.model;

/**
 * Created by sacre on 13-06-17.
 */
public interface Participant {

    public int getId();

    public String getFirstname();

    public String getLastName();

    public String getTel();

    public String getDrink();

    public void setId(int id);

    public void setFirstname(String firstname);

    public void setLastname(String lastname);

    public void setTel(String tel);

    public void setDrink(String drink);
}
