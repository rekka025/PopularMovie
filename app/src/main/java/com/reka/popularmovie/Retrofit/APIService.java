package com.reka.popularmovie.Retrofit;

import com.reka.popularmovie.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by reka on 2/3/18.
 */

public interface APIService {

    @GET("3/movie/popular?api_key=4ccfcd5636af67c1e98f282f90220360&language=en-US&page=1")
    Call<ResponseModel> ambilDataPopularMovie();

    @GET("3/movie/top_rated?api_key=4ccfcd5636af67c1e98f282f90220360&language=en-US&page=1")
    Call<ResponseModel> ambilDataTopRated();
}
