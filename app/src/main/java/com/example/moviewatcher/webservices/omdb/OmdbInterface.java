package com.example.moviewatcher.webservices.omdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbInterface {

    String baseURL = "https://www.omdbapi.com/";
    String API_KEY = "3998006b";

    @GET(".")
    Call<OmdbSearchResponse> getSearchResults(
            @Query("apikey") String api_key,
            @Query("s") String title,
            @Query("type") String type,
            @Query("y") String year,
            @Query("page") int page
    );

    @GET(".")
    Call<OmdbDetailsResponse> getMovieDetails(
            @Query("apikey") String api_key,
            @Query("i") String id,
            @Query("plot") String plot_length
    );
}
