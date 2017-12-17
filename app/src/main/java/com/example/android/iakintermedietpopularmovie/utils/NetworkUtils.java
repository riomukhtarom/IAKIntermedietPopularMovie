package com.example.android.iakintermedietpopularmovie.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by LENOVO on 01/12/2017.
 */

public class NetworkUtils {

    final static private String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static private String MOVIE_POSTER_BASE_URL = "https://image.tmdb.org/t/p/";

    final static private String PARAM_API_KEY = "api_key";
    final static private String API_KEY = "api";
    final static private String IMAGE_SIZE = "w185";
    final static private String VIDEOS_ENDPOINT_SEGMENT = "videos";
    final static private String REVIEWS_ENDPOINT_SEGMENT = "reviews";


    /**
     * check if connect to internet. based on StackOverflow post below
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */
    public static boolean isOnline(ConnectivityManager cm) {
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Builds the URL used to query data from theMovieDB.
     *
     * @param sort_by data will be sorted by sort_by param.
     * @return The URL to use to query the theMovieDB.
     */
    public static URL buildUrl(String sort_by) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(sort_by)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     * Builds the URL used to get image from theMovieDB.
     *
     * @param poster_image_name poster image name want to obtain.
     * @return The URL to use to query the theMovieDB.
     */
    public static URL buildMoviePosterUrl(String poster_image_name) {
        //remove "/" from image name
        poster_image_name = poster_image_name.startsWith("/") ? poster_image_name.substring(1) : poster_image_name;

        Uri builtUri = Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendPath(poster_image_name)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     *
     * @param movie_id
     * @return
     */
    public static URL buildMovieVideoUrl(String movie_id){
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath(VIDEOS_ENDPOINT_SEGMENT)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     *
     * @param movie_id
     * @return
     */
    public static URL buildMovieReviewUrl(String movie_id){
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath(REVIEWS_ENDPOINT_SEGMENT)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}