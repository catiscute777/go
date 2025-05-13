package com.example.go.loginactivity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.Class.currency;
import com.example.go.R;
import com.example.go.text;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The {@code registerforseller} class manages the registration process for new sellers in the application.
 * It provides functionality to collect the seller's email, password, address, and city.
 * It validates the provided address and ensures there is no existing buyer at the same location.
 * It also handles Firebase authentication and stores the new seller's data in the Firebase database.
 */

public class registerforseller extends AppCompatActivity {
    private EditText etEmail; private EditText etPassword;
    private Button btnSignUp;ArrayAdapter<CharSequence> dp;
    Spinner spin;EditText eda;boolean working = false;
    ArrayList<Users> us = new ArrayList<>();int l;

    /**
     * Initializes the activity. Sets up the layout, UI components, and listeners for the registration process.
     * It also retrieves existing buyer data from Firebase to check for duplicate addresses.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registerforseller);
        spin = findViewById(R.id.spinner3);
        eda = findViewById(R.id.editTextTextPostalAddress);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btn);
        working = false;
        dp = ArrayAdapter.createFromResource(registerforseller.this, R.array.city, android.R.layout.simple_spinner_item);
        spin.setAdapter(dp);
        FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    us.add(data.getValue(Users.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        l= us.size();
        btnSignUp.setOnClickListener(new View.OnClickListener()
        { @Override
        public void onClick(View v)
        { Log.d("msg","working plz");
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String adress = eda.getText().toString().trim();
            String City = spin.getSelectedItem().toString().trim();

            if (!email.isEmpty() && !password.isEmpty() && !adress.isEmpty() && !City.isEmpty())
            {
                try {
                    if(checkingadress(adress, City)){
                        registeruser(email, password,adress, City);

                    }
                    else {
                        Toast.makeText(registerforseller.this,"the address is wrong or there is a buyer in this place",Toast.LENGTH_SHORT).show();}

                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

            }
        else
                Toast.makeText(registerforseller.this, "all the componnets are requird ", Toast.LENGTH_SHORT).show();
        }



        }); }

    /**
     * Checks if the provided address is valid and if there is no existing buyer at the same address.
     *
     * @param address The address to check.
     * @param City    The city of the address.
     * @return {@code true} if the address is valid and unique, {@code false} otherwise.
     * @throws UnsupportedEncodingException If there is an issue encoding the address for the API request.
     */
    private boolean checkingadress(String address,String City) throws UnsupportedEncodingException {
        working = false;
        String addressstr = address+", "+City+", Israel";
        if(addressreal(addressstr)){
        for(int i =0 ; i<us.size();i++){
            String address1 = us.get(i).address+", "+us.get(i).city+", Israel";
            Log.d("msg",address1);
            Log.d("cheking",address);
            if(addressstr.equals(address1))
            {Toast.makeText(registerforseller.this, "There is a buyer in this place", Toast.LENGTH_SHORT).show();
            return false;}
        }
        return true;}
        Log.d("strange","don't work plz");
       return false;
    }



    /**
     * Checks if an address is real by using the Google Maps Geocoding API.
     *
     * @param address The address to check for validity.
     * @return {@code true} if the address is real and valid, {@code false} otherwise.
     * @throws UnsupportedEncodingException If there is an issue encoding the address for the API request.
     */

    private boolean addressreal(String address) throws UnsupportedEncodingException {
        working = false;
        OkHttpClient client = new OkHttpClient();
        String formattedAddress = address.replace(" ", "+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+formattedAddress+"&key=AIzaSyBnimj_K9I8GFNHCjFFF1v0bEI2Qtk--aA";
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("godf",""+response);
                    String responseData = response.body().string();
                    Log.d("go",""+responseData);
                   try {
                       JSONObject json = new JSONObject(responseData);
                       JSONArray results = json.getJSONArray("results");
                       Log.d("sdf",""+results);
                       if (results.length() > 0) {
                       JSONObject firstResult = results.getJSONObject(0);
                       if (!firstResult.has("partial_match")) {
                           working = true;
                       }
                       }
                   }catch (JSONException e) {
                       throw new RuntimeException(e);
                   }}
            }
        });
        return working;
    }

/**
  *If the registration is successful Registers a new user with Firebase Authentication and adds their
  *data to the Firebase database. */


    private void registeruser(String email, String password ,String address, String City) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(registerforseller.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            currency n = new currency("Here is the collection of the currency",-1);
                            text t = new text("t","this is a message");
                            Users buy = new Users(email,password,City,address);
                            buy.addcurrency(n);
                            buy.msges.add(t);
                            buy.requests.add(t);
                            FB.buyer.child("user"+(us.size()+1)).setValue(buy);
                            finish(); }
                        else { Toast.makeText(registerforseller.this, "Registration Failed", Toast.LENGTH_SHORT).show(); } }

                });
    }
}