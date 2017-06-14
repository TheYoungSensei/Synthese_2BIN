package com.example.christopher.lastfm.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Artist {

    private Set<ModelObserver> observers = new HashSet<ModelObserver>();
    private List<String> formatted;

    public static final List<MyItem> ITEMS = new ArrayList<>();

    public static final Map<String, MyItem> ITEM_MAP = new HashMap<String, MyItem>();

    public List<String> getResult() {
        return Collections.unmodifiableList(formatted);
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

    public interface ModelObserver {

        public void notifyChange();
    }

    public static void addItem(MyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MyItem createDummyItem(int position) {
        return new MyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MyItem {
        public final String id;
        public final String content;
        public final String details;

        public MyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
