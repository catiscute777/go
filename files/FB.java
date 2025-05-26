package com.example.go.Class;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * FB is a utility class that provides access to the Firebase Realtime Database.
 * It initializes and holds references to the Firebase database root and a specific
 * node for buyer data within the database. This class uses static members to provide
 * easy access to these database references from any part of the application.
 */
public class FB {

    /**
     * A static instance of FirebaseDatabase, providing access to the Firebase Realtime Database.
     */
    public static FirebaseDatabase F = FirebaseDatabase.getInstance();

    /**
     * A static DatabaseReference pointing to the "Users" node in the Firebase Realtime Database.
     * This reference can be used to read or write data related to users in the database.
     */
    public static DatabaseReference buyer = F.getReference("Users");
}