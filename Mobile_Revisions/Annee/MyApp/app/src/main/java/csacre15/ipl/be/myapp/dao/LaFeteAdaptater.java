package csacre15.ipl.be.myapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import csacre15.ipl.be.myapp.model.Participant;

/**
 * Created by csacre15 on 29/03/2017.
 */
public class LaFeteAdaptater {

    private LaFeteDb db;
    private Cursor lastCursor;

    public LaFeteAdaptater(Context context) {
        this.db = new LaFeteDb(context);
    }

    public void open() throws SQLException {
        this.db.open();
    }

    public void close() {
        this.db.close();
    }

    public long addUtil(String nom, String prenom, String boisson, String tel) {
        ContentValues nouvelleLigne = new ContentValues();
        nouvelleLigne.put(LaFeteContract.TABLE_UTIL_NOM, nom);
        nouvelleLigne.put(LaFeteContract.TABLE_UTIL_PRENOM, prenom);
        nouvelleLigne.put(LaFeteContract.TABLE_UTIL_BOISSON, boisson);
        nouvelleLigne.put(LaFeteContract.TABLE_UTIL_TEL, tel);
        return this.db.addUtil(nouvelleLigne);
    }

    public List<Participant> findLast10(){
        Cursor cursor = this.db.selectAll();
        this.lastCursor = cursor;
        List<Participant> participants = new ArrayList<Participant>();
        if(cursor.moveToFirst()) {
            do {
                int id=Integer.valueOf(cursor.getString(0));
                String nom =cursor.getString(1);
                String prenom=cursor.getString(2);
                String boisson=cursor.getString(3);
                String tel = cursor.getString(4);
                Participant participant = new Participant(id, nom, prenom, tel, boisson);
                participants.add(participant);
            } while(cursor.moveToNext());
        }
        return participants;
    }


    public Cursor getCursor() {
        return lastCursor;
    }
}
