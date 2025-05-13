package com.example.go.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.go.R;
import com.example.go.forseller.allseller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * loginforseller is a Fragment responsible for handling the seller login process.
 * It allows sellers to enter their email and password to log in to their account.
 * Upon successful login, it navigates to the allseller activity; otherwise, it
 * displays an error message. Additionally, it provides a link to the seller registration activity.
 */
public class loginforseller extends Fragment {
    /**
     * Intent used for navigating between activities.
     */
    Intent intt;
    /**
     * TextView to navigate the user to the seller register activity.
     */
    TextView tv;
    /**
     * EditText field for entering the seller's email address.
     */
    private EditText etEmail;
    /**
     * EditText field for entering the seller's password.
     */
    private EditText etPassword;
    /**
     * Button to trigger the seller login process.
     */
    private Button btnLogin;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor
     */
    public loginforseller() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginforseller.
     */
    public static loginforseller newInstance(String param1, String param2) {
        loginforseller fragment = new loginforseller();
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
        View v = inflater.inflate(R.layout.fragment_loginforseller, container, false);
        etEmail = v.findViewById(R.id.etEmail);
        etPassword = v.findViewById(R.id.etPassword);
        btnLogin = v.findViewById(R.id.btnLogin);
        tv = v.findViewById(R.id.registerTextView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), registerforseller.class));
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
     * Logs in a seller with the provided email and password.
     * This method utilizes Firebase Authentication to authenticate the seller.
     * It navigates to the allseller activity upon successful login or displays
     * an error message if the login fails.
     *
     * @param email    The email address of the seller.
     * @param password The password for the seller's account.
     */
    private void loginUser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).
                addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            intt = new Intent(getContext(), allseller.class);
                            Log.d("important", password);
                            intt.putExtra("pas", password);
                            startActivity(intt);
                        } else {
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}