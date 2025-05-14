package com.example.go.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.go.R;
import com.example.go.Class.Users;

import java.util.ArrayList;

/**
 * CustomAdapter is a specialized ArrayAdapter designed to display a list of Users
 * in a custom layout. It fetches user data and populates a ListView or similar
 * view with information about each user, including their email, address, and a
 * calculated currency sum based on a specific currency type.
 */
public class CustomAdapter extends ArrayAdapter<Users> {

    /**
     * The type of currency to filter and display.
     */
    private final String type1;

    /**
     * Constructor for CustomAdapter.
     *
     * @param context The current context.
     * @param users   The ArrayList of Users objects to display.
     * @param type1   The type of currency to filter by (e.g., "USD", "EUR").
     */
    public CustomAdapter(@NonNull Context context, ArrayList<Users> users, String type1) {
        super(context, R.layout.list, users);
        this.type1 = type1;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Users users = getItem(position);
        String currency = "";
        // Loops through each currency entry to find a match
        for (int i = 1; i < users.getCurrency().size(); i++) {
            String type = users.getCurrency().get(i).getType();
            type = type.substring(0, 3);
            //Check if the currency matches the one requested
            if (type.equals(this.type1)) {
                currency = users.getCurrency().get(i).getSum() + " " + type;
            }
        }
        // Inflate the view if it's null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listusers, parent, false);
        }
        // Get view references
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView addressTextView = convertView.findViewById(R.id.addressTextView);
        TextView sum = convertView.findViewById(R.id.sumTextView);
        // Populate the view with user data
        emailTextView.setText(users.getEmail());
        addressTextView.setText(users.getCity() + " " + users.getAddress());
        sum.setText(currency);
        return convertView;
    }
}