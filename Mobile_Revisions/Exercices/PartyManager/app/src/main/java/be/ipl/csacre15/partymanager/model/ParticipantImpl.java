package be.ipl.csacre15.partymanager.model;

/**
 * Created by sacre on 13-06-17.
 */
public class ParticipantImpl implements Participant {

    private int id;
    private String firstname;
    private String lastname;
    private String tel;
    private String drink;

    public ParticipantImpl() {
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getFirstname() {
        return this.firstname;
    }

    @Override
    public String getLastName() {
        return this.lastname;
    }

    @Override
    public String getTel() {
        return this.tel;
    }

    @Override
    public String getDrink() {
        return this.drink;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }
}
