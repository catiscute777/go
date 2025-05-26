package com.example.go.buildservises;


/**
 * The {@code AddressValidationCallback} interface defines a callback method
 * that will be invoked when the validation of an address is complete.
 * It is used to communicate the validation result back to the caller.
 * */
public interface AddressValidationCallback {
    void onResult(boolean isValid);
}
