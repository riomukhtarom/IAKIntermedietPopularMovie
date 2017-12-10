package com.example.android.iakintermedietpopularmovie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LENOVO on 08/12/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final short DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieDB.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.MovieList.TABLE_NAME + " ( " +
                MovieContract.MovieList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieList.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieList.COLUMN_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieList.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieList.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieList.COLUMN_VOTE_AVERAGE + " FLOAT NOT NULL " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+MovieContract.MovieList.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
