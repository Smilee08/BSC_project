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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Complaint_FormActivity extends AppCompatActivity {

    EditText category, description;
    TextView email;
    Spinner complaint_type;
    Button save;
    ActionBar actionBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_form);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Complains");
        }

        firebaseAuth = FirebaseAuth.getInstance();

        complaint_type = findViewById(R.id.spinner);
        category = findViewById(R.id.e1);
        description = findViewById(R.id.e2);
        email = findViewById(R.id.email);

        save = findViewById(R.id.save);
        save.setOnClickListener(view -> processinsert());



        Spinner spinner = findViewById(R.id.spinner);
        List<String> Complaint_Type = new ArrayList<>();
        Complaint_Type.add(0, "Select complaint type");
        Complaint_Type.add("Personal");
        Complaint_Type.add("Electricity");
        Complaint_Type.add("Water Supply");
        Complaint_Type.add("Hygiene");
        Complaint_Type.add("Others");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Complaint_Type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose A Complaint Type")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void processinsert() {

        String ct = complaint_type.getSelectedItem().toString();
        String c = category.getText().toString();
        String d = description.getText().toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Complaint");
        String em= firebaseAuth.getCurrentUser().getEmail();
        //dataholder dataholder = new dataholder();
        //dataholder.setEmail(em);
        //databaseReference.setValue(dataholder);

        if (TextUtils.isEmpty(c)) {
            category.setError("Please fill the time field");
        }
        if (TextUtils.isEmpty(d)) {
            description.setError("Please fill the date field");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("category", c);
        map.put("complaint_type", ct);
        map.put("description", d);
        map.put("email", em);

        FirebaseDatabase.getInstance().getReference().child("complaint").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        category.setText("");
                        description.setText("");
                        email.setText("");

                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Could not insert", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, com.example.final_pro.ComplainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}