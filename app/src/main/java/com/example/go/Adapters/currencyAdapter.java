package com.example.go.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.go.Class.currency;
import com.example.go.R;

import java.util.ArrayList;

/**
 * {@code currencyAdapter} is an ArrayAdapter that provides views for {@link currency} objects
 * within a list or spinner. It inflates the layout specified by {@link R.layout#list} for each item
 * and binds the data to the TextViews within the layout.
 */
public class currencyAdapter extends ArrayAdapter<currency> {
    /**
     * Constructs a {@code currencyAdapter} with the given context and list of currency objects.
     *
     * @param context  The current context.
     * @param currency The list of {@link currency} objects to display.
     */
    public currencyAdapter(Context context, ArrayList<currency> currency) {
        super(context, 0, currency);
    }

    /**
     * Provides a view for an object in the data set.
     *
     * <p>This method recycles views when possible to improve performance. It inflates a new view
     * if no view can be recycled. Once a view is obtained, it retrieves the {@link currency}
     * object at the specified position and sets the text of the TextViews with the currency type
     * and sum.</p>
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     * @throws NullPointerException If the currency object at the position is null.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        currency currency = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvAge = convertView.findViewById(R.id.tvAge);
        if (currency != null) {
            tvName.setText(currency.getType());
            tvAge.setText(String.valueOf(currency.getSum()));
        }
        return convertView;
    }
}