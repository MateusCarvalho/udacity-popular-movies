package com.mateus.popularmovies.main.ui;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends MasterActivity {

    private RecyclerView mListview;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListview = (RecyclerView) findViewById(R.id.listMovies);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new FetchDataMovie().execute();
    }

    private class FetchDataMovie extends AsyncTask<Void,Void,String>{

        private String dataResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {

                final String PAGE  = "page";
                final String LANGUAGE_PARAM = "language";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(getString(R.string.api_popular_movie)).buildUpon()
                        .appendQueryParameter(LANGUAGE_PARAM,"pt-BR")
                        .appendQueryParameter(PAGE,"1")
                        .appendQueryParameter(APPID_PARAM,YOUR_APP_ID)
                        .build();

                //the url
                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                dataResponse = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            //try {
                //return getWeatherDataFromJson(forecastJsonStr,numDays);
                return "";
            //} catch (JSONException e) {
              //  e.printStackTrace();
            //}


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }
}
