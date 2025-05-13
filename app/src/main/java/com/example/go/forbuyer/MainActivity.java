package com.example.go.forbuyer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go.R;
import com.example.go.buildservises.build;
import com.example.go.buildservises.servis;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainActivity is the primary activity for the buyer's interface.
 * It allows users to input an amount and select a currency to
 * calculate an exchange rate result using a third-party API.
 * Upon successful calculation, it enables a button to proceed
 * to a buyer selection screen.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * ArrayAdapter for populating the currency selection spinner.
     */
    ArrayAdapter<CharSequence> dp;
    /**
     * Button, though not directly used in the current implementation
     * based on the provided code snippet.
     */
    Button b; // Unused in the provided snippet
    /**
     * EditText for the user to input the amount for exchange.
     */
    EditText sum;
    /**
     * Button to proceed to the buyer selection screen after currency conversion.
     */
    Button bt;
    /**
     * TextView to display the calculated exchange rate result.
     */
    TextView result;
    /**
     * Spinner for the user to select the target currency.
     */
    Spinner change;

    /**
     * Called when the activity is first created.
     * This is where the activity is initialized, the layout is set,
     * and UI elements are wired up. It also sets up the currency spinner
     * and the click listeners for the buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        change = findViewById(R.id.spinner);
        bt = findViewById(R.id.button2);
        result = findViewById(R.id.textView);
        sum = findViewById(R.id.editTextNumber);

        bt.setVisibility(View.INVISIBLE);

        dp = ArrayAdapter.createFromResource(MainActivity.this, R.array.currency, android.R.layout.simple_spinner_item);
        change.setAdapter(dp);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(sum.getText().toString());
                String choosencurrncy = change.getSelectedItem().toString();
                choosencurrncy = choosencurrncy.substring(0, 3);
                Intent in = new Intent(MainActivity.this, choosebuyer.class);
                in.putExtra("currncy", choosencurrncy);
                in.putExtra("n", amount);
                startActivity(in);
            }
        });
    }

    /**
     * Handles the action to perform currency exchange.
     * This method is likely triggered by a button click (though the button itself
     * is not defined in the provided snippet as `doo`). It fetches exchange rates
     * from an external API using Retrofit, calculates the converted amount based
     * on the selected currency and input amount, displays the result, and
     * makes the buyer selection button visible.
     *
     * @param view The view that triggered this method call.
     */
    public void doo(View view) {
        servis retrofitInterface = build.getRetrofitInstance().create(servis.class);
        Call<JsonObject> call = retrofitInterface.getExchangeRates("f87491b58ea3fa2f8ef328a73082fb0b");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject res = response.body();
                    JsonObject rates = res.getAsJsonObject("rates");
                    Log.d("work", "" + rates);
                    String choosencurrncy = change.getSelectedItem().toString();
                    choosencurrncy = choosencurrncy.substring(0, 3);
                    String fromRate = rates.get(choosencurrncy).getAsString();
                    Log.d("work", fromRate);
                    double go = Double.parseDouble(fromRate);
                    double base = Double.parseDouble(rates.get("ILS").getAsString());
                    int amount = Integer.parseInt(sum.getText().toString());
                    double result1 = base * amount * (1 / go);
                    result.setText("result:" + result1);
                    bt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                result.setText("we have a problem! try again");
            }
        });
    }
}