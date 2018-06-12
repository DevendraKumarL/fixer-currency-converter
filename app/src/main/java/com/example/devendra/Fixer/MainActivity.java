package com.example.devendra.Fixer;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.devendra.Fixer.Requests.ServerRequests;
import com.example.devendra.Fixer.ResponseModels.CurrencyData;
import com.example.devendra.Fixer.ResponseModels.Generic.GenericCallback;
import com.example.devendra.Fixer.ResponseModels.Generic.GenericResponse;
import com.example.devendra.Fixer.ResponseModels.HelloWorldData;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static HelloWorldData helloWorldData;

    private static CurrencyData currencyData;

    private double oneINR_to_USD;
    private double oneUSD_to_INR;

    private boolean usdToInrFlag = true;

    public EditText usdEditText;
    public EditText inrEditText;

    public Button convertButton;
    public Button reverseButton;
    public Button resetButton;

    public ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingProgressBar = findViewById(R.id.loading);
        usdEditText = findViewById(R.id.usd);
        inrEditText = findViewById(R.id.inr);

        convertButton = findViewById(R.id.convert_btn);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertionUSDINR();
            }
        });

        reverseButton = findViewById(R.id.reverse_rate_btn);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverseRateConversion();
            }
        });

        resetButton = findViewById(R.id.reset_rate_btn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetRates();
            }
        });

        getCurrencyData();
    }

    public void resetRates() {
        usdEditText.setEnabled(true);
        usdEditText.setFocusableInTouchMode(true);
        usdEditText.setText(String.valueOf(1));
        usdToInrFlag = true;

        inrEditText.setEnabled(false);
        inrEditText.setFocusableInTouchMode(false);
        inrEditText.setText(String.valueOf(oneUSD_to_INR));
    }

    public void reverseRateConversion() {
        if (usdToInrFlag) {
            usdEditText.setFocusableInTouchMode(false);
            usdEditText.setEnabled(false);

            inrEditText.setEnabled(true);
            inrEditText.setFocusableInTouchMode(true);

            usdToInrFlag = false;
            convertionUSDINR();
            return;
        }
        usdEditText.setFocusableInTouchMode(true);
        usdEditText.setEnabled(true);

        inrEditText.setEnabled(false);
        inrEditText.setFocusableInTouchMode(false);

        usdToInrFlag = true;
        convertionUSDINR();
    }

    public void getCurrencyData() {
        // show progress bar
        loadingProgressBar.setVisibility(View.VISIBLE);

        // disable input usd field, convert button and reverse button
        convertButton.setEnabled(false);
        reverseButton.setEnabled(false);
        usdEditText.setFocusable(false);

        // TODO: check network connectivity

        GenericCallback<GenericResponse<CurrencyData>> currencyRatesListener =
                new GenericCallback<GenericResponse<CurrencyData>>() {
            @Override
            public void success(GenericResponse<CurrencyData> response) {
                loadingProgressBar.setVisibility(View.INVISIBLE);

                // enable back these elements
                convertButton.setEnabled(true);
                reverseButton.setEnabled(true);
                usdEditText.setFocusableInTouchMode(true);

                Log.i("CurrencyRateCallSuccess", "API Response successful!");

                currencyData = response.getResponseObj();
                showCurrencyData();
            }

            @Override
            public void failure(GenericResponse<CurrencyData> response) {
                loadingProgressBar.setVisibility(View.INVISIBLE);

                Log.e("CurrencyRateCallFailure", "API Response failed!");

                showErrorMessage(response);
            }
        };

        ServerRequests.getInstance().getCurrencyData(currencyRatesListener);
    }

    public void showCurrencyData() {
        processCurrency(currencyData.getRates().getUSD(), currencyData.getRates().getINR());
        inrEditText.setText(String.valueOf(oneUSD_to_INR));
    }

    public void processCurrency(double eur_to_usd, double eur_to_inr) {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        // one dollar value in euro
        double one_usd_eur = Double.parseDouble(decimalFormat.format(1.0 / eur_to_usd));

        // one dollar value in rupee
        this.oneUSD_to_INR = Double.parseDouble(decimalFormat.format(eur_to_inr * one_usd_eur));

        // one rupee value in dollar
        this.oneINR_to_USD = Double.parseDouble(decimalFormat.format(1.0 / this.oneUSD_to_INR));
    }

    public void convertionUSDINR() {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        // usd to inr conversion
        if (usdToInrFlag) {
            String usdTextValue = usdEditText.getText().toString();
            double usd = Double.parseDouble(usdTextValue);
            Log.i("usdTextValue: ", usdTextValue);
            if (!usdTextValue.equals("") && usd > 0){
                inrEditText.setText(decimalFormat.format(usd * oneUSD_to_INR));
            }
            return;
        }

        // inr to usd conversion
        String inrTextValue = inrEditText.getText().toString();
        double inr = Double.parseDouble(inrTextValue);
        Log.i("inrTextValue: ", inrTextValue);
        if (!inrTextValue.equals("") && inr > 0) {
            usdEditText.setText(decimalFormat.format(inr * oneINR_to_USD));
        }
    }

    public void getHelloWorld() {
        // show progress bar
        loadingProgressBar.setVisibility(View.VISIBLE);

        // TODO: check network connectivity

        GenericCallback<GenericResponse<HelloWorldData>> helloWordListener =
                new GenericCallback<GenericResponse<HelloWorldData>>() {
            @Override
            public void success(GenericResponse<HelloWorldData> response) {
                loadingProgressBar.setVisibility(View.INVISIBLE);

                Log.i("HelloWorld Call Success", "API Response successful!");

                helloWorldData = response.getResponseObj();
                showHelloWorldResponse();
            }

            @Override
            public void failure(GenericResponse<HelloWorldData> response) {
                loadingProgressBar.setVisibility(View.INVISIBLE);

                Log.e("HelloWorld Call Failure", "API Response failed!");

                showErrorMessage(response);
            }
        };

        ServerRequests.getInstance().getHelloWorld(helloWordListener);
    }

    public void showHelloWorldResponse() {
        // use adpater to show the response message

        Log.i("message:", helloWorldData.getMessage());
    }

    public void showErrorMessage(GenericResponse response) {
        Log.e("isStatus: ", "" + response.isStatus());
        Log.e("errorMessage", response.getErrorMessage());
        Log.e("errorCode", "" + response.getResponseCode());
        Snackbar.make(getWindow().getDecorView().getRootView(),
                "Could not get server response...", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.exit_action) {
            this.finish();
            Log.i("Exit Action: ", "Existing the app");
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
