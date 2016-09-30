package com.mateus.popularmovies.main.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;
import com.mateus.popularmovies.main.adapters.AdapterListMovies;
import com.mateus.popularmovies.main.model.Movie;
import com.mateus.popularmovies.main.model.MovieResponse;
import com.mateus.popularmovies.main.rest.APIMovieDB;
import com.mateus.popularmovies.main.rest.ClientMovieDB;
import com.mateus.popularmovies.main.utils.Constants;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends MasterActivity {

    private List<Movie> mItens;
    private RecyclerView mRecyclerView;
    private AdapterListMovies mAdapter;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.listMovies);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new FetchDataMovie().execute();
    }

    private class FetchDataMovie extends AsyncTask<Void,Void,List<Movie>>{

        private String dataResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            APIMovieDB apiMovieDb = ClientMovieDB.getInstance().create(APIMovieDB.class);
            Call<MovieResponse> query = apiMovieDb.getPopularMovies(Constants.APP_ID_MOVIEDB,Constants.DEFAULT_LANGUAGE_MOVIEDB,"1");
            try {
                Response<MovieResponse> response = query.execute();
                mItens = response.body().results;
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return mItens;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);

            if (list!=null) {
                if (mAdapter==null) {
                    mAdapter = new AdapterListMovies(list,getBaseContext());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
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
}
