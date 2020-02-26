package com.example.learn.Network;

import com.example.learn.bean.ModelList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PolyApi {
    @Headers({"Accept: application/json"})
    @GET("assets")
    Call<ModelList> getModel(
            @Query("keywords") String keyword ,
            @Query("key") String api_key

    );
}
