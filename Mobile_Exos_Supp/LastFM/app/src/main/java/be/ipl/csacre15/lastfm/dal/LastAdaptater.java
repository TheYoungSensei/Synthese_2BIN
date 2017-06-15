package be.ipl.csacre15.lastfm.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.ipl.csacre15.lastfm.model.Artist;
import be.ipl.csacre15.lastfm.model.ArtistImpl;

/**
 * Created by sacre on 15-06-17.
 */
public class LastAdaptater {

    private LastDb db;
    private Cursor cursor;
    private Set<Artist> artists = new HashSet<Artist>();

    public LastAdaptater(Context context) {
        this.db = new LastDb(context);
    }

    public void open() {
        this.db.open();
    }

    public void close() {
        this.db.close();
    }

    public long addArtist(Artist artist) {
        ContentValues newLine = new ContentValues();
        newLine.put(LastContract.ARTIST_NAME, artist.getName());
        newLine.put(LastContract.NB_LISTENERS, artist.getNbListeners());
        return this.db.addArtist(newLine);
    }

    public List<Artist> selectAll()  {
        this.cursor = this.db.selectAll();
        List<Artist> artists = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                Artist artist = new ArtistImpl();
                artist.setId(cursor.getInt(0));
                artist.setName(cursor.getString(1));
                artist.setNbListeners(cursor.getInt(2));
                artists.add(artist);
                this.artists.add(artist);
            } while(cursor.moveToNext());
        }
        return artists;
    }

    public boolean existe(Artist artistDto) {
        return this.artists.contains(artistDto);
    }

    public Cursor getCursor() {
        return this.cursor;
    }
}
