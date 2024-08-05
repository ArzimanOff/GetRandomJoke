package com.arziman_off.getrandomjoke;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int needJokesCnt = 1;
    private static final String LOG_TAG = "MainActivity";
    private ScrollView svJokesListBox;
    private LinearLayout llOneJokeBox;
    private LinearLayout jokesList;
    private LinearLayout loadingProgressBarBox;
    private MainViewModel viewModel;
    private MaterialButton btnNewGenerate;
    private ImageButton btnChangeGenerateRules;
    private TextView tvJokeTypeText;
    private TextView tvOneJokeSetup;
    private TextView tvOneJokePunchline;
    private TextView tvJokeIdText;
    private RadioGroup rgChooseJokesCnt;
    private RadioButton rbOneJoke;
    private RadioButton rbListOfJokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (needJokesCnt == 1){
            viewModel.loadOneNewJoke();
        } else if (needJokesCnt > 1){
            viewModel.loadListOfJokes();
        }
        viewModel.getJokeItem().observe(this, new Observer<JokeItemInfo>() {
            @Override
            public void onChanged(JokeItemInfo jokeItem) {
                Log.d(LOG_TAG, jokeItem.toString());
                tvJokeTypeText.setText(jokeItem.getType());
                tvOneJokeSetup.setText(jokeItem.getSetup());
                tvOneJokePunchline.setText(jokeItem.getPunchline());
                tvJokeIdText.setText(jokeItem.getId().toString());
                svJokesListBox.setVisibility(View.GONE);
                llOneJokeBox.setVisibility(View.VISIBLE);
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
                    if (needJokesCnt == 1){
                        llOneJokeBox.setVisibility(View.VISIBLE);
                        svJokesListBox.setVisibility(View.GONE);
                    } else if (needJokesCnt > 1){
                        llOneJokeBox.setVisibility(View.GONE);
                        svJokesListBox.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btnNewGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needJokesCnt == 1){
                    viewModel.loadOneNewJoke();
                } else if (needJokesCnt > 1){
                    viewModel.loadListOfJokes();
                }
            }
        });
        btnChangeGenerateRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeGenerateRulesDialog();
                checkNeedJokesCnt();
            }

        });
    }
    private void checkNeedJokesCnt() {
        if (rbOneJoke.isChecked()){
            needJokesCnt = 1;
        } else {
            needJokesCnt = 20;
        }
    }

    private void showChangeGenerateRulesDialog(){
        int active_rb_color = ContextCompat.getColor(this, R.color.main_color);
        int inactive_rb_color = ContextCompat.getColor(this, R.color.inactive_rb_color);
        View changeGenerateRulesDialog = getLayoutInflater()
                .inflate(
                        R.layout.change_generate_rules_dialog,
                        null
                );
        rgChooseJokesCnt = changeGenerateRulesDialog.findViewById(R.id.rgChooseJokesCnt);
        rbOneJoke = changeGenerateRulesDialog.findViewById(R.id.rbOneJoke);
        rbListOfJokes = changeGenerateRulesDialog.findViewById(R.id.rbListOfJokes);
        rgChooseJokesCnt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkNeedJokesCnt();
                if (rbOneJoke.isChecked()){
                    // активируем нужные стили для текущего радиобокса
                    rbOneJoke.setBackgroundResource(R.drawable.active_rb_bg);
                    rbOneJoke.setTextColor(active_rb_color);

                    // деактивируем нужные стили для остальных радиобоксов
                    rbListOfJokes.setBackgroundResource(R.drawable.inactive_rb_bg);
                    rbListOfJokes.setTextColor(inactive_rb_color);
                } else {
                    // активируем нужные стили для текущего радиобокса
                    rbListOfJokes.setBackgroundResource(R.drawable.active_rb_bg);
                    rbListOfJokes.setTextColor(active_rb_color);

                    // деактивируем нужные стили для остальных радиобоксов
                    rbOneJoke.setBackgroundResource(R.drawable.inactive_rb_bg);
                    rbOneJoke.setTextColor(inactive_rb_color);
                }
            }
        });

        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        builder.setView(changeGenerateRulesDialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Отмена", null);

        builder.create().show();
    }

    private void initViews() {
        loadingProgressBarBox = findViewById(R.id.loadingProgressBarBox);

        svJokesListBox = findViewById(R.id.svJokesListBox);
        llOneJokeBox = findViewById(R.id.llOneJokeBox);
        jokesList = findViewById(R.id.jokesList);

        btnNewGenerate = findViewById(R.id.btnNewGenerate);
        btnChangeGenerateRules = findViewById(R.id.btnChangeGenerateRules);

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