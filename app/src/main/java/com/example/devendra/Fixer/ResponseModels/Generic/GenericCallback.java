package com.example.devendra.Fixer.ResponseModels.Generic;

/**
 * Created by dkumarl on 2/18/2018.
 */

public interface GenericCallback<T> {

    void success(T response);
    void failure(T response);

}
