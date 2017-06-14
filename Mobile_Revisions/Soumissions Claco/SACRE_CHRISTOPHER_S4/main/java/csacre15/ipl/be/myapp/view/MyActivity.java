package csacre15.ipl.be.myapp.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import csacre15.ipl.be.myapp.Builder;
import csacre15.ipl.be.myapp.dao.LaFeteContract;
import csacre15.ipl.be.myapp.model.MyModel;
import csacre15.ipl.be.myapp.R;

public class MyActivity extends AppCompatActivity implements MyModel.MainModelChangeObserver {

    private static final int MY_PERMISSION_READ_SMS = 1;
    private static MyModel mainModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_READ_SMS);
            }
        }
        mainModel = ((Builder) getApplication()).getModel();
        setTexte();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainModel.registerObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainModel.unregisterObserver(this);
    }

    @Override
    public void notifyChange() {
        if(mainModel.getLastUtil() == 1) {
            Toast.makeText(getApplicationContext(), "Vous êtes le " + String.valueOf(mainModel.getLastUtil()) + "er participant", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Vous êtes le " + String.valueOf(mainModel.getLastUtil()) + "ème participant", Toast.LENGTH_LONG).show();
        }
        setTexte();

    }

    private void setTexte() {
        Cursor cursor = mainModel.getCursor10Last();
        String[] fromColumns = {LaFeteContract.TABLE_UTIL_PRENOM, LaFeteContract.TABLE_UTIL_NOM, LaFeteContract.TABLE_UTIL_TEL};
        int[] toViews = {R.id.person_firstname, R.id.person_name, R.id.person_number};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_main, cursor, fromColumns, toViews, 5);
        ListView listView = (ListView) findViewById(R.id.person_name_and_number);
        listView.setAdapter(adapter);
    }
}
