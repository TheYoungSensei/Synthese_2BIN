package com.example.christopher.lastfm;

import android.app.Application;

import com.example.christopher.lastfm.model.Artist;

/**
 * Created by Christopher on 20-04-17.
 */
public class Builder extends Application {

    Artist model;

    @Override
    public void onCreate() {
        super.onCreate();
        model = new Artist();
    }

    public Artist getModel() {
        return model;
    }
}

