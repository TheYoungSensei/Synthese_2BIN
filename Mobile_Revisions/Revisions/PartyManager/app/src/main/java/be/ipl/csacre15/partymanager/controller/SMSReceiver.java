package be.ipl.csacre15.partymanager.controller;

import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import be.ipl.csacre15.partymanager.Builder;
import be.ipl.csacre15.partymanager.R;
import be.ipl.csacre15.partymanager.model.Participant;
import be.ipl.csacre15.partymanager.model.ParticipantImpl;
import be.ipl.csacre15.partymanager.model.PartyModel;

/**
 * Created by sacre on 13-06-17.
 */
public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "YOLO";
    @Override
    public void onReceive(Context context, Intent intent) {
        PartyModel model = ((Builder) context.getApplicationContext()).getModel();
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        String message = "";
        String tel = "";
        for(int i = 0; i < pdus.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            message += messages[i].getMessageBody();
            tel = messages[i].getOriginatingAddress();
        }
        String[] decoded = message.split(";");
        if(decoded[0].equalsIgnoreCase("#laFete")) {
            if(decoded.length == 4) {
                Participant participant = new ParticipantImpl();
                participant.setLastname(decoded[1]);
                participant.setFirstname(decoded[2]);
                participant.setDrink(decoded[3]);
                participant.setTel(tel);
                model.addUtil(participant);
                return;
            }
        }
        return;
        /* Toast.makeText(context, "Petit coquin", Toast.LENGTH_LONG).show();*/
    }
}
