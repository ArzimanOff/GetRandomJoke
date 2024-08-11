package com.arziman_off.getrandomjoke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class FavoriteJokesActivity extends AppCompatActivity {

    private ImageView goBackBtn;
    private ImageView toolbarLogo;
    private MaterialButton placeholderGoBackBtn;
    private MaterialButton addMyJokeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_jokes);
        initViews();
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        placeholderGoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        goBackBtn = findViewById(R.id.goBackBtn);
        toolbarLogo = findViewById(R.id.toolbarLogo);
        placeholderGoBackBtn = findViewById(R.id.placeholder_go_back_btn);
        addMyJokeBtn = findViewById(R.id.add_my_joke_btn);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavoriteJokesActivity.class);
    }
}