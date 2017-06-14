package be.ipl.csacre15.partymanager.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import be.ipl.csacre15.partymanager.Builder;
import be.ipl.csacre15.partymanager.R;
import be.ipl.csacre15.partymanager.dal.PartyContract;
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
        setTexte();
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.registerObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        model.unregisterObserver(this);
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

    private void setTexte() {
        Cursor cursor = model.getCursor10Last();
        String[] fromColumns = {PartyContract.UTIL_FIRSTNAME,
                PartyContract.UTIL_NAME, PartyContract.UTIL_TEL};
        int[] toViews = {R.id.person_firstname, R.id.person_name, R.id.person_number};
        SimpleCursorAdapter adaptater = new SimpleCursorAdapter(this, R.layout.activity_party_view, cursor, fromColumns, toViews, 5);
        ListView listView =((ListView) findViewById(R.id.person_name_and_number));
        listView.setAdapter(adaptater);
    }
}
