package com.example.christopher.lastfm;

import android.app.Application;

/**
 * Created by Christopher on 20-04-17.
 */
public class Builder  extends Application {

    LastModel model;

    @Override
    public void onCreate() {
        super.onCreate();
        model = new LastModel();
    }

    public LastModel getModel() {
        return model;
    }
}

