package com.reka.popularmovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reka.popularmovie.DetailActivity;
import com.reka.popularmovie.MainActivity;
import com.reka.popularmovie.Model.ResultsItem;
import com.reka.popularmovie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reka on 2/3/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Context context;
    List<ResultsItem> listMovie = new ArrayList<>();

    //constructor
    public MovieAdapter(Context context, List<ResultsItem> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }
    //sambungin layout_item
    //kedua
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(view);
    }
    //set data
    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, final int position) {
    holder.judul.setText(listMovie.get(position).getTitle());
    Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+listMovie.get(position).getPosterPath()).into(holder.imageView);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent pindah = new Intent(context, DetailActivity.class);

            //kirim data antar fragment ke activity
            //ngirim key
            pindah.putParcelableArrayListExtra("DATA_MOVIE", (ArrayList<? extends Parcelable>) listMovie);
            pindah.putExtra("POSISI",position);
            //kirim data
            context.startActivity(pindah);

        }
    });

    }

    //jumlah list
    //mas arif ini pertama
    @Override
    public int getItemCount() {
        return listMovie.size();
    }
    //sambungin komponen dalam layout
    //ketiga
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView judul;
        ImageView imageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_judul);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
