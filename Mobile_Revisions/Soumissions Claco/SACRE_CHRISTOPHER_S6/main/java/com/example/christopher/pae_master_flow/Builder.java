package com.example.christopher.pae_master_flow;

import android.app.Application;

/**
 * Created by csacre15 on 11/05/2017.
 */
public class Builder extends Application {

    private BusinessModel model;
    @Override
    public void onCreate() {
        super.onCreate();
        this.model = new BusinessModel();
    }

    public BusinessModel getModel() {
        return this.model;
    }
}
