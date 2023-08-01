package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Vendors_FormActivity extends AppCompatActivity {
    TextView name, type,days,contactno;
    Button submit,back;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_form);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Vendors Details");
        }

        name = findViewById(R.id.name_del);
        type = findViewById(R.id.type_Del);
        days = findViewById(R.id.rec_del);
        contactno = findViewById(R.id.contact_del);


        submit = (Button) findViewById(R.id.submit_delivery);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { processinsert();
            }
        });
    }

    private void processinsert() {
        String n = name.getText().toString();
        String d = days.getText().toString();
        String cn = contactno.getText().toString();
        String ty = type.getText().toString();

        if (TextUtils.isEmpty(n)) {
            name.setError("Please fill the name field");
        }
        if (TextUtils.isEmpty(d)) {
            days.setError("Please fill the days field");
        }
        if (TextUtils.isEmpty(cn)) {
            contactno.setError("Please fill the contactno field");
        }
        if (TextUtils.isEmpty(ty)) {
            type.setError("Please fill the type field");
        }

        if (n.isEmpty() && d.isEmpty() && cn.isEmpty() && ty.isEmpty()){
            Toast.makeText(Vendors_FormActivity.this,"Enter the data",Toast.LENGTH_SHORT).show();
        }
        else{
        Map<String, Object> map = new HashMap<>();
        map.put("name", n);
        map.put("days", d);
        map.put("contactno", cn);
        map.put("type", ty);


        FirebaseDatabase.getInstance().getReference().child("Users4").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                        days.setText("");
                        contactno.setText("");
                        type.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Vendors_FormActivity.this, com.example.final_pro.Ven_det_RecActivity.class));
                        overridePendingTransition(0, 0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                    }
                });
    }
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
    public void onBackPressed () {
        Intent intent = new Intent(this, com.example.final_pro.Ven_det_RecActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}