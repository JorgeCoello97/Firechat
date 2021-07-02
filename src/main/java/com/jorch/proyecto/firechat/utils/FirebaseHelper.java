package com.jorch.proyecto.firechat.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by JORCH on 19/03/2017.
 */

public class FirebaseHelper {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase firebaseDatabase;
    //AUTHENTICATION
    public static FirebaseAuth getAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }
    public static FirebaseUser getCurrentUser(){
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser();
    }
    public static void signOut(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    //DATABASE
    public static FirebaseDatabase getDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase;
    }
    public static DatabaseReference getReference(String child){
        firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference(child);
    }
    public static DatabaseReference getRoot(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference();
    }
}
