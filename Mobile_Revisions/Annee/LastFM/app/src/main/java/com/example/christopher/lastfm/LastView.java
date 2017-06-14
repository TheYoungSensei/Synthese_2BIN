package com.example.christopher.lastfm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LastView extends AppCompatActivity implements LastModel.ModelObserver {

    LastFMTask task;
    LastModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_view);
        Button button = (Button) findViewById(R.id.query);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myClickHandler();
            }
        });
        model = ((Builder) getApplication()).getModel();
        model.registerObserver(this);
    }

    public void myClickHandler() {

        EditText edit = (EditText) findViewById(R.id.input_query);
        int max = 1;
        if(!edit.getText().toString().equals("")) {
            max = Integer.valueOf(edit.getText().toString());
        }
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        LastFMTask task = new LastFMTask();
        if(networkInfo != null && networkInfo.isConnected()) {
            //fetch data
            task.execute("http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=belgium&api_key=32ef5df0e36797b605e205529058f3b8&format=json&limit=" + max);
        } else {
            ((TextView) findViewById(R.id.notif)).setText("You need to be connected");
        }
    }

    @Override
    public void notifyChange() {
        Map<String, Integer> map = model.getResult();
        List<String> result = new ArrayList<String>();
        for(String s : map.keySet()) {
            result.add(s + " "+ map.get(s));
        }
        ArrayAdapter<String> ad = new ArrayAdapter<String>(
                this.getApplicationContext(), android.R.layout.simple_expandable_list_item_1, result);
        ListView v = (ListView) findViewById(R.id.print);
        v.setAdapter(ad);
    }

    public class LastFMTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadUrl(new URL(params[0]));
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            Log.v("YOLO", "Attention error");
            //((TextView) findViewById(R.id.notif)).setText(s);
            //GO TO MODEL
            model.getDownload(s);
        }

        private String downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code : " + responseCode);
                }
                stream = connection.getInputStream();
                if (stream != null) {
                    result = readStream(stream, 5000000);
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        private String readStream(InputStream stream, int maxLength) throws IOException{
            String result = null;
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[maxLength];
            int numChars = 0;
            int readSize = 0;
            while(numChars < maxLength && readSize != -1) {
                numChars += readSize;
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if(numChars != -1) {
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
            }
            return result;
        }
    }

}
