package be.ipl.csacre15.lastfragment;

import android.app.Application;

import be.ipl.csacre15.lastfragment.model.LastModel;

/**
 * Created by sacre on 13-06-17.
 */
public class Builder extends Application{

    private LastModel model;

    public Builder() {
        this.model = new LastModel();
    }

    public LastModel getModel() {
        return this.model;
    }
}
