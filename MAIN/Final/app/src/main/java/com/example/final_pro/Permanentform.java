package com.example.final_pro;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permanentform extends AppCompatActivity
{
    TextView name, vehicalno,  timein, timeout;
    Button submit,back;
    Spinner Wing,VehicalType;
    ActionBar actionBar;
    int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permanentform);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Permanent Vehicle Details");
        }

        Spinner spinner= findViewById(R.id.spinner3);
        List<String> WING = new ArrayList<>();
        WING.add(0,"Select Wing");
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
                if (parent.getItemAtPosition(position).equals("Choose Wing")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner s= findViewById(R.id.spinner4);
        List<String>Vehical = new ArrayList<>();
        Vehical.add(0,"Select Type");
        Vehical.add("Two Wheeler");
        Vehical.add("Four Wheeler");

        ArrayAdapter<String> arraAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Vehical);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(arraAdapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Type")){
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
        Wing=(Spinner) findViewById(R.id.spinner3);
        vehicalno=(EditText)findViewById(R.id.vehicalno);
        timein=(EditText)findViewById(R.id.timein);
        timeout=(EditText)findViewById(R.id.timeout);
        VehicalType=(Spinner) findViewById(R.id.spinner4);

        timein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Permanentform.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (timePicker, hourOfDay, minute) -> {
                            hour = hourOfDay;
                            min = minute;
                            String time = hour + ":" + min;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                                    "HH:mm"
                            );
                            try {
                                Date date = simpleDateFormat.parse(time);
                                SimpleDateFormat dateFormat = new SimpleDateFormat(
                                        "hh:mm aa"
                                );
                                timein.setText(dateFormat.format(date));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });

        timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Permanentform.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (timePicker, hourOfDay, minute) -> {
                            hour = hourOfDay;
                            min = minute;
                            String time = hour + ":" + min;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                                    "HH:mm"
                            );
                            try {
                                Date date = simpleDateFormat.parse(time);
                                SimpleDateFormat dateFormat = new SimpleDateFormat(
                                        "hh:mm aa"
                                );
                                timeout.setText(dateFormat.format(date));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });


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
        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("vehicalno",vehicalno.getText().toString());
        map.put("timein",timein.getText().toString());
        map.put("wing",Wing.getSelectedItem().toString().trim());
        map.put("vehicaltype",VehicalType.getSelectedItem().toString().trim());
        map.put("timeout",timeout.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Permanent").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                        vehicalno.setText("");
                        timein.setText("");
                        timeout.setText("");
                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(Permanentform.this,Permanent_activity.class);
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
        Intent intent = new Intent(this, com.example.final_pro.ParkingActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}