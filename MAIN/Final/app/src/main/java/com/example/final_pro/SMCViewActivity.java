package com.example.final_pro;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SMCViewActivity extends AppCompatActivity {

    ActionBar actionBar;
    TextView name_sm,phone_sm;
    FloatingActionButton call_SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smcview);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Contact Info");
        }
        Emergency_ContactModel model=new Emergency_ContactModel();
        name_sm=findViewById(R.id.name_pho);
        name_sm.setText(getIntent().getStringExtra("name"));
       // name_sm.setText(getIntent().getStringExtra("name"));

        phone_sm=findViewById(R.id.phone_pho);
        phone_sm.setText(getIntent().getStringExtra("contact"));
        call_SM=findViewById(R.id.phone_call);
        call_SM.setOnClickListener(view -> {
            String phone=phone_sm.getText().toString();
            String s="tel:"+phone;
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(s));
            //startActivity(intent);
            if (ActivityCompat.checkSelfPermission(SMCViewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SMCViewActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 10);
                return;
            } else {
                try {
                    startActivity(intent);
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(SMCViewActivity.this, "number is not found", Toast.LENGTH_SHORT).show();
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
        Intent intent= new Intent(this,Society_ContactActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}