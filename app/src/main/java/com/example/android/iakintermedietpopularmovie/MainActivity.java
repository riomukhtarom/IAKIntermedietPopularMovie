package com.example.android.iakintermedietpopularmovie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.iakintermedietpopularmovie.adapter.MovieAdapter;
import com.example.android.iakintermedietpopularmovie.model.Movie;
import com.example.android.iakintermedietpopularmovie.utils.NetworkUtils;
import com.example.android.iakintermedietpopularmovie.utils.RecyclerViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener{

    @BindView(R.id.rv_movie) RecyclerView mMovieView;
    @BindView(R.id.pb_loading) ProgressBar mLoadingView;

    private MovieAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MovieAdapter(this, movieList, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMovieView.setLayoutManager(layoutManager);
        mMovieView.setAdapter(adapter);

        loadData();
    }

    private void loadData(){
        URL url = NetworkUtils.buildUrl("popular");
        new GetDataTask().execute(url);
    }

    @Override
    public void onItemClicked(int position) {
        //Toast.makeText(this, "position"+position, Toast.LENGTH_SHORT).show();

        Movie movie = movieList.get(position);

        Intent intent = new Intent(this , DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.MOVIE_INTENT, movie);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = this;
        int menuIntemWasSelected = item.getItemId();
        switch (menuIntemWasSelected){
            case R.id.favorite_menu:
                //favorite
                String messageFavorite = "Favorite";
                Toast.makeText(context, messageFavorite, Toast.LENGTH_LONG).show();
                return true;
            case R.id.popular_menu:
                //popolar
                String messagePopular = "Popular";
                Toast.makeText(context, messagePopular, Toast.LENGTH_LONG).show();
                return true;
            case R.id.top_rated_menu:
                //top rated
                String messageTopRated = "Top Rated";
                Toast.makeText(context, messageTopRated, Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class GetDataTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMovieView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result= null;

            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            mLoadingView.setVisibility(View.GONE);
            mMovieView.setVisibility(View.VISIBLE);

            Log.e("json_object", strings);
            try {
                JSONObject jsonObject = new JSONObject(strings);
                JSONArray results = jsonObject.getJSONArray("results");

                for(int i=0; i<results.length(); i++){
                    movieList.add(new Movie(
                            results.getJSONObject(i).getInt("id"),
                            results.getJSONObject(i).getString("title"),
                            results.getJSONObject(i).getString("poster_path"),
                            results.getJSONObject(i).getString("overview"),
                            results.getJSONObject(i).getString("release_date"),
                            results.getJSONObject(i).getDouble("vote_average"))
                    );
                }

                adapter.notifyDataSetChanged();

                Log.e("json_array", strings);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
