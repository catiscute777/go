package com.example.go.buildservises;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The {@code build} class is a utility class responsible for providing a singleton instance of
 * {@link Retrofit} for network operations. It is designed to be used across the application
 * to manage and centralize the configuration of the Retrofit client.
 */
public class build {
    private static Retrofit retrofit;

    /**
     * Returns a singleton instance of the {@link Retrofit} client.
     * If an instance does not already exist, it creates a new one configured with a base URL and
     * a Gson converter factory.
     *
     * @return A configured instance of {@link Retrofit}.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.exchangeratesapi.io/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}