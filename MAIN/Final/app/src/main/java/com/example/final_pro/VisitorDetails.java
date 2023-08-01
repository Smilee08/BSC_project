package com.example.final_pro;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VisitorDetails extends AppCompatActivity {

    EditText n, c, t, d, g, r;
    Button submit;
    DatePickerDialog.OnDateSetListener setListener;
    int hour, min;
    DatabaseReference databaseReference;
ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitordetails);

Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Visitors Details");
        }

        n = (EditText) findViewById(R.id.name);
        c = (EditText) findViewById(R.id.contact);
        d = (EditText) findViewById(R.id.date);
        t = (EditText) findViewById(R.id.time_in);
        g = (EditText) findViewById(R.id.guest_of);
        r = (EditText) findViewById(R.id.roomno);

        databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        GetDateTime getDateTime = new GetDateTime(this);
        getDateTime.datetime(new GetDateTime.VolleyCallBack() {
            @Override
            public void onGetDateTime(String date, String time) {
                d.setText(date);
                t.setText(time);
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        VisitorDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        d.setText(date+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        VisitorDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                                t.setText(dateFormat.format(date));

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

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = n.getText().toString();
                String contact = c.getText().toString();
                String date = d.getText().toString();
                String time = t.getText().toString();
                String guest_of = g.getText().toString();
                String roomno = r.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    n.setError("Enter Name of Visitor");
                    return;
                }
                if (TextUtils.isEmpty(contact)) {
                    c.setError("Enter Contact No. of Visitor");
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    d.setError("Enter Date");
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    t.setError("Enter Time");
                    return;
                }
                if (TextUtils.isEmpty(guest_of)) {
                    g.setError("Enter Name");
                    return;
                }
                if (TextUtils.isEmpty(roomno)) {
                    r.setError("Enter Room No.");
                    return;
                }
if(name.isEmpty() && contact.isEmpty() && date.isEmpty() && time.isEmpty() && guest_of.isEmpty() && roomno.isEmpty()){
    Toast.makeText(VisitorDetails.this, "Enter the data.", Toast.LENGTH_SHORT).show();

}else{
                VisitorString visitorString = new VisitorString(name, contact, date, time, guest_of, roomno);
                String visitorId = databaseReference.push().getKey();
                databaseReference.child(visitorId).setValue(visitorString).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(VisitorDetails.this, "Details added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), VisitorActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VisitorDetails.this, "Error!!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
        Intent intent= new Intent(this,VisitorActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}