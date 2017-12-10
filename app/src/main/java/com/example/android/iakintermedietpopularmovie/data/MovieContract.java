package com.example.android.iakintermedietpopularmovie.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URL;

/**
 * Created by LENOVO on 08/12/2017.
 */

public class MovieContract {

    private MovieContract() {}

    public static final String AUTHORITY = "com.example.android.iakintermedietpopularmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_MOVIES = "movies";



    public static class MovieList implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movie_list";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

    }

}
