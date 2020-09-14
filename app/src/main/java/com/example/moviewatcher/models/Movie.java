package com.example.moviewatcher.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies_table", indices = {@Index(value = {"movieId"}, unique = true)})
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String movieId;

    private String title;

    private String genre;

    private String type;

    private String year;

    private String description;

    private String posterUrl;

    public Movie(String movieId, String title, String genre, String type, String year, String description, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.type = type;
        this.year = year;
        this.description = description;
        this.posterUrl = posterUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
