package com.example.android.iakintermedietpopularmovie;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.iakintermedietpopularmovie.data.MovieContract;
import com.example.android.iakintermedietpopularmovie.model.Movie;
import com.example.android.iakintermedietpopularmovie.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    TextView mMovieTitle;
    ImageView mMovieImage;
    TextView mMovieYear;
    TextView mMovieRating;
    TextView mMovieOverview;

    //TextView mMovieFavoriteBotton;

    public static final String MOVIE_INTENT = "movie_intent";

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mMovieImage = (ImageView) findViewById(R.id.movie_image);
        mMovieYear = (TextView) findViewById(R.id.movie_year);
        mMovieRating = (TextView) findViewById(R.id.movie_rating);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);


        movie = getIntent().getParcelableExtra(MOVIE_INTENT);

        //title
        mMovieTitle.setText(movie.getTitle() );

        //gambar
        URL poster_url = NetworkUtils.buildMoviePosterUrl(movie.getPosterPath());
        Glide.with(this)
                .load(poster_url)
                .into(mMovieImage);

        //year
        mMovieYear.setText(movie.getReleaseDate());

        //rating
        mMovieRating.setText(String.valueOf(movie.getVoteAverage()));

        //overview
        mMovieOverview.setText(movie.getOverview());

    } 


    public void save_movie(View v){
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieList.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieList.COLUMN_PATH, movie.getPosterPath());
        contentValues.put(MovieContract.MovieList.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieList.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MovieList.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(MovieContract.MovieList.CONTENT_URI, contentValues);

        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
