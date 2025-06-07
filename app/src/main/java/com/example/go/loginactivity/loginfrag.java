package com.example.go.loginactivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go.forbuyer.MainActivity;
import com.example.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Loginfrag is a Fragment responsible for handling the user login process.
 * It allows users to enter their email and password to log in to their account.
 * Upon successful login, it navigates to the main activity; otherwise, it
 * displays an error message.
 */
public class loginfrag extends Fragment {
    /**
     * EditText field for entering the user's email address.
     */
    private EditText etEmail;
    /**
     * EditText field for entering the user's password.
     */
    private EditText etPassword;
    /**
     * Button to trigger the user login process.
     */
    private Button btnLogin;
    /**
     * TextView to navigate the user to the register activity
     */
    TextView tv;
    /**
     * Instance of FirebaseAuth for handling user authentication.
     */
    FirebaseAuth first;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor
     */
    public loginfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginfrag.
     */
    public static loginfrag newInstance(String param1, String param2) {
        loginfrag fragment = new loginfrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        View v = inflater.inflate(R.layout.fragment_loginfrag, container, false);
        etEmail = v.findViewById(R.id.etEmail);
        tv = v.findViewById(R.id.registerTextView);
        etPassword = v.findViewById(R.id.etPassword);
        FirebaseApp Firstapp = FirebaseApp.getInstance("firstProject");
        first = FirebaseAuth.getInstance(Firstapp);
        btnLogin = v.findViewById(R.id.btnLogin);
        getpremision();
        etEmail.setOnCreateContextMenuListener(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), register.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "Email and Password are required", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }


            }

        });
        return v;
    }

    /**
     * Requests the necessary permissions for POST_NOTIFICATIONS if running on Android TIRAMISU or higher.
     * This method checks the permission status and prompts the user for permission if not already granted.
     */
    private void getpremision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS}, 1);
            }
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
            }
        }
    }

    /**
     * Logs in a user with the provided email and password.
     * This method utilizes Firebase Authentication to authenticate the user.
     * It navigates to the main activity upon successful login or displays an
     * error message if the login fails.
     *
     * @param email    The email address of the user.
     * @param password The password for the user's account.
     */
    private void loginUser(String email, String password) {
        first.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}