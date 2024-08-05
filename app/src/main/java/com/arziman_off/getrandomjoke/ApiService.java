package com.arziman_off.getrandomjoke;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {
    int jokesCnt = 15;
    @GET("random_joke")
    Single<JokeItemInfo> generateOneNewJoke();
    @GET("jokes/random/" + jokesCnt)
    Single<List<JokeItemInfo>> generateNewJokesList();
}
