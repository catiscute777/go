package com.example.go.forseller;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.go.Adapters.textAdapter;
import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.Class.text;
import com.example.go.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A Fragment that displays a list of messages for a specific user and handles user requests.
 * This fragment retrieves user data from Firebase, displays messages in a ListView,
 * and allows users to interact with messages through an alert dialog.
 */
public class secondfrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Users u;
    private text message;
    private int t = 0;
    private DatabaseReference myRef;
    private textAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;

    /**
     * Required empty public constructor.
     */
    public secondfrag() {
    }

    /**
     * Creates a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1, typically a user identifier.
     * @return A new instance of fragment secondfrag.
     */
    public static secondfrag newInstance(String param1) {
        secondfrag fragment = new secondfrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is first created.
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

        View v = inflater.inflate(R.layout.fragment_secondfrag, container, false);
        ListView listView = v.findViewById(R.id.list56);
        FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (data.getValue(Users.class).getPassword().equals(mParam1)) {

                        u = data.getValue(Users.class);
                        if (u != null) {
                            adapter = new textAdapter(getContext(), u.getMsges());
                            listView.setAdapter(adapter);
                            myRef = FB.F.getReference("Users/user" + (t + 1));
                        }
                    }
                    t++;
                }
                t = 0;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    message = adapter.getItem(i);
                    showalert();
                }

            }
        });

        return v;
    }

    /**
     * Displays an alert dialog for user requests.
     * This method shows a dialog where users can accept or cancel a request.
     * If a request is accepted, the method processes the request, updates user data,
     * and updates the database.
     */
    private void showalert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter the request");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int t = message.getContent().indexOf(":");
                String s = message.getContent().substring(t + 2);
                int n = Integer.parseInt(s.substring(0, s.length() - 4));
                s = s.substring(s.length() - 3);
                Log.d("check", s+","+n);
                for (int i = 1; i < u.getCurrency().size(); i++) {
                    if (u.getCurrency().get(i).getType().substring(0, 3).equals(s)) {
                        if (u.getCurrency().get(i).getSum()> n)
                            u.getCurrency().get(i).setSum(u.getCurrency().get(i).getSum() - n);
                        if (u.getCurrency().get(i).getSum() < n)
                            u.getCurrency().get(i).setSum(0);
                    }
                }
                u.getRequests().add(message);
                u.getMsges().remove(message);
                myRef.setValue(u);
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}