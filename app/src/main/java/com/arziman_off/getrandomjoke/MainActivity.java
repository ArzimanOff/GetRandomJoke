package com.arziman_off.getrandomjoke;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //---------------------------------
    private LinearLayout jokesList;
    private ArrayList<JokeItemInfo> jokes = new ArrayList<>();
    //---------------------------------
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
        //---------------------------------
        generateJokes(2);
        showJokes();
        //---------------------------------
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
                    //TODO
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
        //---------------------------------
        jokesList = findViewById(R.id.jokesList);
        //---------------------------------
        btnNewGenerate = findViewById(R.id.btnNewGenerate);
//        tvOneJokeSetup = findViewById(R.id.tvJokeSetup);
//        tvOneJokePunchline = findViewById(R.id.tvJokePunchline);
    }

    //---------------------------------
    private void generateJokes(int n){
        for (int i = 0; i < n; i++){
            viewModel.loadOneNewJoke();
            viewModel.getJokeItem().observe(this, new Observer<JokeItemInfo>() {
                @Override
                public void onChanged(JokeItemInfo jokeItem) {
                    jokes.add(jokeItem);
                    Log.d("checkCheck", String.valueOf(jokes.size()));
                    Log.d(LOG_TAG, jokeItem.toString());
                }
            });
        }
    }

    private void showJokes(){
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

    //---------------------------------

}