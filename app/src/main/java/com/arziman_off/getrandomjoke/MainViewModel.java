package com.arziman_off.getrandomjoke;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainViewModel extends AndroidViewModel{
    private static final String BASE_URL = "https://official-joke-api.appspot.com/jokes/random";
    private static final String LOG_TAG = "MainViewModel";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SETUP = "setup";
    private static final String KEY_PUNCHLINE = "punchline";
    private static final String KEY_ID = "id";
    private MutableLiveData<JokeItemInfo> jokeItem = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<JokeItemInfo> getJokeItem() {
        return jokeItem;
    }

    public void loadOneNewJoke(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BASE_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder data = new StringBuilder();
                    String result;
                    do {
                        result = bufferedReader.readLine();
                        if (result != null){
                            data.append(result);
                        }
                    } while (result != null);

                    JSONObject jsonObject = new JSONObject(data.toString());
                    String jokeType = jsonObject.getString(KEY_TYPE);
                    String jokeSetup =  jsonObject.getString(KEY_SETUP);
                    String jokePunchline = jsonObject.getString(KEY_PUNCHLINE);
                    Integer jokeId = jsonObject.getInt(KEY_ID);
                    JokeItemInfo jokeInfo = new JokeItemInfo(
                            jokeType,
                            jokeSetup,
                            jokePunchline,
                            jokeId
                    );
                    jokeItem.postValue(jokeInfo);
                } catch (Exception e){
                    Log.d(LOG_TAG, "Ошибка: " + e.toString());
                }
            }
        }).start();
    }

}
