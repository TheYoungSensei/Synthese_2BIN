package com.oui.cycledevie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Création", "onCreate() - CycleDeVie");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Redémarrage", "onRestart() - CycleDeVie");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Démarrage", "onStart() - CycleDeVie");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lancement", "onResume() - CycleDeVie");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("En Pause", "onPause() - CycleDeVie");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop", "onStop() - CycleDeVie");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Détruit", "onDestroy() - CycleDeVie");
    }
}
