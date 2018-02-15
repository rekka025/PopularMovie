package com.reka.popularmovie.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reka.popularmovie.Adapter.MovieAdapter;
import com.reka.popularmovie.Model.ResponseModel;
import com.reka.popularmovie.Model.ResultsItem;
import com.reka.popularmovie.R;
import com.reka.popularmovie.Retrofit.RetrofitServer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Top_Rated_Fragment extends Fragment {


    public Top_Rated_Fragment() {
        // Required empty public constructor
    }
    List<ResultsItem> listMovie = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_top__rated_, container, false);
        recyclerView =  view.findViewById(R.id.rc_pop);
//        //1. perlu dataset
////        //memakai fori
////        for (int i = 0; i < 20 ; i++) {
////            //memakai constructor
////            MovieModel movieModel1 = new MovieModel("https://myanimelist.cdn-dena.com/images/anime/9/77484l.jpg","Kimi No Nawa");
////            listMovie.add(movieModel1);
////
////            //memakai setter
////            MovieModel movieModel2 = new MovieModel();
////            movieModel2.setJudulMovies("Kimi no nawa ");
////            movieModel2.setPosterMovies("https://myanimelist.cdn-dena.com/images/anime/9/77484l.jpg");
////            listMovie.add(movieModel2);
////        }
        ambilDataOnline();
//
        //2. adapter
        recyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
        //3. layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));


        return view;

    }

    private void ambilDataOnline() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Mohon bersabar");
        progress.show();

        final Call<ResponseModel> requestData = RetrofitServer.getApiService().ambilDataTopRated();
        requestData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()){
                    progress.dismiss();
                    listMovie = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));

                }else {
                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
            }
        });


    }


}


