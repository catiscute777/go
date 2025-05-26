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
    private String type;
    /**
     * The current sum or amount associated with this currency.
     */
    private int sum;

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
public String getType(){
        return this.type;
}
public int getSum(){
        return this.sum;
}
public void setSum(int s){
        this.sum = s;
}
public void setType(String t){
        this.type = t;

}
}