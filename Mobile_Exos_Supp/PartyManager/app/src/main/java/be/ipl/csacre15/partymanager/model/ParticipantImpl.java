package be.ipl.csacre15.partymanager.model;

import java.util.Objects;

/**
 * Created by sacre on 13-06-17.
 */
public class ParticipantImpl implements Participant {

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParticipantImpl that = (ParticipantImpl) o;
            return Objects.equals(tel, that.tel);
        }

        @Override
        public int hashCode() {
        return Objects.hash(tel);
    }

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
