package student.ipl.be.ktirche15.compteurpintes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class CompteurPintes extends AppCompatActivity {

    private static int compteur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compteur_pintes);
    }

    public void unlimitedPower(View view){
        this.compteur++;
        CharSequence message = "+" + this.compteur;
        TextView bouton = (TextView) findViewById(R.id.compteur);
        bouton.setText(message);

    }
}
