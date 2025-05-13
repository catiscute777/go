package com.example.go.buildservises;
import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


/**
 * The {@code go} class is a custom {@link Application} class used to initialize Firebase.
 * It extends the Android {@link Application} class to provide a global application state
 * and to perform setup operations when the application is first created.
 */
public class go extends Application {
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * This is where you should initialize Firebase for your application.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Configure Firebase options for a specific project.
        FirebaseOptions options1 = new FirebaseOptions.Builder()
                .setApplicationId("1:470479131103:android:e60a0bbe93208f1b34f46a")
                .setApiKey("AIzaSyBeXte-olRATH-H_z6VirNJJ_qyqHg4kdw")
                .setProjectId("loginuser-fd2b0")
                .setDatabaseUrl("https://loginuser-fd2b0-default-rtdb.firebaseio.com/")
                .build();

        // Initialize Firebase with the specified options and a name.
        FirebaseApp.initializeApp(this, options1, "firstProject");

    }
}