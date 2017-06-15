package be.ipl.csacre15.lastfm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.ipl.csacre15.lastfm.dal.LastContract;
import be.ipl.csacre15.lastfm.model.LastModel;

public class LastView extends AppCompatActivity implements LastModel.LastObserver{

    private LastModel model;
    public static final String URL = "http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=belgium&api_key=32ef5df0e36797b605e205529058f3b8&format=json&limit=";
    Button button;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_view);
        button = (Button) findViewById(R.id.query);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        });
        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 1);
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_NETWORK_STATE}, 2);
            }
        }
        model = ((Builder) getApplication()).getModel();
    }

    private void refresh() {
        model.selectAll();
    }

    private void click() {
        EditText editText = (EditText) findViewById(R.id.input_query);
        int max = 1;
        if(!editText.getText().toString().equalsIgnoreCase("")) {
            max = Integer.valueOf(editText.getText().toString());
        }

        //Util.showProgress(true, (View) findViewById(R.id.print), (View) findViewById(R.id.login_progress), getApplicationContext());
        model.newTask().execute(URL + max);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMng = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
        model.regiterObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        model.unregisterObserver(this);
    }

    @Override
    public void notifyChange() {
        Cursor cursor = model.getCursor();
        String[] fromColumns = {LastContract.ARTIST_NAME, LastContract.NB_LISTENERS};
        int[] toViews = {R.id.artist_name, R.id.nb_listeners};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_view, cursor, fromColumns, toViews, 5);
        ListView listView = ((ListView) findViewById(R.id.print));
        listView.setAdapter(adapter);
        //Util.showProgress(false, (View) findViewById(R.id.print), (View) findViewById(R.id.login_progress), getApplicationContext());
    }


}
