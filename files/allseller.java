package com.example.go.forseller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.go.R;
import com.google.android.material.tabs.TabLayout;

/**
 * Allseller is an AppCompatActivity that serves as the main interface
 * for sellers. It hosts multiple fragments within a FrameLayout and
 * allows navigation between these fragments using a TabLayout.
 * This activity is responsible for receiving and passing user data
 * (specifically a password or identifier) to the fragments.
 */
public class allseller extends AppCompatActivity {
    /**
     * The FrameLayout that acts as a container for the different fragments.
     */
    FrameLayout fram;
    /**
     * An Intent object, though not directly used in the provided snippet
     * based on the variable name.
     */
    Intent gi;
    /**
     * The TabLayout used to switch between different seller-related fragments.
     */
    TabLayout tab;

    /**
     * Called when the activity is first created.
     * Initializes the activity, sets up edge-to-edge display, retrieves
     * user data from the incoming Intent, sets up the TabLayout and
     * FrameLayout, and initializes the first fragment to be displayed.
     * It also sets up a listener for TabLayout selections to handle
     * fragment switching.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_allseller);

        Intent intent = getIntent();
        String st = intent.getStringExtra("pas");
        String g = st;

        tab = findViewById(R.id.tablel);
        fram = findViewById(R.id.frame);

        Firstfrag firstFragment = Firstfrag.newInstance(g);
        secondfrag fragmentB = secondfrag.newInstance(g);
        thirdfarg fargment = thirdfarg.newInstance(g);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, firstFragment)
                .addToBackStack(null).commit();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        t.replace(R.id.frame, firstFragment);
                        break;
                    case 1:
                        t.replace(R.id.frame, fragmentB);
                        break;
                    case 2:
                        t.replace(R.id.frame, fargment);
                        break;
                }
                t.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No action needed when a tab is unselected in this implementation.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No action needed when a tab is reselected in this implementation.
            }
        });
    }
}