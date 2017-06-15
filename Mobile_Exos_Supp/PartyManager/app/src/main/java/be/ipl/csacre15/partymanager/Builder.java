package be.ipl.csacre15.partymanager;

import android.app.Application;

import be.ipl.csacre15.partymanager.dal.PartyAdaptater;
import be.ipl.csacre15.partymanager.model.PartyModel;

/**
 * Created by sacre on 13-06-17.
 */
public class Builder extends Application{

    private PartyModel model;

    @Override
    public void onCreate() {
        super.onCreate();
        model = new PartyModel();
        PartyAdaptater adaptater = new PartyAdaptater(getApplicationContext());
        adaptater.open();
        model.setAdapteur(adaptater);
    }

    public PartyModel getModel() {
        return model;
    }
}
