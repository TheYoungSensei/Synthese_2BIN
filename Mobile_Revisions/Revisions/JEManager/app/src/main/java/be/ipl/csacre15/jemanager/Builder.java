package be.ipl.csacre15.jemanager;

import android.app.Application;

import be.ipl.csacre15.jemanager.businessDays.BusinessModel;

/**
 * Created by sacre on 14-06-17.
 */
public class Builder extends Application{

    private BusinessModel model;

    public Builder() {
        this.model = new BusinessModel();
    }

    public BusinessModel getModel() {
        return model;
    }
}
