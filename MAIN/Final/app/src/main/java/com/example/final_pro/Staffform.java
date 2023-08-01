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

public class Staffform extends AppCompatActivity {
    TextView name;
    TextView work;
    TextView department;
    Spinner availability;
    TextView contactno;
    Button submit,back;

ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffform);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Staff Details");
        }


        Spinner sp= findViewById(R.id.spinner43);
        List<String> avai= new ArrayList<>();
        avai.add(0,"Select Wing");
        avai.add("Available");
        avai.add("Not Available");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, avai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Availibility")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        name=(EditText)findViewById(R.id.name);
        work=(EditText) findViewById(R.id.work);
        department=(EditText)findViewById(R.id.department);
        contactno=(EditText)findViewById(R.id.contactno);
        availability=(Spinner) findViewById(R.id.spinner43);

        submit=(Button)findViewById(R.id.perSub);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }


    private void processinsert()
    {
        String n = name.getText().toString();
        String w = work.getText().toString();
        String d = department.getText().toString();
        String a = availability.getSelectedItem().toString();
        String c = contactno.getText().toString();
        if (TextUtils.isEmpty(n)) {
            name.setError("Please fill the name field");
        }
        if (TextUtils.isEmpty(w)) {
            work.setError("Please fill the work field");
        }
        if (TextUtils.isEmpty(d)) {
            department.setError("Please fill the department field");
        }

        if (TextUtils.isEmpty(c)) {
            contactno.setError("Please fill the contact No field");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", n);
        map.put("work", w);
        map.put("department", d);
        map.put("availability", a);
        map.put("contactno", c);


        FirebaseDatabase.getInstance().getReference().child("staffavail").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                        work.setText("");
                        department.setText("");


                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
               Intent intent= new Intent(Staffform.this,Staffavailibilty.class);
               startActivity(intent);
               overridePendingTransition(0,0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
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
    public void onBackPressed () {
        Intent intent = new Intent(this,Staffavailibilty .class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}