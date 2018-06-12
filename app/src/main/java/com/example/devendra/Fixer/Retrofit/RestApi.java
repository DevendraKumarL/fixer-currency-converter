package com.example.devendra.Fixer.Retrofit;

import com.example.devendra.Fixer.ResponseModels.CurrencyData;
import com.example.devendra.Fixer.ResponseModels.HelloWorldData;

import retrofit2.http.GET;

/**
 * Created by dkumarl on 2/18/2018.
 */

public interface RestApi {

    @GET("/hello/world")
    retrofit2.Call<HelloWorldData> getHelloWorld();

    @GET("/fixer")
    retrofit2.Call<CurrencyData> getCurrencyRates();

}
