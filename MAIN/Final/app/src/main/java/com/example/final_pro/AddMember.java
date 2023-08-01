package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMember extends AppCompatActivity {

    private EditText fname, member1, room1, emailaddress1, contact1;
    Button add, cancel;
    DatabaseReference database;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Add Member");
        }

        fname = (EditText) findViewById(R.id.name);
        member1 = (EditText) findViewById(R.id.member);
        room1 = (EditText) findViewById(R.id.room);
        emailaddress1 = (EditText) findViewById(R.id.emailid);
        contact1 = (EditText) findViewById(R.id.contact);

        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);

        database = FirebaseDatabase.getInstance().getReference("WingA");


        add.setOnClickListener(view -> {

            String name = fname.getText().toString().trim();
            String member = member1.getText().toString().trim();
            String room = room1.getText().toString().trim();
            String emailaddress = emailaddress1.getText().toString().trim();
            String contact = contact1.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                fname.setError("Enter First Name");
                return;
            }
            if (TextUtils.isEmpty(member)) {
                member1.setError("Enter No. of Members");
                return;
            }
            if (TextUtils.isEmpty(room)) {
                room1.setError("Enter Room No.");
                return;
            }
            if (TextUtils.isEmpty(emailaddress)) {
                emailaddress1.setError("Enter Email ID");
                return;
            }
            if (TextUtils.isEmpty(contact)) {
                contact1.setError("Enter Mobile No.");
                return;
            }



            MemberA new_member1 = new MemberA(name, member, room, emailaddress, contact);
            database.child(name).setValue(new_member1).addOnSuccessListener(unused -> {
                Toast.makeText(AddMember.this, "Details added Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WingA.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddMember.this, "Error!!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WingA.class);
                startActivity(intent);
                overridePendingTransition(0,0);
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
   //dont put back press
   @Override
   public void onBackPressed () {
       Intent intent = new Intent(this, WingA.class);
       startActivity(intent);
       overridePendingTransition(0, 0);
       finish();
       //super.onBackPressed();
   }
}