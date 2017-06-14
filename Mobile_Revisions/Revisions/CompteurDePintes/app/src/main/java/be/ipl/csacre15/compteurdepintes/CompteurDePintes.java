package be.ipl.csacre15.compteurdepintes;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CompteurDePintes extends AppCompatActivity {

    public static final String PREFERENCES = "pintesPreferences";
    public static final String HIGHSCORE = "highscore";
    public static final String COMPTEUR = "compteur";
    private static final int CODE_HIGHSCORE = 1;
    public static final String LIMITE = "limite";
    public static final String HIGHSCORE_ACTIVITY = "highscoreActivity";
    Button bouton;
    TextView compteur;
    TextView notification;
    int compteurInt;
    private int highscore;
    private int limite;
    private boolean highscoreActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 1);
            }
        }
        setContentView(R.layout.activity_main);
        onRestoreInstanceState(savedInstanceState);
        bouton = (Button) findViewById(R.id.bouton);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uneDePlus();
            }
        });
        compteur = (TextView) findViewById(R.id.compteur);
        compteur.setText(String.valueOf(compteurInt));
        notification = (TextView) findViewById(R.id.notification);
        SharedPreferences preferences =this.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        this.highscore = preferences.getInt(HIGHSCORE, 0);
        setNotification();
    }

    private void uneDePlus() {
        compteurInt++;
        setNotification();
        compteur.setText(String.valueOf(compteurInt));
    }

    private void setNotification() {
        switch(compteurInt) {
            case 1:
                notification.setText(R.string.notif1);
                break;
            case 5:
                notification.setText(R.string.notif5);
                break;
            case 8:
                notification.setText(R.string.notif8);
                break;
            case 10:
                notification.setText(R.string.notif10);
                break;
            case 12:
                notification.setText(R.string.notif12);
                break;
            case 15:
                notification.setText(R.string.notif15);
                break;
            case 18:
                notification.setText(R.string.notif18);
                break;
            case 24:
                notification.setText(R.string.notif24);
                break;
            case 30:
                notification.setText(R.string.notif30);
                break;
            case 50:
                notification.setText(R.string.notif50);
                break;
            default :
                break;
        }
        if(compteurInt > highscore) {
            highscore = compteurInt;
            notification.setText(R.string.newHighScore);
            if(!highscoreActivity) {
                Intent intent = new Intent(this, Highscore.class);
                intent.putExtra(HIGHSCORE, this.highscore);
                startActivityForResult(intent, CODE_HIGHSCORE);
            }
        }
        if(highscoreActivity) {
            if(compteurInt >= limite) {
                Intent intent = new Intent(this, Panic.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODE_HIGHSCORE) {
            this.limite = data.getIntExtra(Highscore.LIMITE, 0);
            this.limite += highscore;
            this.highscoreActivity = true;
            setNotification();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quit:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(HIGHSCORE, this.highscore);
        editor.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(COMPTEUR, this.compteurInt);
        savedInstanceState.putInt(HIGHSCORE, this.highscore);
        savedInstanceState.putInt(LIMITE, this.limite);
        savedInstanceState.putBoolean(HIGHSCORE_ACTIVITY, this.highscoreActivity);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            this.compteurInt = savedInstanceState.getInt(COMPTEUR, 0);
            this.highscore = savedInstanceState.getInt(HIGHSCORE, 0);
            this.limite = savedInstanceState.getInt(LIMITE, 0);
            this.highscoreActivity = savedInstanceState.getBoolean(HIGHSCORE_ACTIVITY);
        }
    }
}
