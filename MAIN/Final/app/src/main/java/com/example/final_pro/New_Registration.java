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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class New_Registration extends AppCompatActivity {

    Button sotp;
    private EditText fullname, roomNo, mobilenumber;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Registration");
        }

        fullname = (EditText) findViewById(R.id.Fullname);
        roomNo = (EditText) findViewById(R.id.roomno);
        mobilenumber = (EditText) findViewById(R.id.et1);
        sotp = (Button) findViewById(R.id.bt1);

        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        sotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Fullname = fullname.getText().toString().trim();
                String Roomno = roomNo.getText().toString().trim();
                String MobileNumber = mobilenumber.getText().toString();

                if (TextUtils.isEmpty(Fullname)){
                    fullname.setError("Please enter your First name");
                    return;
                }
                if (TextUtils.isEmpty(Roomno)){
                    roomNo.setError("Please enter your Room no.");
                    return;
                }
                if (TextUtils.isEmpty(MobileNumber)) {
                    mobilenumber.setError("Please Fill your mobile number");
                    return;
                }

                if (!mobilenumber.getText().toString().trim().isEmpty()) {
                    if ((mobilenumber.getText().toString().trim()).length() == 10) {

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + mobilenumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                New_Registration.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(New_Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        Intent intent = new Intent(getApplicationContext(), Otp.class);
                                        intent.putExtra("mobile", mobilenumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                        overridePendingTransition(0,0);
                                    }
                                }
                        );
                    }
                    else {
                        Toast.makeText(New_Registration.this, "Please Enter 10 digit mobile number",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(New_Registration.this, "Enter 10 digit mobile number",Toast.LENGTH_SHORT).show();
                }

                Register addUser = new Register(Fullname,Roomno,MobileNumber);
                databaseReference.child(Fullname).setValue(addUser).addOnSuccessListener(unused -> Toast.makeText(New_Registration.this, "Saved Successfully", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(New_Registration.this, "Error!!"+e.getMessage(), Toast.LENGTH_SHORT).show());
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
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}