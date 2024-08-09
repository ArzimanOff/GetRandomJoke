package com.arziman_off.getrandomjoke;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel{
    private static final String LOG_TAG = "MainViewModel";
    private MutableLiveData<JokeItemInfo> jokeItem = new MutableLiveData<>();
    private MutableLiveData<List<JokeItemInfo>> jokeItemsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNowLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingError = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<JokeItemInfo> getJokeItem() {
        return jokeItem;
    }

    public LiveData<List<JokeItemInfo>> getJokeItemsList() {
        return jokeItemsList;
    }

    public LiveData<Boolean> getIsNowLoading() {
        return isNowLoading;
    }

    public LiveData<Boolean> getIsLoadingError() {
        return isLoadingError;
    }

    public void loadOneNewJoke(int id){
        Disposable disposable = loadOneNewJokeRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoadingError.setValue(false);
                        isNowLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isNowLoading.setValue(false);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        isLoadingError.setValue(true);
                    }
                })
                .retry(3)
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

    public void loadListOfJokes(int needJokesCnt){
        Disposable disposable = loadNewJokesListRx(needJokesCnt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoadingError.setValue(false);
                        isNowLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isNowLoading.setValue(false);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        isLoadingError.setValue(true);
                    }
                })
                .subscribe(
                        new Consumer<List<JokeItemInfo>>() {
                            @Override
                            public void accept(List<JokeItemInfo> jokeItems) throws Throwable {
                                jokeItemsList.setValue(jokeItems);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d(LOG_TAG, "Error " + throwable.getMessage());
                            }
                        }
                );
        // для методов loadOneNewJoke и loadListOfJokes
        // возможно в будущем надо будет добавлять в разные compositeDisposable
        compositeDisposable.add(disposable);
    }

    private Single<JokeItemInfo> loadOneNewJokeRx(int id){
        if (id < 0){
            return ApiFactory
                    .getApiService()
                    .generateOneNewJoke();
        } else {
            return ApiFactory
                    .getApiService()
                    .generateOnNewJokeById(id);
        }
    }

    private Single<List<JokeItemInfo>> loadNewJokesListRx(int needJokesCnt){
        return ApiFactory
                .getApiService()
                .generateNewJokesList(needJokesCnt);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
