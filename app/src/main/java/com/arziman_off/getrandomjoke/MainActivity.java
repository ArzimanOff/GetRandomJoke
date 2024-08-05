package com.arziman_off.getrandomjoke;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ScrollView svJokesListBox;
    private LinearLayout llOneJokeBox;
    private LinearLayout jokesList;
    private LinearLayout loadingProgressBarBox;
    private static final String LOG_TAG = "MainActivity";
    private MainViewModel viewModel;
    private MaterialButton btnNewGenerate;
    private TextView tvJokeTypeText;
    private TextView tvOneJokeSetup;
    private TextView tvOneJokePunchline;
    private TextView tvJokeIdText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.loadOneNewJoke();
        //        viewModel.loadListOfJokes();
        viewModel.getJokeItem().observe(this, new Observer<JokeItemInfo>() {
            @Override
            public void onChanged(JokeItemInfo jokeItem) {
                Log.d(LOG_TAG, jokeItem.toString());
                tvJokeTypeText.setText(jokeItem.getType());
                tvOneJokeSetup.setText(jokeItem.getSetup());
                tvOneJokePunchline.setText(jokeItem.getPunchline());
                tvJokeIdText.setText(jokeItem.getId().toString());
            }
        });
        viewModel.getJokeItemsList().observe(this, new Observer<List<JokeItemInfo>>() {
            @Override
            public void onChanged(List<JokeItemInfo> jokeItemsList) {
                showJokes(jokeItemsList);
            }
        });

        viewModel.getIsLoadingError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadingError) {
                if (isLoadingError){
                    Toast toast = Toast.makeText(
                            MainActivity.this,
                            R.string.internet_error_toast_text,
                            Toast.LENGTH_SHORT
                    );
                    toast.setGravity(
                            Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
                            0,
                            700
                    );
                    toast.show();
                }
            }
        });
        viewModel.getIsNowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNowLoading) {
                if (isNowLoading) {
                    loadingProgressBarBox.setVisibility(View.VISIBLE);
                    svJokesListBox.setVisibility(View.GONE);
                    llOneJokeBox.setVisibility(View.GONE);
                } else {
                    loadingProgressBarBox.setVisibility(View.GONE);
                    llOneJokeBox.setVisibility(View.VISIBLE);
                }
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
        loadingProgressBarBox = findViewById(R.id.loadingProgressBarBox);

        svJokesListBox = findViewById(R.id.svJokesListBox);
        llOneJokeBox = findViewById(R.id.llOneJokeBox);
        jokesList = findViewById(R.id.jokesList);

        btnNewGenerate = findViewById(R.id.btnNewGenerate);

        tvJokeTypeText = findViewById(R.id.tvJokeTypeText);
        tvOneJokeSetup = findViewById(R.id.tvJokeSetup);
        tvOneJokePunchline = findViewById(R.id.tvJokePunchline);
        tvJokeIdText = findViewById(R.id.tvJokeIdText);
    }

    private void showJokes(List<JokeItemInfo> jokes){
        for (JokeItemInfo jokeItem : jokes) {
            Log.d("checkCheck", jokeItem.toString());
            View jokeView = getLayoutInflater()
                    .inflate(
                            R.layout.joke_item_view,
                            jokesList,
                            false
                    );
            TextView tvJokeType = jokeView.findViewById(R.id.tvJokeTypeText);
            TextView tvJokeSetup = jokeView.findViewById(R.id.tvJokeSetup);
            TextView tvJokePunchline = jokeView.findViewById(R.id.tvJokePunchline);
            TextView tvJokeId = jokeView.findViewById(R.id.tvJokeIdText);

            tvJokeType.setText(jokeItem.getType());
            tvJokeSetup.setText(jokeItem.getSetup());
            tvJokePunchline.setText(jokeItem.getPunchline());
            tvJokeId.setText("id: "+ jokeItem.getId().toString());
            jokesList.addView(jokeView);
        }
    }
}