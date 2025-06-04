package com.example.go.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.go.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

/**
 * LoginActivity is the entry point of the application's authentication flow.
 * It provides a user interface for users to log in, either as a regular user or as a seller.
 * This activity utilizes fragments to manage different login views, and it allows switching
 * between these views using a TabLayout.
 */
public class LoginActivity extends AppCompatActivity  {
    /**
     * The FrameLayout used to hold the login fragments.
     */
    FrameLayout fram;
    /**
     * The TabLayout for switching between different login views (e.g., regular user, seller).
     */
    TabLayout tab;
    /**
     * The EditText for the email input.
     */
    private EditText etEmail;
    /**
     * The EditText for the password input.
     */
    private EditText etPassword;
    /**
     * The Button used to perform the login action.
     */
    private Button btnLogin;
    /**
     * The intent for navigating to another activity.
     */
    Intent in;

    /**
     * Called when the activity is first created.
     * Initializes the layout, enables edge-to-edge display, initializes Firebase,
     * and sets up the TabLayout and FrameLayout for managing login fragments.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
        FirebaseApp.initializeApp(this);
        tab = findViewById(R.id.tablel);
        fram = findViewById(R.id.frame);
        loginfrag loginfrag = new loginfrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, loginfrag)
                .addToBackStack(null).commit();
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment frag = null;
                switch (tab.getPosition()) {
                    case 0:
                        frag = new loginfrag();
                        break;
                    case 1:
                        frag = new loginforseller();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, frag).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}