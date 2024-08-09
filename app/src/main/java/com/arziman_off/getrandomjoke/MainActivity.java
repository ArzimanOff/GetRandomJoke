package com.arziman_off.getrandomjoke;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RANDOM_JOKE = -1;
    private static final String LOG_TAG = "MainActivity";
    public static int needJokesCnt = 1;
    public static int needJokeId = RANDOM_JOKE;
    private RecyclerView rvJokesListBox;
    private JokesAdapter jokesAdapter;
    private ConstraintLayout llOneJokeBox;
    private LinearLayout loadingProgressBarBox;
    private MainViewModel viewModel;
    private MaterialButton btnNewGenerate;
    private View btnChangeGenerateRules;
    private TextView tvJokeTypeText;
    private TextView tvOneJokeSetup;
    private TextView tvOneJokePunchline;
    private TextView tvJokeIdText;
    private ImageView oneJokeLikeBtn;
    private RadioButton rbOneJoke;
    private RadioButton rbListOfJokes;
    private RadioButton rbRandomId;
    private RadioButton rbSetId;
    private EditText etNeedJokeIdInput;
    private TextView tvNeedJokesCntInputTitle;
    private EditText etNeedJokesCntInput;
    private LinearLayout clownView;
    private TextView changeGenerateRulesBtnIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMainViews();

        jokesAdapter = new JokesAdapter();
        rvJokesListBox.setAdapter(jokesAdapter);

        changeGenerateRulesBtnIndicator.setText(String.valueOf(needJokesCnt));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (needJokesCnt == 1) {
            viewModel.loadOneNewJoke(needJokeId);
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
                oneJokeLikeBtn.setVisibility(View.VISIBLE);

                rvJokesListBox.setVisibility(View.GONE);
                llOneJokeBox.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getJokeItemsList().observe(this, new Observer<List<JokeItemInfo>>() {
            @Override
            public void onChanged(List<JokeItemInfo> jokeItemsList) {
                showJokes(jokeItemsList);
                jokesAdapter.notifyDataSetChanged();
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
                    rvJokesListBox.setVisibility(View.GONE);
                    llOneJokeBox.setVisibility(View.GONE);
                } else {
                    loadingProgressBarBox.setVisibility(View.GONE);
                    if (needJokesCnt == 1) {
                        llOneJokeBox.setVisibility(View.VISIBLE);
                        rvJokesListBox.setVisibility(View.GONE);
                    } else if (needJokesCnt > 1) {
                        llOneJokeBox.setVisibility(View.GONE);
                        rvJokesListBox.setVisibility(View.VISIBLE);
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
                    viewModel.loadOneNewJoke(needJokeId);
                    // после генерации по id стоит обнулить id
                    // чтобы снова генерировалось рандомно
                    needJokeId = RANDOM_JOKE;
                } else if (needJokesCnt > 1) {
                    clownView.setVisibility(View.GONE);
                    viewModel.loadListOfJokes(needJokesCnt);
                } else {
                    ImageView imageViewGif = findViewById(R.id.clownViewImage);

                    Glide.with(getApplicationContext())
                            .asGif()
                            .load(R.drawable.monke_clown)
                            .into(imageViewGif);
                    clownView.setVisibility(View.VISIBLE);

                    llOneJokeBox.setVisibility(View.GONE);
                    rvJokesListBox.setVisibility(View.GONE);
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
            if (rbRandomId.isChecked()){
                needJokeId = RANDOM_JOKE;
            } else {
                if (etNeedJokeIdInput != null &&
                        !etNeedJokeIdInput.getText().toString().trim().isEmpty()){
                    needJokeId = Integer.parseInt(etNeedJokeIdInput.getText().toString());
                }
            }
        } else {
            if (etNeedJokesCntInput != null &&
                    !etNeedJokesCntInput.getText().toString().trim().isEmpty()){
                needJokesCnt = Integer.parseInt(etNeedJokesCntInput.getText().toString());
            }
        }
    }

    private void showChangeGenerateRulesDialog() {
        View changeGenerateRulesDialog = getLayoutInflater()
                .inflate(
                        R.layout.change_generate_rules_dialog,
                        null
                );
        LinearLayout llChooseIdBox = changeGenerateRulesDialog.findViewById(R.id.llChooseIdBox);
        RadioGroup rgChooseId = changeGenerateRulesDialog.findViewById(R.id.rgChooseId);
        rbRandomId = changeGenerateRulesDialog.findViewById(R.id.rbRandomId);
        rbSetId = changeGenerateRulesDialog.findViewById(R.id.rbSetId);
        etNeedJokeIdInput = changeGenerateRulesDialog.findViewById(R.id.etNeedJokeIdInput);
        TextView etNeedJokeIdInputWarningText = changeGenerateRulesDialog.findViewById(R.id.etNeedJokeIdInputWarningText);

        etNeedJokeIdInput.addTextChangedListener(EditTextWatchers.getEtTextWatcher(etNeedJokeIdInputWarningText));

        RadioGroup rgChooseJokesCnt = changeGenerateRulesDialog.findViewById(R.id.rgChooseJokesCnt);
        rbOneJoke = changeGenerateRulesDialog.findViewById(R.id.rbOneJoke);
        rbListOfJokes = changeGenerateRulesDialog.findViewById(R.id.rbListOfJokes);
        tvNeedJokesCntInputTitle = changeGenerateRulesDialog.findViewById(R.id.tvNeedJokesCntInputTitle);
        etNeedJokesCntInput = changeGenerateRulesDialog.findViewById(R.id.etNeedJokesCntInput);
        TextView etNeedJokesCntInputWarningText = changeGenerateRulesDialog.findViewById(R.id.etNeedJokesCntInputWarningText);

        etNeedJokesCntInput.addTextChangedListener(EditTextWatchers.getEtTextWatcher(etNeedJokesCntInputWarningText));

        rgChooseJokesCnt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbOneJoke.isChecked()) {
                    activateRadioButton(rbOneJoke);
                    deactivateRadioButton(rbListOfJokes);

                    // убираем поле ввода кол-ва шуток
                    tvNeedJokesCntInputTitle.setVisibility(View.GONE);
                    etNeedJokesCntInput.setVisibility(View.GONE);
                    etNeedJokesCntInputWarningText.setVisibility(View.GONE);

                    // показываем кнопки выбора id
                    llChooseIdBox.setVisibility(View.VISIBLE);
                } else {
                    activateRadioButton(rbListOfJokes);
                    deactivateRadioButton(rbOneJoke);

                    // скрываем кнопки выбора id
                    llChooseIdBox.setVisibility(View.GONE);

                    // показываем поле ввода кол-ва шуток
                    tvNeedJokesCntInputTitle.setVisibility(View.VISIBLE);
                    etNeedJokesCntInput.setVisibility(View.VISIBLE);
                    etNeedJokesCntInputWarningText.setVisibility(View.VISIBLE);
                }
            }
        });

        rgChooseId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbRandomId.isChecked()){
                    etNeedJokeIdInput.setVisibility(View.GONE);
                    etNeedJokeIdInputWarningText.setVisibility(View.GONE);
                    activateRadioButton(rbRandomId);
                    deactivateRadioButton(rbSetId);
                } else {
                    etNeedJokeIdInput.setVisibility(View.VISIBLE);
                    etNeedJokeIdInputWarningText.setVisibility(View.VISIBLE);
                    activateRadioButton(rbSetId);
                    deactivateRadioButton(rbRandomId);
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

    private void activateRadioButton(RadioButton rb) {
        int active_rb_color = ContextCompat.getColor(this, R.color.main_color);
        // активируем нужные стили для текущего радиобокса
        rb.setBackgroundResource(R.drawable.active_rb_bg);
        rb.setTextColor(active_rb_color);
    }
    private void deactivateRadioButton(RadioButton rb) {
        int inactive_rb_color = ContextCompat.getColor(this, R.color.inactive_rb_color);
        // деактивируем нужные стили для остальных радиобоксов
        rb.setBackgroundResource(R.drawable.inactive_rb_bg);
        rb.setTextColor(inactive_rb_color);
    }


    private void initMainViews() {
        loadingProgressBarBox = findViewById(R.id.loadingProgressBarBox);

        rvJokesListBox = findViewById(R.id.rvJokesListBox);
        llOneJokeBox = findViewById(R.id.llOneJokeBox);

        btnNewGenerate = findViewById(R.id.btnNewGenerate);
        btnChangeGenerateRules = findViewById(R.id.btnChangeGenerateRules);

        tvJokeTypeText = findViewById(R.id.tvJokeTypeText);
        tvOneJokeSetup = findViewById(R.id.tvJokeSetup);
        tvOneJokePunchline = findViewById(R.id.tvJokePunchline);
        tvJokeIdText = findViewById(R.id.tvJokeIdText);
        oneJokeLikeBtn = findViewById(R.id.oneJokeLikeBtn);

        changeGenerateRulesBtnIndicator = findViewById(R.id.changeGenerateRulesBtnIndicator);
    }

    private void showJokes(List<JokeItemInfo> jokes) {
        jokesAdapter.setJokes((ArrayList<JokeItemInfo>) jokes);
    }
}