package com.example.go.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.go.Class.text;
import com.example.go.R;

import java.util.ArrayList;

/**
 * {@code textAdapter} is a custom {@link ArrayAdapter} used to display a list of {@link text} objects
 * in a {@link android.widget.ListView} or similar view. It handles the creation and binding of
 * views for each item in the list, ensuring that each item displays the messageText.
 *
 * <p>This adapter inflates the `listtext` layout for each item, which is expected to contain a
 * {@link TextView} with the ID `emailTextView`. The adapter then sets the `messageText` of the
 * corresponding {@link text} object to this TextView.</p>
 *
 * <p>The Adapter assumes that {@link text} objects have a public member
 * variable called messageText.</p>
 */
public class textAdapter extends ArrayAdapter<text> {

    /**
     * Constructs a new {@code textAdapter}.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param text    An {@link ArrayList} of {@link text} objects to be displayed.
     * @throws NullPointerException if the context or the text arrayList is null
     */
    public textAdapter(@NonNull Context context, ArrayList<text> text) {
        super(context, R.layout.listtext, text);
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     * @throws IndexOutOfBoundsException if the index position is outside the bounds of the arrayList.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        text text = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listtext, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.emailTextView);
        if (text != null) {
            tvName.setText(text.getContent());
        }
        return convertView;
    }
}