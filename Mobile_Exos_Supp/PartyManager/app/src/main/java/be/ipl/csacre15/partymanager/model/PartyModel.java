package be.ipl.csacre15.partymanager.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.ipl.csacre15.partymanager.dal.PartyAdaptater;

/**
 * Created by sacre on 13-06-17.
 */
public class PartyModel {

    public static final int MAXTODISPLAY = 10;
    private PartyAdaptater adaptater;
    private List<PartyObserver> observers = new ArrayList<PartyObserver>();
    private long util;
    private Set<Participant> participants = new HashSet<Participant>();

    public void setAdapteur(PartyAdaptater adaptater) {
        this.adaptater = adaptater;
    }

    public void addUtil(Participant participant) {
        this.participants.add(participant);
        notifyAllObservers();
    }


    public String[] findLast10() {
        int taille = participants.size();
        if(participants.size() > 10) {
            taille = 10;
        }
        String[] lignes = new String[taille];
        int i = 0;
        for(Participant participant : participants) {
            lignes[i] = participants.size() - i + ") "
                    + participant.getLastName() + " "
                    +participant.getFirstname() + " "
                    + participant.getTel();
            i ++;
            if(i >= taille)
                break;
        }
        return lignes;
    }

    public Set<Participant> participants() {
        return Collections.unmodifiableSet(participants);
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
