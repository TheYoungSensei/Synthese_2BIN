package be.ipl.csacre15.calculatrice;

import android.app.Application;

/**
 * Created by csacre15 on 9/03/2017.
 */
public class Builder extends Application {

    CalcModel model;

    @Override
    public void onCreate() {
        super.onCreate();
        model = new CalcModel();
    }

    public CalcModel getModel() {
        return model;
    }
}
