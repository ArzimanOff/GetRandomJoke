package com.arziman_off.getrandomjoke;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("random_joke")
    Single<JokeItemInfo> generateOneNewJoke();
    @GET("jokes/{id}")
    Single<JokeItemInfo> generateOnNewJokeById(@Path("id") int jokeId);
    @GET("jokes/random/{count}")
    Single<List<JokeItemInfo>> generateNewJokesList(@Path("count") int jokesCnt);

}
