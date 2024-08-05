package com.arziman_off.getrandomjoke;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("random_joke")
    Single<JokeItemInfo> generateOneNewJoke();
}
