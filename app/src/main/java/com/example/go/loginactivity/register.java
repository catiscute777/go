package com.example.go.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Register is an Activity responsible for handling the user registration process.
 * It allows users to create a new account by entering their email and password.
 * Upon successful registration, it displays a success message; otherwise, it
 * displays an error message. The activity uses Firebase Authentication for
 * managing user accounts.
 */

public class register extends AppCompatActivity {
    /**
     * EditText field for entering the user's email address.
     */
    private EditText etEmail;
    /**
     * EditText field for entering the user's password.
     */
    private EditText etPassword;
    /**
     * Button to trigger the user registration process.
     */
    private Button btnSignUp;
    /**
     * Instance of FirebaseAuth for handling user authentication.
     */
    FirebaseAuth firstAuth;


    /**
     * Called when the activity is first created.
     * Initializes the layout, enables edge-to-edge display, initializes Firebase,
     * sets up the EditText and Button components, and sets a click listener on
     * the registration button to handle user registration.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        etEmail = findViewById(R.id.etEmail);
        FirebaseApp firstApp = FirebaseApp.getInstance("firstProject");
        firstAuth = FirebaseAuth.getInstance(firstApp);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnLogin);
        btnSignUp.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v)
        { String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty())
            { Toast.makeText(register.this, "Email and Password are required", Toast.LENGTH_SHORT).show(); }
            else {
                registeruser(email, password); }
        }


        }); }

    /**
     * Registers a new user with the provided email and password.
     * This method utilizes Firebase Authentication to create a new user.
     * It displays a success or failure message based on the outcome of the
     * registration attempt.
     *
     * @param email    The email address of the user.
     * @param password The password for the user's account.
     */
    private void registeruser(String email, String password) {
        firstAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            finish(); }
                        else { Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show(); } }


                });
    }
}
