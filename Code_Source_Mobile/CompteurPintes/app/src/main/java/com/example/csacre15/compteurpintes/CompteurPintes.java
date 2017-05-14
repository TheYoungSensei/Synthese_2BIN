package com.example.csacre15.compteurpintes;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class CompteurPintes extends AppCompatActivity {

    private int compteur;
    private int highscore;
    public static final String HIGHSCORE = "highscore";
    private static final int CODE_HIGHSCORE = 1;
    private boolean isMaximum;
    private int maximum;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestoreInstanceState(savedInstanceState);
        chargerLesPreferences();
        setContentView(R.layout.activity_compteur_pintes);
        affichageDescription();
        TextView highscore = (TextView) findViewById(R.id.highscore);
        highscore.setText("HighScore : " + this.highscore);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void chargerLesPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("mesPreferences", MODE_PRIVATE);
        this.highscore = preferences.getInt("highscore", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quit:
                quit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void quit(){
        this.finish();
    }

    public void unlimitedPower(View view) {
        this.compteur++;
        affichageDescription();

        switch (this.compteur) {
            case 1:
                changeTextDescription(getResources().getString(R.string.biere_1));
                break;
            case 5:
                changeTextDescription(getResources().getString(R.string.biere_5));
                break;
            case 8:
                changeTextDescription(getResources().getString(R.string.biere_8));
                break;
            case 10:
                changeTextDescription(getResources().getString(R.string.biere_10));
                break;
            case 12:
                changeTextDescription(getResources().getString(R.string.biere_12));
                break;
            case 15:
                changeTextDescription(getResources().getString(R.string.biere_15));
                break;
            case 18:
                changeTextDescription(getResources().getString(R.string.biere_18));
                break;
            case 24:
                changeTextDescription(getResources().getString(R.string.biere_24));
                break;
            case 30:
                changeTextDescription(getResources().getString(R.string.biere_30));
                break;
            case 50:
                changeTextDescription(getResources().getString(R.string.biere_50));
                break;
            default:
                changeTextDescription(getResources().getString(R.string.fagot));
                break;
        }

        if (this.highscore < this.compteur) {
            this.highscore = this.compteur;
            setHighscore();
            if(!this.isMaximum) {
                Intent intent = new Intent(this, Highscore.class);
                intent.putExtra(HIGHSCORE, this.highscore);
                startActivityForResult(intent, CODE_HIGHSCORE);
            }
        }

         if(this.isMaximum) {
            if(this.maximum < this.compteur) {
                Intent intent = new Intent(this, Panic.class);
                startActivity(intent);
            }
        }
    }

    private void setHighscore() {
        TextView highscore = (TextView) findViewById(R.id.highscore);
        highscore.setText("Nouveau Highscore");
    }

    private void affichageDescription() {
        TextView bouton = (TextView) findViewById(R.id.compteur);
        bouton.setText("" +this.compteur);
    }

    private void changeTextDescription(CharSequence message) {
        TextView description = (TextView) findViewById(R.id.introduction);
        description.setText(message);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CompteurPintes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.csacre15.compteurpintes/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        sauverLesPreferences();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CompteurPintes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.csacre15.compteurpintes/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void sauverLesPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("mesPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("highscore", this.highscore);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODE_HIGHSCORE) {
            this.maximum = Integer.valueOf(data.getIntExtra(Highscore.MAXIMUM, -1));
            if (this.maximum != -1) {
                this.maximum += this.highscore;
                isMaximum = true;
                setHighscore();
                this.compteur = this.highscore;
                affichageDescription();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceSate) {
        savedInstanceSate.putInt("compteur", this.compteur);
        savedInstanceSate.putInt("highscore", this.highscore);
        savedInstanceSate.putInt("maximum", this.maximum);
        savedInstanceSate.putBoolean("isMaximum", this.isMaximum);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceSate) {
        if(savedInstanceSate != null) {
            this.compteur = savedInstanceSate.getInt("compteur", 0);
            this.highscore = savedInstanceSate.getInt("highscore", 0);
            this.maximum = savedInstanceSate.getInt("maximum", -1);
            this.isMaximum = savedInstanceSate.getBoolean("isMaximum", false);
        }
    }
}

