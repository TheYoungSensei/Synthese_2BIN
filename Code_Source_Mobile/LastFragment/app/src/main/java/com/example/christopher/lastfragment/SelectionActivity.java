package com.example.christopher.lastfragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionActivity extends AppCompatActivity {

    private static final int MAX = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()) {
                //fetch data
                LastFMTask task = new LastFMTask();
                task.execute("http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=belgium&api_key=32ef5df0e36797b605e205529058f3b8&format=json&limit=" + MAX);
            }
        }
        /*
        //Code Remplacement Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new Fragment());
        transaction.addToBackStack(null);
        transaction.commit();
        */
    }


    public void setListItem(List<String> list){
        ArrayAdapter<String> ad = new ArrayAdapter<String>(
                this.getApplicationContext(), android.R.layout.simple_expandable_list_item_1, list);
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
            this.getDownload(s);
        }

        private void getDownload(String s) {

            List<String> formatted = new ArrayList<String>();
            try {
                JSONArray artists = new JSONObject(s).getJSONObject("topartists").getJSONArray("artist");
                for(int i = 0; i < artists.length(); i++) {
                    JSONObject artist = artists.getJSONObject(i);
                    formatted.add(artist.getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SelectionFragment firstFragment = new SelectionFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
            setListItem(formatted);
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
