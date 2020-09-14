package com.example.moviewatcher.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviewatcher.repositories.MovieDetailsRepository;
import com.example.moviewatcher.webservices.omdb.OmdbDetailsResponse;

public class MovieDetailsViewModel extends AndroidViewModel {

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<OmdbDetailsResponse> mutableLiveData;
    private MovieDetailsRepository movieDetailsRepository;

    public void getData(String api_key, String id, String plot) {
        if (mutableLiveData != null) {
            return;
        }
        movieDetailsRepository = MovieDetailsRepository.getInstance();
        mutableLiveData = movieDetailsRepository.getMovieDetails(api_key, id, plot);
    }

    public MutableLiveData<OmdbDetailsResponse> getMovieDetailsRepository() {
        return mutableLiveData;
    }
}
