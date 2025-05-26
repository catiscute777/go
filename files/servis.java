package com.example.go.buildservises;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Servis is an interface that defines the API endpoints for fetching exchange rate data.
 * It uses Retrofit annotations to specify how HTTP requests should be made to
 * retrieve the latest exchange rates.
 */
public interface servis {

    /**
     * Fetches the latest exchange rates from the API.
     * This method sends a GET request to the "latest" endpoint and requires an
     * access key as a query parameter. The response is expected to be a JSON
     * object containing the exchange rate data.
     *
     * @param apiKey The API access key required for authentication.
     * @return A Call object that can be used to make the network request
     * and receive the JsonObject response asynchronously.
     */
    @GET("latest")
    Call<JsonObject> getExchangeRates(@Query("access_key") String apiKey);
}