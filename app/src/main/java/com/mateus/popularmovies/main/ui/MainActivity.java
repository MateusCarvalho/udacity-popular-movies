package com.mateus.popularmovies.main.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.MasterActivity;

public class MainActivity extends MasterActivity {

    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListview = (ListView) findViewById(R.id.listMovies);
    }

}
