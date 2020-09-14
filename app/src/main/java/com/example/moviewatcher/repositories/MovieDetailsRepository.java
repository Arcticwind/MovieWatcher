package com.example.moviewatcher.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviewatcher.webservices.omdb.OmdbDetailsResponse;
import com.example.moviewatcher.webservices.omdb.OmdbInterface;
import com.example.moviewatcher.webservices.omdb.OmdbService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {

    private static MovieDetailsRepository movieDetailsRepository;

    public static MovieDetailsRepository getInstance() {
        if (movieDetailsRepository == null) {
            movieDetailsRepository = new MovieDetailsRepository();
        }
        return movieDetailsRepository;
    }

    private OmdbInterface omdbInterface;

    public MovieDetailsRepository() {
        omdbInterface = OmdbService.createService(OmdbInterface.class);
    }

    public MutableLiveData<OmdbDetailsResponse> getMovieDetails(String api_key, String id, String plot) {
        MutableLiveData<OmdbDetailsResponse> data = new MutableLiveData<>();
        omdbInterface.getMovieDetails(api_key, id, plot).enqueue(new Callback<OmdbDetailsResponse>() {
            @Override
            public void onResponse(Call<OmdbDetailsResponse> call, Response<OmdbDetailsResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OmdbDetailsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
