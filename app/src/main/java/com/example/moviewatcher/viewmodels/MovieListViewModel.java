package com.example.moviewatcher.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviewatcher.repositories.MovieListRepository;
import com.example.moviewatcher.webservices.omdb.OmdbSearchResponse;

public class MovieListViewModel extends AndroidViewModel {

    public MovieListViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<OmdbSearchResponse> mutableLiveData;
    private MovieListRepository movieListRepository;

    public void getData(String api_key, String title, String type, String year, int page) {
        movieListRepository = MovieListRepository.getInstance();
        mutableLiveData = movieListRepository.getSearchDetails(api_key, title, type, year, page);
    }

    public LiveData<OmdbSearchResponse> getMovieListRepository() {
        return mutableLiveData;
    }
}
