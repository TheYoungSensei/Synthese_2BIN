package be.ipl.csacre15.compteurdepintes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Highscore extends AppCompatActivity {

    private int highscore;
    private Button bouton;
    public static final String LIMITE = "limite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        Intent intent = getIntent();
        this.highscore = intent.getIntExtra(CompteurDePintes.HIGHSCORE, 0);
        TextView view = (TextView) findViewById(R.id.score);
        view.setText("Nouvel Highscore : " + highscore);
        bouton = (Button) findViewById(R.id.maxButton);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continuer();
            }
        });
    }

    private void continuer() {
        EditText view = (EditText) findViewById(R.id.limite);
        String limite = view.getText().toString();
        if(!limite.equals("")) {
            int limiteInt = Integer.valueOf(limite);
            Intent intent = new Intent(this, CompteurDePintes.class);
            intent.putExtra(LIMITE, limiteInt);
            setResult(AppCompatActivity.RESULT_OK, intent);
            finish();
        }
    }
}
