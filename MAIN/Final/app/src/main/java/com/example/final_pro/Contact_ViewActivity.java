package com.example.final_pro;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contact_ViewActivity extends AppCompatActivity {
ActionBar actionBar;
TextView name_v,phone_v;
FloatingActionButton call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
           actionBar.setTitle("Contact Info");
        }
        name_v=findViewById(R.id.name_phone);
        name_v.setText(getIntent().getStringExtra("name"));

        phone_v=findViewById(R.id.phone_phone);
        phone_v.setText(getIntent().getStringExtra("contact"));
        call=findViewById(R.id.phone_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_v.getText().toString();
                String s = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                if (ActivityCompat.checkSelfPermission(Contact_ViewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Contact_ViewActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 10);
                    return;
                } else {
                    try {
                        startActivity(intent);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(Contact_ViewActivity.this, "number is not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void Load_Settings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String orien=sp.getString("ORIENTATION","false");
        if ("1".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        } else if ("2".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if ("3".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
    }
    @Override
    public void onResume() {
        overridePendingTransition(0,0);
        Load_Settings();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,EmergencyActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}