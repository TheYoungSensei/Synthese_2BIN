package com.example.christopher.pae_master_flow;

import com.example.christopher.pae_master_flow.dummy.DetailsContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by csacre15 on 11/05/2017.
 */
public class BusinessModel {

    private List<Observer> eventsObserver = new ArrayList<Observer>();
    private List<Observer> companiesObserver = new ArrayList<Observer>();
    List<String> companiesToDisplay = new ArrayList<String>();

    public BusinessModel() {

    }

    public void showEvents(String s) {
        JSONArray events = null;
        try {
            events = new JSONArray(s);
            for(int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                String date = event.getJSONObject("date").getInt("dayOfMonth") + "-" + event.getJSONObject("date").getInt("monthValue") + "-" + event.getJSONObject("date").getInt("year");
                DetailsContent.addItem(new DetailsContent.DetailsItem(String.valueOf(i), date, String.valueOf(event.getInt("eventId"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyEvents();
    }

    public void showCompanies(String s) {
        this.companiesToDisplay = new ArrayList<>();
        try {
            JSONArray participations = new JSONArray(s);
            for(int i =0; i < participations.length(); i++) {
                JSONObject participation = participations.getJSONObject(i);
                JSONObject company = participation.getJSONObject("company");
                this.companiesToDisplay.add(company.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyCompanies();
    }

    public void notifyEvents() {
        for(Observer obs : eventsObserver) {
            obs.notifyChange();
        }
    }

    public void notifyCompanies() {
        for(Observer obs : companiesObserver) {
            obs.notifyChange();
        }
    }

    public void registerCompaniesObserver(Observer obs) {
        this.companiesObserver.add(obs);
    }

    public void unregisterCompaniesObserver(Observer obs) {
        this.companiesObserver .remove(obs);
    }

    public interface Observer {

        public void notifyChange();
    }

    public List<String> displayCompanies() {
        return Collections.unmodifiableList(this.companiesToDisplay);
    }




}
