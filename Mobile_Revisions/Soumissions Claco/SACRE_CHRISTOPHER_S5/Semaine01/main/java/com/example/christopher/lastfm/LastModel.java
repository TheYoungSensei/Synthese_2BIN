package com.example.christopher.lastfm;

import android.graphics.PorterDuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

/**
 * Created by Christopher on 19-04-17.
 */
public class LastModel {

    private Set<ModelObserver> observers = new HashSet<ModelObserver>();
    private Map<String, Integer> formatted;

    public Map<String, Integer> getResult() {
        return Collections.unmodifiableMap(formatted);
    }

    public void registerObserver(ModelObserver obs) {
        this.observers.add(obs);
    }

    public void unregisterObserver(ModelObserver obs) {
        this.observers.remove(obs);
    }

    private void notifyAllObservers() {
        for(ModelObserver observer : observers ) {
            observer.notifyChange();
        }
    }

    public void getDownload(String s) {
        this.formatted = new HashMap<String, Integer>();
        try {
            JSONArray artists = new JSONObject(s).getJSONObject("topartists").getJSONArray("artist");
            for(int i = 0; i < artists.length(); i++) {
                JSONObject artist = artists.getJSONObject(i);
                formatted.put(artist.getString("name"), artist.getInt("listeners"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyAllObservers();
    }

    public interface ModelObserver {

        public void notifyChange();
    }
}
