package com.mateus.popularmovies.main.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;
import com.mateus.popularmovies.main.ui.fragment.MovieDetailFragment;
import com.mateus.popularmovies.main.utils.Constants;


/**
 * Created by mateus on 27/09/16.
 */
public class MovieDetailActivity extends MasterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_movie);

        if (savedInstanceState==null) {
            if (getIntent().hasExtra(Constants.MOVIE_ID)) {

                String movieID = getIntent().getStringExtra(Constants.MOVIE_ID);

                Bundle args = new Bundle();
                args.putString(Constants.MOVIE_ID, movieID);

                MovieDetailFragment fragment = new MovieDetailFragment();
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, fragment)
                        .commit();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        changeUpIcon();
    }
}
