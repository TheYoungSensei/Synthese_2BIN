package be.ipl.csacre15.lastfm.model;

/**
 * Created by sacre on 15-06-17.
 */
public class ArtistImpl implements Artist{

    private int id;
    private String name;
    private int nbListeners;

    public ArtistImpl() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistImpl artist = (ArtistImpl) o;
        return name.equals(artist.name);

    }

    @Override
    public int hashCode() {
        int  result = 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNbListeners() {
        return nbListeners;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNbListeners(int nbListeners) {
        this.nbListeners = nbListeners;
    }
}
