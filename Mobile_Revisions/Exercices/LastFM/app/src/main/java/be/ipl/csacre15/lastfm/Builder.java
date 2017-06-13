package be.ipl.csacre15.lastfm;

import android.app.Application;

/**
 * Created by sacre on 13-06-17.
 */
public class Builder extends Application{

    private LastModel model;

    public Builder() {
        this.model = new LastModel();
    }

    public LastModel getModel() {
        return model;
    }
}
