package com.example.devendra.Fixer.ResponseModels;

/**
 * Created by dkumarl on 2/25/2018.
 */

public class CurrencyData {

    private String base;

    private String date;

    private String timestamp;

    private String success;

    private Rates rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

}
