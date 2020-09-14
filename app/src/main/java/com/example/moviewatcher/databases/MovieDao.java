package com.example.moviewatcher.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviewatcher.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM movies_table")
    void deleteAllMovies();

    @Query("SELECT * FROM movies_table ORDER BY title ASC")
    LiveData<List<Movie>> getAllMovies();
}
