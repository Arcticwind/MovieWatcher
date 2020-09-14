package com.example.moviewatcher.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviewatcher.models.Movie;
import com.example.moviewatcher.repositories.WishListRepository;

import java.util.List;

public class WishListViewModel extends AndroidViewModel {
    private WishListRepository repository;
    private LiveData<List<Movie>> allMovies;

    public WishListViewModel(Application application) {
        super(application);
        repository = new WishListRepository(application);
        allMovies = repository.getAllMovies();
    }

    public void insert(Movie movie) { repository.insert(movie); }

    public void delete(Movie movie) { repository.delete(movie); }

    public void deleteAllMovies() { repository.deleteAllMovies(); }

    public LiveData<List<Movie>> getAllMovies() { return allMovies; }
}
