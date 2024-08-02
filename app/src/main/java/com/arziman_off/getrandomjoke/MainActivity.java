package com.arziman_off.getrandomjoke;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private MainViewModel viewModel;
    private MaterialButton btnNewGenerate;
    private TextView tvOneJokeSetup;
    private TextView tvOneJokePunchline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadOneNewJoke();
        viewModel.getJokeItem().observe(this, new Observer<JokeItemInfo>() {
            @Override
            public void onChanged(JokeItemInfo jokeItem) {
                tvOneJokeSetup.setText(jokeItem.getSetup());
                tvOneJokePunchline.setText(jokeItem.getPunchline());
                Log.d(LOG_TAG, jokeItem.toString());
            }
        });
        btnNewGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadOneNewJoke();
            }
        });
    }

    private void initViews() {
        btnNewGenerate = findViewById(R.id.btnNewGenerate);
        tvOneJokeSetup = findViewById(R.id.tvOneJokeSetup);
        tvOneJokePunchline = findViewById(R.id.tvOneJokePunchline);
    }
}