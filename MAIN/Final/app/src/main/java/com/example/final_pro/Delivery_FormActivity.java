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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Delivery_FormActivity extends AppCompatActivity {
    TextView name, time, date,typeofdelivery,recievername,flatno,contactno;
    Button submit,back;
ActionBar actionBar;
int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_form);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Delivery Details");
        }

        name = findViewById(R.id.name_del);
        time = findViewById(R.id.time_del);
        date = findViewById(R.id.date_del);
        contactno = findViewById(R.id.contact_del);
        typeofdelivery= findViewById(R.id.type_Del);
        flatno= findViewById(R.id.flat_del);
        recievername= findViewById(R.id.rec_del);

     /*   GetDateTime getDateTime = new GetDateTime(this);
        getDateTime.datetime(new GetDateTime.VolleyCallBack() {
            @Override
            public void onGetDateTime(String date, String time) {
                date.setText(date);
                time.setText(time);
            }
        }); */

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

      /*  date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Delivery_FormActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        date.setText(date+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Delivery_FormActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                                time.setText(dateFormat.format(date));

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
*/

        submit = findViewById(R.id.submit_delivery);
        submit.setOnClickListener(view -> processinsert());
    }

    private void processinsert()
    {
        String n = name.getText().toString();
        String t = time.getText().toString();
        String d = date.getText().toString();
        String cn = contactno.getText().toString();
        String ty = typeofdelivery.getText().toString();
        String rn = recievername.getText().toString();
        String fn = flatno.getText().toString();
        if (TextUtils.isEmpty(n)) {
            name.setError("Please fill the name field");
        }
        if (TextUtils.isEmpty(t)) {
            time.setError("Please fill the time field");
        }
        if (TextUtils.isEmpty(d)) {
            date.setError("Please fill the date field");
        }
        if (TextUtils.isEmpty(cn)) {
            contactno.setError("Please fill the contact no field");
        }
        if (TextUtils.isEmpty(ty)) {
            typeofdelivery.setError("Please fill the type of delivery field");
        }
        if (TextUtils.isEmpty(rn)) {
            recievername.setError("Please fill the type of reciever name field");
        }
        if (TextUtils.isEmpty(fn)) {
            flatno.setError("Please fill the type of flatno field");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", n);
        map.put("date", d);
        map.put("contactno", cn);
        map.put("typeofdelivery", ty);
        map.put("recievername", rn);
        map.put("flatno", fn);
        map.put("time", t);


        FirebaseDatabase.getInstance().getReference().child("Delivery").push()
                .setValue(map)
                .addOnSuccessListener(aVoid -> {
                    name.setText("");
                     time.setText("");
                   date.setText("");
                    contactno.setText("");
                    typeofdelivery.setText("");
                     recievername.setText("");
                     flatno.setText("");


                    Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, com.example.final_pro.DeliveryActivity.class));
                    overridePendingTransition(0,0);
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show());

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
        Intent intent = new Intent(this, com.example.final_pro.DeliveryActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}