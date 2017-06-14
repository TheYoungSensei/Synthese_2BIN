package csacre15.ipl.be.myapp.model;

/**
 * Created by csacre15 on 29/03/2017.
 */
public class Participant {

    private int id;
    private String prenom;
    private String nom;
    private String tel;
    private String boisson;

    public Participant(String prenom, String nom, String tel, String boisson) {
        this.prenom = prenom;
        this.nom = nom;
        this.tel = tel;
        this.boisson = boisson;
    }

    public Participant(int id, String prenom, String nom, String tel, String boisson) {

        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.tel = tel;
        this.boisson = boisson;
    }

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getTel() {
        return tel;
    }

    public String getBoisson() {
        return boisson;
    }
}
