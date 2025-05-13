package com.example.go.Class;

import android.util.Log;

import com.example.go.text;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * {@code Users} represents a user in the system. It stores user-related information such as
 * email, password, city, address, a list of currencies, messages, and requests.
 * It implements {@link Serializable} to enable object serialization.
 */
public class Users implements Serializable {
    /**
     * The email address of the user.
     */
    public String email;
    /**
     * The password associated with the user's account.
     */
    public String password;
    /**
     * The city where the user resides.
     */
    public String city;
    /**
     * The full address of the user.
     */
    public String address;
    /**
     * A list of currencies associated with the user.
     */
    public ArrayList<com.example.go.Class.currency> currency;
    /**
     * A list of messages associated with the user.
     */
    public ArrayList<text> msges;
    /**
     * A list of requests associated with the user.
     */
    public ArrayList<text> requests;

    /**
     * Constructs a new {@code Users} object with default values.
     */
    public Users() {
    }

    /**
     * Constructs a new {@code Users} object with the specified email, password, city, and address.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @param city     The user's city.
     * @param address  The user's address.
     */
    public Users(String email, String password, String city, String address) {
        this.email = email;
        this.password = password;
        this.currency = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.msges = new ArrayList<>();
        this.address = address;
        this.city = city;

    }

    /**
     * Adds a currency to the user's list of currencies.
     *
     * @param c The currency object to add.
     * @throws NullPointerException if the currencyList attribute is null.
     */
    public void addcurrency(currency c) {
        if (this.currency != null) {
            this.currency.add(c);
            Log.d("Users", "Added currency: " + c);
        } else {
            Log.e("Users", "currencyList is null");
        }
    }


}