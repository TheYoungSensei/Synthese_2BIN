package be.ipl.csacre15.lastfm.model;

import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import be.ipl.csacre15.lastfm.dal.LastAdaptater;

/**
 * Created by sacre on 13-06-17.
 */
public class LastModel {

    private Set<LastObserver> observers = new HashSet<LastObserver>();
    private LastAdaptater adaptater;

    public Cursor getCursor() {
        return cursor;
    }

    private Cursor cursor;

    public void setAdapteur(LastAdaptater adaptater) {
        this.adaptater = adaptater;
    }

    public void regiterObserver(LastObserver obs) {
        this.observers.add(obs);
    }

    public void unregisterObserver(LastObserver obs) {
        this.observers.add(obs);
    }

    public void notifyAllObservers() {
        for(LastObserver obs : observers) {
            obs.notifyChange();
        }
    }

    public LastTask newTask() {
        return new LastTask();
    }

    public void selectAll() {
        adaptater.selectAll();
        cursor = adaptater.getCursor();
        notifyAllObservers();
    }

    public interface LastObserver {
        public void notifyChange();
    }

    public class LastTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadUrl(new URL(params[0]));
            } catch (IOException e) {
               return "Unable to retrieve Web Page, URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            adaptater.selectAll();
            try {
                JSONArray artists = new JSONObject(s).getJSONObject("topartists").getJSONArray("artist");
                for(int i = 0; i < artists.length(); i++) {
                    Artist artistDto = new ArtistImpl();
                    JSONObject artist = artists.getJSONObject(i);
                    artistDto.setName(artist.getString("name"));
                    artistDto.setNbListeners(artist.getInt("listeners"));
                    if(!adaptater.existe(artistDto)) {
                        adaptater.addArtist(artistDto);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            selectAll();
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
                int rCode = connection.getResponseCode();
                if(rCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code : " + rCode);
                }
                stream =connection.getInputStream();
                if(stream != null) {
                    result = readStream(stream, 5000000);
                }
            } finally {
                if(stream != null) {
                    stream.close();
                }
                if(connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        private String readStream(InputStream stream, int maxLength) throws IOException {
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
