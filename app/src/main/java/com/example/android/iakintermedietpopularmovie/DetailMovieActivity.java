package com.example.android.iakintermedietpopularmovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.iakintermedietpopularmovie.adapter.TrailerAdapter;
import com.example.android.iakintermedietpopularmovie.data.MovieContract;
import com.example.android.iakintermedietpopularmovie.model.Movie;
import com.example.android.iakintermedietpopularmovie.model.Trailer;
import com.example.android.iakintermedietpopularmovie.utils.NetworkUtils;
import com.example.android.iakintermedietpopularmovie.utils.RecyclerViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements RecyclerViewClickListener{

    TextView mMovieTitle;
    ImageView mMovieImage;
    TextView mMovieYear;
    TextView mMovieRating;
    TextView mMovieOverview;
    RecyclerView mMovieTrailer;


    //TextView mMovieFavoriteBotton;

    public static final String MOVIE_INTENT = "movie_intent";

    private Movie movie;
    private Trailer trailer;

    private TrailerAdapter trailerAdapter;
    private List<Trailer> trailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mMovieImage = (ImageView) findViewById(R.id.movie_image);
        mMovieYear = (TextView) findViewById(R.id.movie_year);
        mMovieRating = (TextView) findViewById(R.id.movie_rating);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieTrailer = (RecyclerView) findViewById(R.id.rv_trailers);


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

        trailerAdapter = new TrailerAdapter(this, trailerList, this);

        LinearLayoutManager trailerLayout = new LinearLayoutManager(this);
        mMovieTrailer.setLayoutManager(trailerLayout);
        mMovieTrailer.setAdapter(trailerAdapter);

        new GetTrailerTask();
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

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        trailer = trailerList.get(position);
        intent.setData(Uri.parse("http://www.youtube.com/watch?v="+trailer.getKey()));

    }

    public class GetTrailerTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            URL urlTrailer = NetworkUtils.buildMovieVideoUrl(urls[0]);
            new GetTrailerTask().execute(String.valueOf(urlTrailer));
            String result= null;

            try {
                result = NetworkUtils.getResponseFromHttpUrl(urlTrailer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);

            Log.e("json_object", strings);
            try {
                JSONObject jsonObject = new JSONObject(strings);
                JSONArray results = jsonObject.getJSONArray("results");

                for(int i=0; i<results.length(); i++){
                    trailerList.add(new Trailer(
                            results.getJSONObject(i).getString("id"),
                            results.getJSONObject(i).getString("key"),
                            results.getJSONObject(i).getString("name"),
                            results.getJSONObject(i).getString("site"),
                            results.getJSONObject(i).getInt("size"),
                            results.getJSONObject(i).getString("type"))
                    );
                }

                trailerAdapter.notifyDataSetChanged();

                Log.e("json_array trailer", strings);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
