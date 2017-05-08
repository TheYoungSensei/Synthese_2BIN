package com.example.christopher.pae_master_flow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ConnectionActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        button = (Button) findViewById(R.id.connect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection();
            }
        });
    }

    private void connection() {
        try {
            String password = ((EditText) findViewById(R.id.password)).getText().toString();
            String pseudo = ((EditText) findViewById(R.id.pseudo)).getText().toString();
            if (password == null || pseudo == null) {
                ((TextView) findViewById(R.id.notif)).setText("Informations Manquantes");
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("type", "");
            Map<String, String> json = new HashMap<String, String>();
            json.put("login", pseudo);
            json.put("password", password);
            map.put("json", new JSONObject(json).toString());
            String s = HTTPUtils.performPostCall("10.0.136.134:8060/Login", map);
            ((TextView) findViewById(R.id.notif)).setText("Connexion Réussie");
        } catch (HTTPUtils.HTTPException exception) {
            exception.printStackTrace();
            ((TextView) findViewById(R.id.notif)).setText("HTTPException");
        } catch (HTTPUtils.HTTPNetworkException exception) {
            exception.printStackTrace();
            ((TextView) findViewById(R.id.notif)).setText("HTTPNetworkException :" + exception.getCause().getMessage());
        }
    }


}
