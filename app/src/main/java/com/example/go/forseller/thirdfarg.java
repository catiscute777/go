package com.example.go.forseller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.R;
import com.example.go.Class.text;
import com.example.go.Adapters.textAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass for sellers to view and manage buyer requests.
 * This fragment displays a list of requests received by the seller and provides
 * functionality to "finish" (likely remove) these requests.
 * Use the {@link thirdfarg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thirdfarg extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    Users u; // Represents the currently logged-in seller user
    int t; // Used as an index counter in the ValueEventListener
    DatabaseReference myRef; // Database reference for the current user's data
    textAdapter adapter; // Adapter for the ListView displaying requests
    text message; // Represents a selected request message for action


    private String mParam1; // Holds the password or identifier of the seller

    /**
     * Required empty public constructor.
     */
    public thirdfarg() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1 (likely the seller's password or identifier).
     * @return A new instance of fragment thirdfarg.
     */
    public static thirdfarg newInstance(String param1) {
        thirdfarg fragment = new thirdfarg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is first created.
     * Retrieves arguments passed to the fragment, if any.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * Initializes the view, sets up the ListView and its item click listener,
     * retrieves seller data from Firebase, populates the ListView with requests,
     * and handles the display and action of requests when clicked.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thirdfarg, container, false);
        ListView listView = v.findViewById(R.id.list89);
        FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (data.getValue(Users.class).getPassword().equals(mParam1)) {
                        u = data.getValue(Users.class);
                        adapter = new textAdapter(getContext(), u.getRequests());
                        listView.setAdapter(adapter);
                        myRef = FB.F.getReference("Users/user" + (t + 1));
                    }
                    t++;
                }
                t = 0;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if necessary
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    message = adapter.getItem(i);
                    showalert();}

            }
        });

        return v;
    }

    /**
     * Displays an AlertDialog to confirm finishing (removing) a buyer request.
     * This method presents a dialog with "OK" and "Cancel" options.
     * Selecting "OK" removes the selected request from the user's list and
     * updates the data in Firebase.
     */
    private void showalert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Finish");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        { @Override public void onClick(DialogInterface dialog, int which)
        {
            u.getRequests().remove(message);
            myRef.setValue(u);
            dialog.dismiss(); } });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        { @Override
        public void onClick(DialogInterface dialog, int which)
        {
            // No action needed when "Cancel" is clicked
        } });
        AlertDialog alertDialog = builder.create();
        alertDialog.show(); }
}