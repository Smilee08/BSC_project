package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {

    private EditText in1, in2, in3, in4, in5, in6;
    Button votp;
    String getbackendotp;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Load_Settings();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Registration");

        }

        in1 = (EditText) findViewById(R.id.ip1);
        in2 = (EditText) findViewById(R.id.ip2);
        in3 = (EditText) findViewById(R.id.ip3);
        in4 = (EditText) findViewById(R.id.ip4);
        in5 = (EditText) findViewById(R.id.ip5);
        in6 = (EditText) findViewById(R.id.ip6);
        votp = (Button) findViewById(R.id.bt2);

        TextView t1 = (TextView) findViewById(R.id.tx3);
        t1.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        getbackendotp = getIntent().getStringExtra("backendotp");

        votp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!in1.getText().toString().trim().isEmpty() && !in2.getText().toString().trim().isEmpty() && !in3.getText().toString().trim().isEmpty() && !in4.getText().toString().trim().isEmpty() && !in5.getText().toString().trim().isEmpty() && !in6.getText().toString().trim().isEmpty()) {
                    String otpcode = in1.getText().toString() +
                            in2.getText().toString() +
                            in3.getText().toString() +
                            in4.getText().toString() +
                            in5.getText().toString() +
                            in6.getText().toString();
                    if (getbackendotp != null) {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getbackendotp, otpcode
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), com.example.final_pro.Password_Creation.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            }
                            else {
                                Toast.makeText(Otp.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
//                    Toast.makeText(Otp.this, "Verifying OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Otp.this, "Enter OTP Correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        movenumber();

        findViewById(R.id.tx4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        Otp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getbackendotp = newbackendotp;
                                Toast.makeText(Otp.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }

    private void movenumber() {
        in1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    in2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        in2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    in3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        in3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    in4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        in4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    in5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        in5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    in6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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