package com.example.moviewatcher.webservices.omdb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OmdbService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(OmdbInterface.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
