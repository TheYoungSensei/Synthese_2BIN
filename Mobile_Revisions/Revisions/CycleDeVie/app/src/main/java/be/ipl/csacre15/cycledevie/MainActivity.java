package be.ipl.csacre15.cycledevie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("CycleDeVie", "onCreate() - CycleDeVie");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("CycleDeVie", "onStart() - CycleDeVie");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("CycleDeVie", "onResume() - CycleDeVie");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("CycleDeVie", "onPause() - CycleDeVie");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("CycleDeVie", "onStop() - CycleDeVie");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("CycleDeVie", "onDestroy() - CycleDeVie");
    }
}
