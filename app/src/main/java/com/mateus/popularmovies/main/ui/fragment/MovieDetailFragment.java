package com.mateus.popularmovies.main.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.model.Movie;
import com.mateus.popularmovies.main.model.MovieReviews;
import com.mateus.popularmovies.main.rest.APIMovieDB;
import com.mateus.popularmovies.main.rest.ClientMovieDB;
import com.mateus.popularmovies.main.ui.MovieDetailActivity;
import com.mateus.popularmovies.main.utils.Constants;
import com.mateus.popularmovies.main.utils.Utility;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mateuscarvalho on 01/12/16.
 */

public class MovieDetailFragment extends Fragment {

    private ImageView preview;
    private TextView name, overview, dateReleased,authorReview,contentReview;
    private RatingBar averageUser;
    private ImageButton btTrailler,btFavorite;
    private Movie movie;
    private static int DETAIL_FRAGMENT_POSITION_LOAD = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_move, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState==null) {
            preview = (ImageView) view.findViewById(R.id.imagePreview);
            name = (TextView) view.findViewById(R.id.title);
            overview = (TextView) view.findViewById(R.id.overview);
            btFavorite = (ImageButton) view.findViewById(R.id.btFavorite);
            dateReleased = (TextView) view.findViewById(R.id.dateReleased);
            averageUser = (RatingBar) view.findViewById(R.id.averageUser);
            btTrailler = (ImageButton) view.findViewById(R.id.btTrailler);
            contentReview = (TextView) view.findViewById(R.id.review_content);
            authorReview = (TextView) view.findViewById(R.id.author_review);

            Bundle arguments = getArguments();
            String id = arguments.getString(Constants.MOVIE_ID);

            if (arguments!=null){
                //query trailler
                FetchTrailler fetchTrailler = new FetchTrailler();
                fetchTrailler.execute(id);
            }

        }

        btTrailler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie!=null) {
                    watchYoutubeVideo(movie.getVideos().results.get(0).getKey());
                }
            }
        });


        btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie==null) {
                    return ;
                }

                String id = movie.getId();

                if(!Utility.isFavorited(getActivity(),id)) {
                    Utility.addFavoriteItem(getActivity(),id);
                    btFavorite.setSelected(true);
                } else {
                    Utility.removeFavoriteItem(getActivity(),id);
                    btFavorite.setSelected(false);
                }
            }
        });

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

                updateDataView(movie);
            }

            super.onPostExecute(aVoid);
        }
    }

    private void updateDataView(Movie movie){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movie.getDateRelease());

        String yearMovie = String.valueOf(calendar.get(Calendar.YEAR));

        name.setText(movie.getTitle());
        overview.setText(movie.getDescription());
        dateReleased.setText(yearMovie);

        //set image from post
        String pathImage = movie.getPathThumb();
        Picasso.with(getActivity()).load(Constants.API_BASE_IMAGES_MOVIEDB+pathImage).into(preview);

        //set average user
        float average = Float.valueOf(movie.getVoteAverage());
        averageUser.setRating(average);

        if (Utility.isFavorited(getActivity(),movie.getId())) {
            btFavorite.setSelected(true);
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
