package com.example.moviewatcher.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviewatcher.webservices.omdb.OmdbInterface;
import com.example.moviewatcher.webservices.omdb.OmdbSearchResponse;
import com.example.moviewatcher.webservices.omdb.OmdbService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListRepository {

    private static MovieListRepository movieListRepository;

    public static MovieListRepository getInstance() {
        if (movieListRepository == null) {
            movieListRepository = new MovieListRepository();
        }
        return movieListRepository;
    }

    private OmdbInterface omdbInterface;

    public MovieListRepository() {
        omdbInterface = OmdbService.createService(OmdbInterface.class);
    }

    public MutableLiveData<OmdbSearchResponse> getSearchDetails(String api_key, String title, String type, String year, int page) {
        MutableLiveData<OmdbSearchResponse> data = new MutableLiveData<>();
        omdbInterface.getSearchResults(api_key, title, type, year, page).enqueue(new Callback<OmdbSearchResponse>() {
            @Override
            public void onResponse(Call<OmdbSearchResponse> call, Response<OmdbSearchResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OmdbSearchResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
