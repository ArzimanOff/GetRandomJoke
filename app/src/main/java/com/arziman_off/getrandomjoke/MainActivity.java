package com.arziman_off.getrandomjoke;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://official-joke-api.appspot.com/jokes/random";
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadOneNewJoke();
    }

    private void loadOneNewJoke(){
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
                    Log.d(LOG_TAG, data.toString());
                } catch (Exception e){
                    Log.d(LOG_TAG, "Ошибка: " + e.toString());
                }
            }
        }).start();
    }
}