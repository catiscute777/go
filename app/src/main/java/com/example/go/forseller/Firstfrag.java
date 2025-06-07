package com.example.go.forseller;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.go.Adapters.currencyAdapter;
import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.Class.currency;
import com.example.go.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass for sellers to manage their currencies.
 * This fragment displays a list of currencies held by the seller and provides
 * functionality to add, edit, and delete currency entries.
 * Use the {@link Firstfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Firstfrag extends Fragment {

Intent gi;int in;
    Users u;
    currency currncy;
    AlertDialog.Builder alrt;private ArrayList<currency> List;
    DatabaseReference myRef;
    currencyAdapter adapter;ListView listView;
    int t;Users k;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1; // Holds the password or identifier of the seller



    /**
     * Required empty public constructor.
     */
    public Firstfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Firstfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Firstfrag newInstance(String param1) {
        Firstfrag fragment = new Firstfrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        setRetainInstance(true);
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * Initializes the view, sets up the ListView and its item click listener,
     * retrieves seller data from Firebase, populates the ListView, and
     * sets up the click listener for the "Add Currency" button.
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
        View v= inflater.inflate(R.layout.fragment_firstfrag, container, false);
        Button btn = v.findViewById(R.id.button4);
         ListView listView = v.findViewById(R.id.list6);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >0){
                    currncy = adapter.getItem(i);
                    EditAlertDialog(currncy);}
            }


        });


        FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){


                    if(data.getValue(Users.class).getPassword().equals(mParam1)){
                        u = data.getValue(Users.class);
                        k = u;
                        adapter = new currencyAdapter(getContext(), u.getCurrency());
                        listView.setAdapter(adapter);
                        t= in;
                        myRef = FB.F.getReference("Users/user"+(t+1));
                    }
                    in++;
                }
                in =0;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (k !=null){
            adapter.notifyDataSetChanged();

        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showalert();
            }
        }
        );

        return v;}


    /**
     * Displays an AlertDialog to add a new currency entry.
     * This method inflates a custom layout for the dialog, sets up a spinner
     * for currency selection and an EditText for the sum, and defines
     * the actions for the positive ("Finish") and negative ("Cancel") buttons.
     * It then creates and shows the AlertDialog.
     * If the selected currency is not already in the seller's currency list,
     */
    private void showalert() {
        final LayoutInflater[] inflater = {getLayoutInflater()};
        View alertLayout = inflater[0].inflate(R.layout.alertt, null);
        EditText sum = alertLayout.findViewById(R.id.editTextNumber2);
        Spinner spin = alertLayout.findViewById(R.id.spinnerOptions);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.currency, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter1);
        alrt = new AlertDialog.Builder(getContext());
        alrt.setTitle("Add currency");
        alrt.setView(alertLayout);
        alrt.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String selectedOption = spin.getSelectedItem().toString();
               if (!sum.getText().toString().isEmpty()){
                int ed = parseInt(sum.getText().toString());
               if(!selectedOption.isEmpty()){
               if(!contains(selectedOption)){
                   if (ed > 1)
                       place(selectedOption,ed);
               }
               else
                   Toast.makeText(getContext(), "this currency already exists", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getContext(), "Please enter the sum", Toast.LENGTH_SHORT).show();}
            }
        });

        alrt.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = alrt.create();
        alertDialog.show();
    }
    /**
     * Checks if a currency with the given type already exists in the seller's currency list.
     *
     * @param s The type of the currency to check.
     * @return True if the currency exists, false otherwise.*/

    public boolean contains(String s){
        for(int i=1;i<u.getCurrency().size();i++)
            if(u.getCurrency().get(i).getType().equals(s))
                return true;
        return false;
    }
    /**
     * Displays an AlertDialog to edit or delete an existing currency entry.
     * This method provides options to edit the currency's sum or delete the entry
     * from the seller's currency list. It inflates a separate layout for the
     * edit functionality.
     *
     * @param s The currency object to be edited or deleted.
     */

    private void EditAlertDialog(currency s) {
        AlertDialog.Builder edit = new AlertDialog.Builder(getContext());
        edit.setTitle("Edit Currency");
        edit.setPositiveButton("Edit the currency", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alertedit, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView).setTitle("Edit the currency");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText editText = dialogView.findViewById(R.id.editTextNumber4);
                        String userInput = editText.getText().toString();
                        if (!userInput.isEmpty()) {
                        int sum1 = parseInt(userInput);
                        if(sum1>1) {
                            s.setSum(sum1);
                            for (int y = 0; y < u.getCurrency().size(); y++) {
                                if (u.getCurrency().get(y).equals(s.getType()))
                                    u.getCurrency().set(y, s);
                            }

                            getActivity().runOnUiThread(() -> {
                                adapter.notifyDataSetChanged();
                            });
                            myRef.setValue(u);

                        }else Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();}
                    else Toast.makeText(getContext(), "Please enter the new sum", Toast.LENGTH_SHORT).show();}


                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();;

            }
        });
        edit.setNegativeButton("Delete the currency", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                u.getCurrency().remove(s);
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged(); });
                myRef.setValue(u);
            }
        });
        edit.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert1 = edit.create();
        alert1.show();;
    }

    /** this function enter a new currncy that the seller enterd to the currncy
     * list of the seller and updating the firebase and the listview.  */
    public void place(String selc,int num ){
        currency n = new currency(selc,num);
        u.addcurrency(n);
        getActivity().runOnUiThread(() -> {
            adapter.notifyDataSetChanged(); });
        myRef.setValue(u);
    }

}