package com.example.android.iakintermedietpopularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LENOVO on 01/12/2017.
 */

public class Movie implements Parcelable{

    public Movie(int id, String title, String posterPath, String overview, String releaseDate, double voteAverage) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public Movie(int id, String title, float popularity, String posterPath, String backdropPath, String overview, String releaseDate, int voteCount, float voteAverage, boolean isFavMovie) {
        this.id = id;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.isFavMovie = isFavMovie;
    }

    private int id;
    private String title;
    private float popularity;
    private String posterPath;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private int voteCount;
    private double voteAverage;
    private boolean isFavMovie = false ;

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        popularity = in.readFloat();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readDouble();
        isFavMovie = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isFavMovie() {
        return isFavMovie;
    }

    public void setFavMovie(boolean favMovie) {
        isFavMovie = favMovie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeFloat(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeInt(voteCount);
        parcel.writeDouble(voteAverage);
        parcel.writeByte((byte) (isFavMovie ? 1 : 0));
    }
}
