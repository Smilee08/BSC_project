package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SMCont_FormActivity extends AppCompatActivity {

    ActionBar actionBar;
    EditText nametxt,photxt;
    Button Sbutton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smcont_form);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New Contact");
        }

        nametxt=findViewById(R.id.smc_name);
        photxt=findViewById(R.id.smc_number);

        firebaseDatabase=FirebaseDatabase.getInstance();

        databaseReference=firebaseDatabase.getReference("Society_contact");

        Emergency_ContactModel emergencyContactModel=new Emergency_ContactModel();

        Sbutton=findViewById(R.id.sm_save);

        Sbutton.setOnClickListener(v -> {
            String namet=nametxt.getText().toString();
            String contt=photxt.getText().toString();

            String id=databaseReference.push().getKey();
            if (TextUtils.isEmpty(namet)){
                nametxt.setError("enter name");
                return;
            }
            if(TextUtils.isEmpty(contt)){
                photxt.setError("enter contact number");
                return;
            }

            Emergency_ContactModel model=new Emergency_ContactModel(namet,contt);
          //  emergencyContactModel.setName(namet);
            //emergencyContactModel.setContact(contt);
            if (namet.isEmpty() && contt.isEmpty()){
                Toast.makeText(SMCont_FormActivity.this,"Enter the data",Toast.LENGTH_SHORT).show();

            }else {

                databaseReference.child(namet).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SMCont_FormActivity.this,"Contact added",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SMCont_FormActivity.this,"Failed to add contact. ",Toast.LENGTH_SHORT).show();
                    }
                });
                        /*addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(emergencyContactModel);

                        Toast.makeText(SMCont_FormActivity.this, "Contact Added. ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(SMCont_FormActivity.this, "Fail to add contact. " + error, Toast.LENGTH_SHORT).show();

                    }
                });*/

                startActivity(new Intent(SMCont_FormActivity.this, com.example.final_pro.Society_ContactActivity.class));
                overridePendingTransition(0, 0);
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
        Intent intent= new Intent(this,Society_ContactActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}