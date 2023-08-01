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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Vendors_AttendanceActivity extends AppCompatActivity {
    TextView name, type,timein,timeout;
    Button submit,back;
    ActionBar actionBar;
    int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_attendance);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Venders Logs");
        }

        name = findViewById(R.id.name_del);
        type = findViewById(R.id.type_Del);
        timein = findViewById(R.id.time_in);
        timeout = findViewById(R.id.time_out);

        timein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Vendors_AttendanceActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                        Vendors_AttendanceActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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

        submit = findViewById(R.id.submit_delivery);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { processinsert();
            }
        });
    }

    private void processinsert()
    {
        String n = name.getText().toString();
        String ti = timein.getText().toString();
        String to = timeout.getText().toString();
        String ty = type.getText().toString();

        if (TextUtils.isEmpty(n)) {
            name.setError("Please fill the name field");
        }
        if (TextUtils.isEmpty(ti)) {
            timein.setError("Please fill the timein field");
        }
        if (TextUtils.isEmpty(to)) {
            timeout.setError("Please fill the timeout field");
        }
        if (TextUtils.isEmpty(ty)) {
            type.setError("Please fill the type field");
        }

        if(n.isEmpty() && ti.isEmpty() && ty.isEmpty()){
            Toast.makeText(Vendors_AttendanceActivity.this,"Enter the data",Toast.LENGTH_SHORT).show();
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("name", n);
            map.put("timein", ti);
            map.put("timeout", to);
            map.put("type", ty);


            FirebaseDatabase.getInstance().getReference().child("VenderAT").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            name.setText("");
                            timein.setText("");
                            timeout.setText("");
                            type.setText("");

                            Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Vendors_AttendanceActivity.this,Ven_Att_Rec_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
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
        Intent intent = new Intent(this, com.example.final_pro.Ven_Att_Rec_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}