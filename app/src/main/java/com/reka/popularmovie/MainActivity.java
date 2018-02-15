package com.reka.popularmovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.reka.popularmovie.Fragment.FavoriteFragment;
import com.reka.popularmovie.Fragment.PopularFragment;
import com.reka.popularmovie.Fragment.Top_Rated_Fragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //cara memanggil fragment
                    getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragment,new PopularFragment())
                    .commit();

                    return true;
                case R.id.navigation_dashboard:
                    //cara memanggil fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_fragment,new Top_Rated_Fragment())
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    //cara memanggil fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_fragment,new FavoriteFragment())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment,new PopularFragment())
                .commit();
    }


}
