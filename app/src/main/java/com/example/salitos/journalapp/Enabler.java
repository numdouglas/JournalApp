package com.example.salitos.journalapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
//The FirebaseDatabase must be initialized as the first instance and only once. We allot it an Activity class so it will only need to be
//declared the first time the app starts
public class Enabler extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }}
