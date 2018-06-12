package com.example.devendra.Fixer.Requests;

import com.example.devendra.Fixer.ResponseModels.CurrencyData;
import com.example.devendra.Fixer.ResponseModels.Generic.GenericCallback;
import com.example.devendra.Fixer.ResponseModels.Generic.GenericResponse;
import com.example.devendra.Fixer.ResponseModels.HelloWorldData;
import com.example.devendra.Fixer.Retrofit.RestApi;
import com.example.devendra.Fixer.Retrofit.RetrofitProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dkumarl on 2/25/2018.
 */

public class ServerRequests {

    private final int FAILURE_ERROR = 1221;

    private RestApi restApi;

    private static ServerRequests serverRequests = new ServerRequests();

    private ServerRequests() {
        restApi = RetrofitProvider.getRetrofitProviderInstance().getRestApi();
    }

    public static ServerRequests getInstance() {
        return serverRequests;
    }

    public void getHelloWorld(
            final GenericCallback<GenericResponse<HelloWorldData>> HelloWorldResponse) {

        Call<HelloWorldData> helloWorldDataCall = restApi.getHelloWorld();

        final GenericResponse helloWorldServerResponse = new GenericResponse();

        helloWorldDataCall.enqueue(new Callback<HelloWorldData>() {
            @Override
            public void onResponse(Call<HelloWorldData> call, Response<HelloWorldData> response) {
                if (response.isSuccessful()) {
                    helloWorldServerResponse.setStatus(true);
                    helloWorldServerResponse.setResponseObj(response.body());
                    helloWorldServerResponse.setErrorMessage("");
                    helloWorldServerResponse.setResponseCode(response.code());
                    HelloWorldResponse.success(helloWorldServerResponse);
                    return;
                }

                helloWorldServerResponse.setStatus(false);
                helloWorldServerResponse.setResponseObj(response.errorBody());
                helloWorldServerResponse.setErrorMessage(response.message());
                helloWorldServerResponse.setResponseCode(response.code());
                HelloWorldResponse.failure(helloWorldServerResponse);
            }

            @Override
            public void onFailure(Call<HelloWorldData> call, Throwable t) {
                helloWorldServerResponse.setStatus(false);
                helloWorldServerResponse.setResponseObj(null);
                helloWorldServerResponse.setErrorMessage(t.getMessage());
                helloWorldServerResponse.setResponseCode(FAILURE_ERROR);
                HelloWorldResponse.failure(helloWorldServerResponse);
            }
        });
    }

    public void getCurrencyData(final GenericCallback<GenericResponse<CurrencyData>> CurrencyRatesResponse) {

        Call<CurrencyData> currencyRatesCall = restApi.getCurrencyRates();

        final GenericResponse<CurrencyData> currencyRatesServerResponse = new GenericResponse();

        currencyRatesCall.enqueue(new Callback<CurrencyData>() {
            @Override
            public void onResponse(Call<CurrencyData> call, Response<CurrencyData> response) {
                if (response.isSuccessful()) {
                    currencyRatesServerResponse.setStatus(true);
                    currencyRatesServerResponse.setResponseObj(response.body());
                    currencyRatesServerResponse.setErrorMessage("");
                    currencyRatesServerResponse.setResponseCode(response.code());
                    CurrencyRatesResponse.success(currencyRatesServerResponse);
                    return;
                }

                currencyRatesServerResponse.setStatus(false);
                currencyRatesServerResponse.setResponseObj(response.body());
                currencyRatesServerResponse.setResponseCode(response.code());
                currencyRatesServerResponse.setErrorMessage(response.message());
                CurrencyRatesResponse.failure(currencyRatesServerResponse);
            }

            @Override
            public void onFailure(Call<CurrencyData> call, Throwable t) {
                currencyRatesServerResponse.setStatus(false);
                currencyRatesServerResponse.setResponseObj(null);
                currencyRatesServerResponse.setErrorMessage(t.getMessage());
                currencyRatesServerResponse.setResponseCode(FAILURE_ERROR);
                CurrencyRatesResponse.failure(currencyRatesServerResponse);
            }
        });
    }

}
