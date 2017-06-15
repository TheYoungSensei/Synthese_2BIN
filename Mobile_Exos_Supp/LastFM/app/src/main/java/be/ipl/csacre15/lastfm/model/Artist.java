package be.ipl.csacre15.lastfm.model;

/**
 * Created by sacre on 15-06-17.
 */
public interface Artist {

    public int getId();

    public String getName();

    public int getNbListeners();

    public void setId(int id);

    public void setName(String name);

    public void setNbListeners(int nbListeners);
}
