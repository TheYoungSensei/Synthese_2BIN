package csacre15.ipl.be.myapp;

import android.app.Application;

import csacre15.ipl.be.myapp.dao.LaFeteAdaptater;
import csacre15.ipl.be.myapp.dao.LaFeteAdaptater;
import csacre15.ipl.be.myapp.model.MyModel;

/**
 * Created by csacre15 on 22/03/2017.
 */
public class Builder extends Application {

    private MyModel mainModel;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mainModel = new MyModel();
        LaFeteAdaptater mainAdaptateur = new LaFeteAdaptater(getApplicationContext());
        mainAdaptateur.open();
        mainModel.setAdaptateur(mainAdaptateur);
    }

    public MyModel getModel() {
        return this.mainModel;
    }
}
