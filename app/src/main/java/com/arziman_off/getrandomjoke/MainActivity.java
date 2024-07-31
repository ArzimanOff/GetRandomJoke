package com.arziman_off.getrandomjoke;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadOneNewJoke();
        viewModel.getJokeItem().observe(this, new Observer<JokeItemInfo>() {
            @Override
            public void onChanged(JokeItemInfo jokeItem) {
                Log.d(LOG_TAG, jokeItem.toString());
            }
        });
    }
}