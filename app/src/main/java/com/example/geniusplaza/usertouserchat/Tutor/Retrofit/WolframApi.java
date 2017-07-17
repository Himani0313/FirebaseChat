package com.example.geniusplaza.usertouserchat.Tutor.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by geniusplaza on 7/17/17.
 */

public interface WolframApi {

    @GET("query?format=plaintext&output=JSON&appid=WJ3YTH-Q73E3R3VYY&")
    Observable<com.example.geniusplaza.usertouserchat.Tutor.POJO.Query> postGetQueryResult(@retrofit2.http.Query(value = "input", encoded = true) String input);
}
