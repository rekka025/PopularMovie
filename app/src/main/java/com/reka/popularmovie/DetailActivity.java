package com.reka.popularmovie;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.reka.popularmovie.Model.ResultsItem;
import com.reka.popularmovie.database.MovieContract;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    List<ResultsItem> listMovie = new ArrayList<>();
    int posisi;
    Boolean statusFavorite = false;
    private FloatingActionButton fab;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //baca sharedpref
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //terima data
        listMovie = getIntent().getParcelableArrayListExtra("DATA_MOVIE");
        posisi = getIntent().getIntExtra("POSISI", 0);

                //default nya hati fals (kosong)
        statusFavorite = sharedPreferences.getBoolean("FAVORITE" + listMovie.get(posisi).getId(), false);
        //get id untuk membedakan film 1 dengan yng lain

        //menerima key
        //harus berurutan
        getSupportActionBar().setTitle(listMovie.get(posisi).getTitle());
        //nampilin img dan sinopsis
        ImageView imageView = findViewById(R.id.img_detail);
        TextView tvSinopsis = findViewById(R.id.tv_sinopsis);

        tvSinopsis.setText(listMovie.get(posisi).getOverview());
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + listMovie.get(posisi).getPosterPath()).into(imageView);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //variable boolean statusFavorite default false
                //kalo di klik false jadi true dan hati terisi penuh
                //kalo diklik true jadi false dan hati kosong
                //ganti status
                if (statusFavorite == false) {
                    statusFavorite = true;
                    //insert
                    insertDatabase();
                } else if (statusFavorite == true) {
                    statusFavorite = false;
                    //delete
                    delDatabase();
                }

                //ganti icon
                updateFavorite(statusFavorite);

                //tulis data
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("FAVORITE" + listMovie.get(posisi).getId(), statusFavorite);
                editor.commit();
            }
        });


        updateFavorite(statusFavorite);


    }

    private void delDatabase() {
        int id = getContentResolver().delete(MovieContract.MovieEntry
        .CONTENT_URI.buildUpon().appendPath(String.valueOf(listMovie.get(posisi).getId())).build(),null,null);

        if (id >0){
            Toast.makeText(this, "Data Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Gagal Di simpan", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertDatabase() {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_ID,listMovie.get(posisi).getId());
        values.put(MovieContract.MovieEntry.COLUMN_JUDUL,listMovie.get(posisi).getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER,listMovie.get(posisi).getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,listMovie.get(posisi).getOverview());

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,values);
        if (ContentUris.parseId(uri) >0){
            Toast.makeText(this, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Gagal Di simpan", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFavorite(Boolean status) {
        if (status == false) {
            fab.setImageResource(R.drawable.ic_not_favorite);

        } else if (status == true) {

            fab.setImageResource(R.drawable.ic_favorite);

        }
    }

}
