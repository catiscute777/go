package com.example.go.Class;

/**
 * Represents a currency entity with a type and a sum.
 * This class is used to store information about a specific currency,
 * including its type (e.g., "USD", "EUR") and the current sum/amount
 * associated with it.
 */
public class currency {
    /**
     * The type of the currency (e.g., "USD", "EUR", "ILS").
     */
    public String type;
    /**
     * The current sum or amount associated with this currency.
     */
    public int sum;

    /**
     * Default constructor for the currency class.
     * Initializes a new currency object with default values.
     */
    public currency() {
    }

    /**
     * Constructor for the currency class.
     * Initializes a new currency object with the specified type and sum.
     *
     * @param type The type of the currency (e.g., "USD").
     * @param sum  The sum or amount associated with the currency.
     */
    public currency(String type, int sum) {
        this.sum = sum;
        this.type = type;
    }

}