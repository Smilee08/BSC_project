package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form extends AppCompatActivity {
    TextView name,flatno,maintenance,amount;
    Button submit, back;
    Spinner Wing;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Load_Settings();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Payment");
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> WING = new ArrayList<>();
        WING.add(0, "Select Wing");
        WING.add("A");
        WING.add("B");
        WING.add("C");
        WING.add("D");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, WING);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Wing")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        name = (EditText) findViewById(R.id.name1);
        Wing = (Spinner) findViewById(R.id.spinner);
        flatno = (EditText) findViewById(R.id.flatno);


        submit = (Button) findViewById(R.id.perSub1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }

    private void processinsert() {
        String n = name.getText().toString();
        String fn = flatno.getText().toString();
        String w = Wing.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(n)) {
            name.setError("Please fill the name field");
        }
        if (TextUtils.isEmpty(fn)) {
            flatno.setError("Please fill the name field");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", n);
        map.put("flatno", fn);
        map.put("wing", w);



        FirebaseDatabase.getInstance().getReference().child("Pay").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                       flatno.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
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
        Intent intent= new Intent(this,View_activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        //finish();
        super.onBackPressed();
    }
}