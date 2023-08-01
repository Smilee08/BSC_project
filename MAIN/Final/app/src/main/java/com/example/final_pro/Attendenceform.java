package com.example.final_pro;



import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendenceform extends AppCompatActivity {
    TextView name, timein,timeout,date;
    Button submit;
ActionBar actionBar;
int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendenceform);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Staff Attendance");
        }


        name=(EditText)findViewById(R.id.name);
        timein=(EditText) findViewById(R.id.timein);
       timeout=(EditText)findViewById(R.id.timeout);
        date=(EditText)findViewById(R.id.date1);

    /*    GetDateTime getDateTime = new GetDateTime(this);
        getDateTime.datetime(new GetDateTime.VolleyCallBack() {
            @Override
            public void onGetDateTime(String date, String time) {
                date.setText(date);
            }
        });*/

        timein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Attendenceform.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                        Attendenceform.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
        String n = name.getText().toString();
        String ti = timein.getText().toString();
        String to = timeout.getText().toString();
        String d = date.getText().toString();

        if (TextUtils.isEmpty(n)) {
            name.setError("Please fill the name field");
        }
        if (TextUtils.isEmpty(ti)) {
           timein.setError("Please fill the work field");
        }
        if (TextUtils.isEmpty(to)) {
            timeout.setError("Please fill the department field");
        }
        if (TextUtils.isEmpty(to)) {
            date.setError("Please fill the department field");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", n);
        map.put("timein", ti);
        map.put("timeout", to);
        map.put("date", d);



        FirebaseDatabase.getInstance().getReference().child("Staff").push()
                .setValue(map)
                .addOnSuccessListener(aVoid -> {
                    name.setText("");
                    timein.setText("");
                    timeout.setText("");
date.setText("");

                    Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), com.example.final_pro.Attendence.class));
                    overridePendingTransition(0,0);
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
        Intent intent = new Intent(this, com.example.final_pro.Attendence.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}