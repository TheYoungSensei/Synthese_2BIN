package be.ipl.csacre15.lastfm;

import android.app.Application;

import be.ipl.csacre15.lastfm.dal.LastAdaptater;
import be.ipl.csacre15.lastfm.model.LastModel;

/**
 * Created by sacre on 13-06-17.
 */
public class Builder extends Application{

    private LastModel model;

    @Override
    public void onCreate() {
        super.onCreate();
        this.model = new LastModel();
        LastAdaptater adaptater = new LastAdaptater(getApplicationContext());
        adaptater.open();
        model.setAdapteur(adaptater);
    }

    public LastModel getModel() {
        return model;
    }
}
