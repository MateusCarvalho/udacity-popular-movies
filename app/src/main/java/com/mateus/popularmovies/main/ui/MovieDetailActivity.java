package com.mateus.popularmovies.main.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;
import com.mateus.popularmovies.main.model.Movie;
import com.mateus.popularmovies.main.model.MovieReviews;
import com.mateus.popularmovies.main.rest.APIMovieDB;
import com.mateus.popularmovies.main.rest.ClientMovieDB;
import com.mateus.popularmovies.main.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mateus on 27/09/16.
 */
public class MovieDetailActivity extends MasterActivity {

    private ImageView preview;
    private TextView name, overview, dateReleased,authorReview,contentReview;
    private RatingBar averageUser;
    private ImageButton btTrailler;
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_movie);

        preview = (ImageView) findViewById(R.id.imagePreview);
        name = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        dateReleased = (TextView) findViewById(R.id.dateReleased);
        averageUser = (RatingBar) findViewById(R.id.averageUser);
        btTrailler = (ImageButton) findViewById(R.id.btTrailler);
        contentReview = (TextView) findViewById(R.id.review_content);
        authorReview = (TextView) findViewById(R.id.author_review);

        if (getIntent()!=null){

            String id = getIntent().getStringExtra(Constants.MOVIE_ID);

            //query trailler
            FetchTrailler fetchTrailler = new FetchTrailler();
            fetchTrailler.execute(id);

            name.setText(getIntent().getStringExtra(Constants.MOVIE_TITLE));
            overview.setText(getIntent().getStringExtra(Constants.MOVIE_OVERVIEW));
            dateReleased.setText(getIntent().getStringExtra(Constants.MOVIE_DATE_RELEASED));

            //set image from post
            String pathImage = getIntent().getStringExtra(Constants.MOVIE_IMAGE_PATH);
            Picasso.with(getBaseContext()).load(Constants.API_BASE_IMAGES_MOVIEDB+pathImage).into(preview);

            //set average user
            float average = Float.valueOf(getIntent().getStringExtra(Constants.MOVIE_AVERAGE_USER));
            averageUser.setRating(average);
        }


    }



    private class FetchTrailler extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            APIMovieDB apiMovieDb = ClientMovieDB.getInstance().create(APIMovieDB.class);
            Call<Movie> query = null;

            query = apiMovieDb.getMovie(params[0],Constants.APP_ID_MOVIEDB,"videos,reviews");

            try {
                Response<Movie> response = query.execute();
                movie = response.body();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (movie!=null) {
                //show bt trailler
                btTrailler.setVisibility(View.VISIBLE);


                //set review
                if (movie.getReviews().reviews!=null && movie.getReviews().reviews.size()>1) {
                    MovieReviews temp = movie.getReviews().reviews.get(0);
                    authorReview.setText(temp.getAuthor());
                    contentReview.setText(temp.getContent());
                }
            }

            super.onPostExecute(aVoid);
        }
    }


    public void onClickOpenTrailler(View view) {

        if (movie!=null) {
            watchYoutubeVideo(movie.getVideos().results.get(0).getKey());
        }
    }

    private void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

}
