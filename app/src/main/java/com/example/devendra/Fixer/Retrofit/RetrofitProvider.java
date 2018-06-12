package com.example.devendra.Fixer.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dkumarl on 2/18/2018.
 */

public class RetrofitProvider {

    private static final String baseURL = "https://currency-convert.glitch.me";

    private RestApi restApi;

    private static RetrofitProvider retrofitProvider = new RetrofitProvider();

    public static RetrofitProvider getRetrofitProviderInstance() {
        return retrofitProvider;
    }

    public RetrofitProvider() {
        buildRestApi();
    }

    private void buildRestApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public RestApi getRestApi() {
        return restApi;
    }

}
