package com.example.devendra.Fixer.ResponseModels.Generic;

/**
 * Created by dkumarl on 2/18/2018.
 */

public class GenericResponse<T> {

    private boolean status;
    private String errorMessage;
    private int responseCode;
    private T responseObj;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public T getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(T responseObj) {
        this.responseObj = responseObj;
    }

}
