package be.ipl.csacre15.partymanager.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import be.ipl.csacre15.partymanager.model.Participant;
import be.ipl.csacre15.partymanager.model.ParticipantImpl;

/**
 * Created by sacre on 13-06-17.
 */
public class PartyAdaptater {

    private PartyDb db;
    private Cursor cursor;

    public PartyAdaptater(Context context) {
        this.db = new PartyDb(context);
    }

    public void open() {
        this.db.open();
    }

    public void close() {
        this.db.close();
    }

    public long addUtil(Participant participantdto) {
        Participant participant = (Participant) participantdto;
        ContentValues newLine = new ContentValues();
        newLine.put(PartyContract.UTIL_NAME, participant.getLastName());
        newLine.put(PartyContract.UTIL_FIRSTNAME, participant.getFirstname());
        newLine.put(PartyContract.UTIL_DRINK, participant.getDrink());
        newLine.put(PartyContract.UTIL_TEL, participant.getTel());
        return this.db.addUtil(newLine);
    }

    public List<Participant> findLast10() {
        this.cursor = this.db.selectAll();
        List<Participant> participants = new ArrayList<Participant>();
        if(cursor.moveToFirst()) {
            do {
                Participant participant = new ParticipantImpl();
                participant.setId(cursor.getInt(0));
                participant.setLastname(cursor.getString(1));
                participant.setFirstname(cursor.getString(2));
                participant.setDrink(cursor.getString(3));
                participant.setTel(cursor.getString(4));
                participants.add(participant);
            } while(cursor.moveToNext());
        }
        return participants;
    }

    public Cursor getCursor() {
        return this.cursor;
    }




}
