package be.ipl.csacre15.partymanager.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import be.ipl.csacre15.partymanager.dal.PartyAdaptater;

/**
 * Created by sacre on 13-06-17.
 */
public class PartyModel {

    public static final int MAXTODISPLAY = 10;
    private PartyAdaptater adaptater;
    private List<PartyObserver> observers = new ArrayList<PartyObserver>();
    private long util;

    public void setAdapteur(PartyAdaptater adaptater) {
        this.adaptater = adaptater;
    }

    public void addUtil(Participant participant) {
        this.util = adaptater.addUtil(participant);
        notifyAllObservers();
    }

    public long getLastUtil() {
        return this.util;
    }

    public String[] findLast10() {
        List<Participant> participants = adaptater.findLast10();
        String[] lignes = new String[participants.size()];
        for(int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            lignes[i] = participants.size() - i + ") "
                    + participant.getLastName() + " "
                    +participant.getFirstname() + " "
                    + participant.getTel();
        }
        return lignes;
    }

    public Cursor getCursor10Last() {
        adaptater.findLast10();
        return adaptater.getCursor();
    }

    private void notifyAllObservers() {
        for(PartyObserver obs : observers) {
            obs.notifyChange();
        }
    }

    public void registerObserver(PartyObserver obs) {
        observers.add(obs);
    }

    public void unregisterObserver(PartyObserver obs) {
        observers.remove(obs);
    }


    public interface PartyObserver {
        public void notifyChange();
    }

}
