package com.arziman_off.getrandomjoke;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
    private View btnChangeGenerateRules;
    private TextView tvJokeTypeText;
    private TextView tvOneJokeSetup;
    private TextView tvOneJokePunchline;
    private TextView tvJokeIdText;
    private RadioGroup rgChooseJokesCnt;
    private RadioButton rbOneJoke;
    private RadioButton rbListOfJokes;
    private TextView tvNeedJokesCntInputTitle;
    private EditText etNeedJokesCntInput;
    private LinearLayout clownView;
    private TextView changeGenerateRulesBtnIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMainViews();
        changeGenerateRulesBtnIndicator.setText(String.valueOf(needJokesCnt));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (needJokesCnt == 1) {
            viewModel.loadOneNewJoke();
        } else if (needJokesCnt > 1) {
            viewModel.loadListOfJokes(needJokesCnt);
        }
        viewModel.getJokeItem().observe(this, new Observer<JokeItemInfo>() {
            @Override
            public void onChanged(JokeItemInfo jokeItem) {
                Log.d(LOG_TAG, jokeItem.toString());
                tvJokeTypeText.setText(jokeItem.getType());
                tvOneJokeSetup.setText(jokeItem.getSetup());
                tvOneJokePunchline.setText(jokeItem.getPunchline());
                tvJokeIdText.setText("id: " + jokeItem.getId().toString());

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
                if (isLoadingError) {
                    loadingProgressBarBox.setVisibility(View.VISIBLE);
                    ApplicationSignals.loadingError(getApplicationContext());
                } else {
                    loadingProgressBarBox.setVisibility(View.GONE);
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
                    if (needJokesCnt == 1) {
                        llOneJokeBox.setVisibility(View.VISIBLE);
                        svJokesListBox.setVisibility(View.GONE);
                    } else if (needJokesCnt > 1) {
                        llOneJokeBox.setVisibility(View.GONE);
                        svJokesListBox.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btnNewGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clownView = findViewById(R.id.clownView);
                if (needJokesCnt == 1) {
                    clownView.setVisibility(View.GONE);
                    viewModel.loadOneNewJoke();
                } else if (needJokesCnt > 1) {
                    clownView.setVisibility(View.GONE);
                    jokesList.removeAllViews();
                    viewModel.loadListOfJokes(needJokesCnt);
                } else {
                    ImageView imageViewGif = findViewById(R.id.clownViewImage);

                    Glide.with(getApplicationContext())
                            .asGif()
                            .load(R.drawable.monke_clown)
                            .into(imageViewGif);
                    clownView.setVisibility(View.VISIBLE);

                    llOneJokeBox.setVisibility(View.GONE);
                    svJokesListBox.setVisibility(View.GONE);
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
        if (rbOneJoke.isChecked()) {
            needJokesCnt = 1;
        } else {
            if (etNeedJokesCntInput != null &&
                    !etNeedJokesCntInput.getText().toString().trim().isEmpty()){
                needJokesCnt = Integer.parseInt(etNeedJokesCntInput.getText().toString());
            }
        }
    }

    private void showChangeGenerateRulesDialog() {
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
        tvNeedJokesCntInputTitle = changeGenerateRulesDialog.findViewById(R.id.tvNeedJokesCntInputTitle);
        etNeedJokesCntInput = changeGenerateRulesDialog.findViewById(R.id.etNeedJokesCntInput);
        rgChooseJokesCnt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbOneJoke.isChecked()) {
                    // активируем нужные стили для текущего радиобокса
                    rbOneJoke.setBackgroundResource(R.drawable.active_rb_bg);
                    rbOneJoke.setTextColor(active_rb_color);
                    // деактивируем нужные стили для остальных радиобоксов
                    rbListOfJokes.setBackgroundResource(R.drawable.inactive_rb_bg);
                    rbListOfJokes.setTextColor(inactive_rb_color);

                    // убираем поле ввода кол-ва шуток
                    tvNeedJokesCntInputTitle.setVisibility(View.GONE);
                    etNeedJokesCntInput.setVisibility(View.GONE);
                } else {
                    // активируем нужные стили для текущего радиобокса
                    rbListOfJokes.setBackgroundResource(R.drawable.active_rb_bg);
                    rbListOfJokes.setTextColor(active_rb_color);
                    // деактивируем нужные стили для остальных радиобоксов
                    rbOneJoke.setBackgroundResource(R.drawable.inactive_rb_bg);
                    rbOneJoke.setTextColor(inactive_rb_color);

                    // показываем поле ввода кол-ва шуток
                    tvNeedJokesCntInputTitle.setVisibility(View.VISIBLE);
                    etNeedJokesCntInput.setVisibility(View.VISIBLE);
                }
            }
        });

        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        builder
                .setView(changeGenerateRulesDialog)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        checkNeedJokesCnt();
                        changeGenerateRulesBtnIndicator.setText(String.valueOf(needJokesCnt));
                    }
                })
                .create()
                .show();
    }

    private void initMainViews() {
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
        changeGenerateRulesBtnIndicator = findViewById(R.id.changeGenerateRulesBtnIndicator);
    }

    private void showJokes(List<JokeItemInfo> jokes) {
        for (JokeItemInfo jokeItem : jokes) {
            Log.d(LOG_TAG, jokeItem.toString());
            View jokeView = getLayoutInflater()
                    .inflate(
                            R.layout.joke_item_view,
                            jokesList,
                            false
                    );
            initJokeViewItems(jokeView, jokeItem);
            jokesList.addView(jokeView);
        }
    }

    private void initJokeViewItems(View jokeView, JokeItemInfo jokeItem) {
        TextView tvJokeType = jokeView.findViewById(R.id.tvJokeTypeText);
        TextView tvJokeSetup = jokeView.findViewById(R.id.tvJokeSetup);
        TextView tvJokePunchline = jokeView.findViewById(R.id.tvJokePunchline);
        TextView tvJokeId = jokeView.findViewById(R.id.tvJokeIdText);
        tvJokeType.setText(jokeItem.getType());
        tvJokeSetup.setText(jokeItem.getSetup());
        tvJokePunchline.setText(jokeItem.getPunchline());
        tvJokeId.setText("id: " + jokeItem.getId().toString());
    }
}