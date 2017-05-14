package csacre15.ipl.be.myapp.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import csacre15.ipl.be.myapp.Builder;
import csacre15.ipl.be.myapp.model.MyModel;

/**
 * Created by csacre15 on 22/03/2017.
 */
public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        MyModel mainModel = ((Builder) context.getApplicationContext()).getModel();
        Bundle bundle = intent.getExtras();
        Object[] pdus =(Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        String message = "";
        String tel = "";
        for(int i =0; i < pdus.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            message += messages[i].getMessageBody();
            tel = messages[i].getOriginatingAddress();
        }
        String[] decoded = message.split(";");
        if(decoded[0].equalsIgnoreCase("#laFete")){
            if(decoded.length != 4) {
                Toast.makeText(context, "Petit Coquin", Toast.LENGTH_LONG).show();
            } else {
                mainModel.ajouterUtil(decoded[1], decoded[2], decoded[3], tel);
            }
        } else{
            Toast.makeText(context, "Petit Coquin", Toast.LENGTH_LONG).show();
        }
    }
}
