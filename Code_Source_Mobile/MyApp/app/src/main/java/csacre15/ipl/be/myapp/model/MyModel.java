package csacre15.ipl.be.myapp.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import csacre15.ipl.be.myapp.dao.LaFeteAdaptater;

/**
 * Created by csacre15 on 23/03/2017.
 */
public class MyModel {

    public static final int MAXDISPLAY = 10;
    private LaFeteAdaptater adaptateur;

    private List<MainModelChangeObserver> observers = new ArrayList<MainModelChangeObserver>();
    private long lastUtil;

    public void setAdaptateur(LaFeteAdaptater adaptateur) {
        this.adaptateur = adaptateur;
    }

    public void ajouterUtil(String nom, String prenom, String boisson, String tel) {
        this.lastUtil =  adaptateur.addUtil(nom, prenom, boisson, tel);
        notifyAllObservers();
    }

    public long getLastUtil() {
        return this.lastUtil;
    }

    public String[] findLastThen() {
        List<Participant> participants = adaptateur.findLast10();
        String[] lignes = new String[participants.size()];
        for(int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            lignes[i] = participants.size() - i + ") " + participant.getNom()+ " " + participant.getPrenom() + " " + participant.getTel();
        }
        return lignes;
    }

    public Cursor getCursor10Last() {
        adaptateur.findLast10();
        return adaptateur.getCursor();
    }

    private void notifyAllObservers() {
        for(MainModelChangeObserver obs : this.observers) {
            obs.notifyChange();
        }
    }

    public void registerObserver(MainModelChangeObserver obs) {
        observers.add(obs);
    }

    public void unregisterObserver(MainModelChangeObserver obs) {
        observers.remove(obs);
    }

    public interface MainModelChangeObserver {
        public void notifyChange();
    }




}
