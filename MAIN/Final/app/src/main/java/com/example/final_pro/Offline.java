package com.example.final_pro;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Offline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
