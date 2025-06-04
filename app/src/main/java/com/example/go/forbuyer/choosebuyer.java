package com.example.go.forbuyer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go.Adapters.CustomAdapter;
import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * {@code choosebuyer} is an Activity that allows the user to select a buyer from a list.
 * It fetches buyer data from Firebase, filters it based on currency and amount, and displays it in a ListView.
 * Users can filter buyers by city using a Spinner.
 * It uses {@link CustomAdapter} to display buyers in the ListView.
 * Implements the {@link AppCompatActivity} class.
 */
public class choosebuyer extends AppCompatActivity {
    /**
     * List to store all users fetched from Firebase.
     */
    ArrayList<Users> all = new ArrayList<>();
    /**
     * List to store users filtered by place (city).
     */
    ArrayList<Users> forplace = new ArrayList<>();
    /**
     * Adapter to display the list of users in the ListView.
     */
    private CustomAdapter customAdapter;
    /**
     * ListView to display the list of buyers.
     */
    private ListView listView1;
    /**
     * Intent to navigate to the map activity.
     */
    Intent next;
    /**
     * The selected user
     */
    Users users;
    /**
     * The required string
     */
    String req;
    /**
     * Spinner for selecting a city to filter buyers.
     */
    Spinner places;
    /**
     * Button to trigger filtering by city.
     */
    ImageButton btn;
    /**
     * Adapter for the city spinner.
     */
    ArrayAdapter<CharSequence> dp;

    /**
     * Called when the activity is first created.
     * Initializes the UI components, fetches buyer data from Firebase, and sets up listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choosebuyer);
        listView1 = findViewById(R.id.listView);
        btn = findViewById(R.id.button3);
        places = findViewById(R.id.spinner2);
        dp = ArrayAdapter.createFromResource(choosebuyer.this, R.array.city, android.R.layout.simple_spinner_item);
        places.setAdapter(dp);
        next = new Intent(choosebuyer.this, map.class);
        Intent gi = getIntent();
        String currncy = gi.getStringExtra("currncy");
        int sum = gi.getIntExtra("n", -1);
        req = " " + sum + " " + currncy;
        FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (check(data.getValue(Users.class), currncy, sum)) {
                        all.add(data.getValue(Users.class));
                    }

                }
                customAdapter = new CustomAdapter(choosebuyer.this, all, currncy);
                listView1.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                users = customAdapter.getItem(position);
                showAlertDialog();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!places.getSelectedItem().toString().equals("Choose the city")) {
                    forplace.clear();
                    String city1 = places.getSelectedItem().toString().trim();
                    FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                if (data.getValue(Users.class).getCity().equals(city1) && (check(data.getValue(Users.class), currncy, sum))) {
                                    forplace.add(data.getValue(Users.class));
                                    Log.d("msg", forplace.toString());
                                }

                            }
                            Log.d("msg", forplace.toString());

                            customAdapter = new CustomAdapter(choosebuyer.this, forplace, currncy);
                            customAdapter.notifyDataSetChanged();
                            listView1.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else
                    Toast.makeText(choosebuyer.this, "the city is not chosen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Displays an AlertDialog to confirm navigation to the map activity.
     *
     * <p>When the user clicks 'OK', the activity navigates to {@link map} and passes user information.
     * If 'Cancel' is clicked, the dialog is dismissed.</p>
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GO TO MAP");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                next.putExtra("e", users.getEmail());
                next.putExtra("c", users.getCity());
                next.putExtra("r", req);
                next.putExtra("a", users.getAddress());
                startActivity(next);
                dialog.dismiss();
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

    /**
     * Checks if a user meets the required currency and amount criteria.
     *
     * <p>It verifies whether any of the user's currency types matches the provided currency {@code c}
     * and if the sum of that currency is at least 75% of the required amount {@code n}.</p>
     *
     * @param s The user to check.
     * @param c The required currency type.
     * @param n The required amount.
     * @return {@code true} if the user meets the criteria, {@code false} otherwise.
     * @throws NullPointerException If the user or any of its currency are null.
     */
    public static boolean check(Users s, String c, int n) {
        for (int i = 1; i < s.getCurrency().size(); i++) {
            String type = s.getCurrency().get(i).getType();
            type = type.substring(0, 3);
            if (type.equals(c) && s.getCurrency().get(i).getSum() >= 0.75 * n)
                return true;
        }
        return false;
    }
}