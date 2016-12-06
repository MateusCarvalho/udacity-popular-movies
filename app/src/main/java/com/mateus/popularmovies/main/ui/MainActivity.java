package com.mateus.popularmovies.main.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;
import com.mateus.popularmovies.main.adapters.AdapterListMovies;
import com.mateus.popularmovies.main.model.Movie;
import com.mateus.popularmovies.main.rest.APIMovieDB;
import com.mateus.popularmovies.main.rest.ClientMovieDB;
import com.mateus.popularmovies.main.utils.Constants;
import com.mateus.popularmovies.main.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends MasterActivity {

    private List<Movie> mItens;
    private RecyclerView mRecyclerView;
    private AdapterListMovies mAdapter;
    private final String TAG = "MainActivity";
    public static boolean mTwoPane=false;
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "fragment_movie_details";
    private  SharedPreferences sharedPreferences;
    private boolean showOnlyFavorites=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.movie_detail_container) != null;
        mRecyclerView = (RecyclerView) findViewById(R.id.listMovies);

        if (mTwoPane) {
            if (savedInstanceState == null) {
                changeFragment(new Fragment());
            }
        }


    }

    public boolean isOnlyShowFavorites(){
        return this.showOnlyFavorites;
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String order = sharedPreferences.getString(getString(R.string.pref_order_key),getString(R.string.pref_order_default));
        showOnlyFavorites = sharedPreferences.getBoolean(getString(R.string.favorites_enabled),false);

        new FetchDataMovie().execute(order);
    }

    private class FetchDataMovie extends AsyncTask<String,Void,List<Movie>>{

        private String dataResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            APIMovieDB apiMovieDb = ClientMovieDB.getInstance().create(APIMovieDB.class);
            Call<Movie.MovieResponse> query = null;

            if (params[0].equals(getString(R.string.pref_order_default))) {
              query  = apiMovieDb.getPopularMovies(Constants.APP_ID_MOVIEDB, Constants.DEFAULT_LANGUAGE_MOVIEDB, "1");
            } else {
                query = apiMovieDb.getTopRatedMovies(Constants.APP_ID_MOVIEDB, Constants.DEFAULT_LANGUAGE_MOVIEDB, "1");
            }
            try {
                Response<Movie.MovieResponse> response = query.execute();
                mItens = response.body().results;
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return mItens;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);
            if(isOnlyShowFavorites()){

                List<Movie> newList = new ArrayList<Movie>();

                for (Movie temp :list){
                    if (Utility.isFavorited(MainActivity.this,temp.getId())) {
                        newList.add(temp);
                    }
                }

                list = newList;
            }

            if (list!=null) {
                if (mAdapter==null) {
                    mAdapter = new AdapterListMovies(list,MainActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    mAdapter.clearData();
                    mAdapter.setData(list);
                }
            }
        }


    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment, MOVIE_DETAILS_FRAGMENT_TAG)
                .commit();
    }
}
