package com.reka.popularmovie.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by reka on 2/3/18.
 */

public class RetrofitServer {
    private static Retrofit getRetrofit(){
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    return retrofit;
    }

    public static APIService getApiService(){
        return getRetrofit().create(APIService.class);
    }
}
