package be.ipl.csacre15.partymanager.view;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import be.ipl.csacre15.partymanager.Builder;
import be.ipl.csacre15.partymanager.R;
import be.ipl.csacre15.partymanager.dal.PartyContract;
import be.ipl.csacre15.partymanager.model.Participant;
import be.ipl.csacre15.partymanager.model.ParticipantImpl;
import be.ipl.csacre15.partymanager.model.PartyModel;

public class PartyView extends AppCompatActivity implements PartyModel.PartyObserver {

    private static final int PERMISSION_READ_SMS = 1;
    private PartyModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_view);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, PERMISSION_READ_SMS);
            }
        }
        model = ((Builder) getApplication()).getModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.registerObserver(this);
        chargerLesPreferences();
        setTexte();
    }

    @Override
    protected void onPause() {
        super.onPause();
        model.unregisterObserver(this);
        sauverLesPreferences();
    }

    @Override
    public void notifyChange() {
        /* Gestion des Toasters : */
        /*if(model.getLastUtil() == 1) {
            Toast.makeText(getApplicationContext(), "Vous êtes le " + String.valueOf(mainModel.getLastUtil()) + "er participant", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Vous êtes le " + String.valueOf(mainModel.getLastUtil()) + "ème participant", Toast.LENGTH_LONG).show();
        }*/
        setTexte();
    }


    private void sauverLesPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("mesPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> strings = new HashSet<String>();
        for(Participant participant : model.participants()) {
           strings.add(participant.getFirstname() + ";" + participant.getLastName() + ";" + participant.getTel() + ";" + participant.getDrink());
        }
        editor.putStringSet("mesParticipants", strings);
        editor.commit();
    }

    private void chargerLesPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("mesPreferences", MODE_PRIVATE);
        Set<String> preferencesSet = preferences.getStringSet("mesParticipants", new HashSet<String>());
        for(String s : preferencesSet){
            String[] decoded = s.split(";");
            Participant participant = new ParticipantImpl();
            participant.setFirstname(decoded[0]);
            participant.setLastname(decoded[1]);
            participant.setTel(decoded[2]);
            participant.setDrink(decoded[3]);
            model.addUtil(participant);
        }
    }

    private void setTexte() {
        String [] participants = model.findLast10();
        ArrayAdapter<String> ad = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_expandable_list_item_1, participants
        );
        ListView v = (ListView) findViewById(R.id.person_name_and_number);
        v.setAdapter(ad);
    }
}
