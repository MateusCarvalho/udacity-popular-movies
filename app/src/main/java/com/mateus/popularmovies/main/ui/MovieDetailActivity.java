package com.mateus.popularmovies.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;
import com.mateus.popularmovies.main.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by mateus on 27/09/16.
 */
public class MovieDetailActivity extends MasterActivity {

    private ImageView preview;
    private TextView name, overview, dateReleased, averageUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_movie);

        preview = (ImageView) findViewById(R.id.imagePreview);
        name = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        dateReleased = (TextView) findViewById(R.id.dateReleased);
        averageUser = (TextView) findViewById(R.id.averageUser);

        if (getIntent()!=null){
            name.setText(getIntent().getStringExtra(Constants.MOVIE_TITLE));
            overview.setText(getIntent().getStringExtra(Constants.MOVIE_OVERVIEW));
            averageUser.setText(getIntent().getStringExtra(Constants.MOVIE_AVERAGE_USER)+"/10");
            dateReleased.setText(getIntent().getStringExtra(Constants.MOVIE_DATE_RELEASED));
            String pathImage = getIntent().getStringExtra(Constants.MOVIE_IMAGE_PATH);
            Picasso.with(getBaseContext()).load(Constants.API_BASE_IMAGES_MOVIEDB+pathImage).into(preview);
        }


    }

}
