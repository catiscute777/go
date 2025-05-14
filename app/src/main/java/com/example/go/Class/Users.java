package com.example.go.Class;

import android.util.Log;

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
    private String email;
    /**
     * The password associated with the user's account.
     */
    private String password;
    /**
     * The city where the user resides.
     */
    private String city;
    /**
     * The full address of the user.
     */
    private String address;
    /**
     * A list of currencies associated with the user.
     */
    private ArrayList<com.example.go.Class.currency> currency;
    /**
     * A list of messages associated with the user.
     */
    private ArrayList<text> msges;
    /**
     * A list of requests associated with the user.
     */
    private ArrayList<text> requests;

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

    public String getEmail()
    {return  this.email;}

    public void setEmail(String email)
    {this.email=email;}

    public String getPassword()
    {return  this.password;}

    public void setPassword(String password)
    {this.password=password;}

    public String getCity()
    {return  this.city;}

    public void setCity(String city)
    {this.city=city;}

    public String getAddress()
    {return  this.address;}

    public void setAddress(String address)
    {this.address=address;}

    public ArrayList<currency> getCurrency()
    {return  this.currency;}

    public void setCurrency(ArrayList<currency> currency)
    {this.currency=currency;}

    public ArrayList<text>getMsges()
    {return  this.msges;}

    public void setMsges(ArrayList<text> msges)
    {this.msges=msges;}

    public ArrayList<text> getRequests()
    {return  this.requests;}

    public void setRequests(ArrayList<text> requests)
    {this.requests=requests;}




}