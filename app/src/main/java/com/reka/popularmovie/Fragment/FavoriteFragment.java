package com.reka.popularmovie.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.reka.popularmovie.database.MovieContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public FavoriteFragment() {
        // Required empty public constructor
    }

    List<ResultsItem> listMovie = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //perbedaan antara fragment dan activity
        //1.harus membuat variable viewnya dulu biar bisa di panggil
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView =  view.findViewById(R.id.rc_pop);
        //1. perlu dataset
//        //memakai fori
//        for (int i = 0; i < 20 ; i++) {
//            //memakai constructor
//            MovieModel movieModel1 = new MovieModel("https://myanimelist.cdn-dena.com/images/anime/9/77484l.jpg","Kimi No Nawa");
//            listMovie.add(movieModel1);
//
//            //memakai setter
//            MovieModel movieModel2 = new MovieModel();
//            movieModel2.setJudulMovies("Kimi no nawa ");
//            movieModel2.setPosterMovies("https://myanimelist.cdn-dena.com/images/anime/9/77484l.jpg");
//            listMovie.add(movieModel2);
//        }
        ambilDataFavorit();
        //2. adapter
        recyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
        //3. layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return view;

    }

    private void ambilDataFavorit() {
    getActivity().getSupportLoaderManager().initLoader(100,null,this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       switch (id){
           case 100:
               return new CursorLoader(
                       getActivity(),
                       MovieContract.MovieEntry.CONTENT_URI,
                       null,null,null,null);
           default:
               throw new  RuntimeException("Loader not working guys");
       }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listMovie.clear();
        if (data.getCount() > 0){
            if (data.moveToFirst()){
                do {
                    ResultsItem movie = new ResultsItem();
                    movie.setId(data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                    movie.setTitle(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_JUDUL)));
                    movie.setPosterPath(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
                    movie.setOverview(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                    listMovie.add(movie);
                    recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
                }while (data.moveToNext());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onResume() {
        super.onResume();
        ambilDataFavorit();
    }
}
