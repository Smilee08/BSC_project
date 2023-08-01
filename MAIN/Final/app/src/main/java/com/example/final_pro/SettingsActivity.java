package com.example.final_pro;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Settings");

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.example.final_pro.SettingsFragment()).commit();

    }

    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();

}