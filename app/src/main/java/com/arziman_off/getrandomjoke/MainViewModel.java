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
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel{
    private static final String BASE_URL = "https://official-joke-api.appspot.com/jokes/random";
    private static final String LOG_TAG = "MainViewModel";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SETUP = "setup";
    private static final String KEY_PUNCHLINE = "punchline";
    private static final String KEY_ID = "id";
    private MutableLiveData<JokeItemInfo> jokeItem = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<JokeItemInfo> getJokeItem() {
        return jokeItem;
    }

    public void loadOneNewJoke(){
        Disposable disposable = loadOneNewJokeRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<JokeItemInfo>() {
                            @Override
                            public void accept(JokeItemInfo jokeItemInfo) throws Throwable {
                                jokeItem.setValue(jokeItemInfo);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d(LOG_TAG, "Error " + throwable.getMessage());
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }

    private Single<JokeItemInfo> loadOneNewJokeRx(){
        return Single.fromCallable(new Callable<JokeItemInfo>() {
            @Override
            public JokeItemInfo call() throws Exception {
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
                return new JokeItemInfo(
                        jokeType,
                        jokeSetup,
                        jokePunchline,
                        jokeId
                );
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
