package com.example.final_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button bt2;
    TextView forgot_password;
    private EditText e1, p1;
    Button bt1;
    FirebaseAuth fAuth;
    ActionBar actionBar;
    private AlertDialog.Builder passwordResetDialog;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Login");
        }

        bt1 = (Button) findViewById(R.id.b1);
        e1 = (EditText) findViewById(R.id.email);
        p1 = (EditText) findViewById(R.id.pass);
        bt2 = (Button) findViewById(R.id.b2);
        forgot_password = (TextView) findViewById(R.id.t1);

        fAuth = FirebaseAuth.getInstance();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e1.getText().toString().trim();
                String pass = p1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    e1.setError("Please enter your Email ID");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    p1.setError("Please enter your Password");
                    return;
                }
                if (pass.length() < 6) {
                    p1.setError("Password must be >= 6 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            overridePendingTransition(0, 0);
                        } else {
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, New_Registration.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

    }

    public void createDialog() {
        passwordResetDialog = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.forgotpassword, null);

        EditText resetmail = (EditText) popupView.findViewById(R.id.edit1);
        Button reset = (Button) popupView.findViewById(R.id.rm);
        Button can = (Button) popupView.findViewById(R.id.cl);

        passwordResetDialog.setView(popupView);
        dialog = passwordResetDialog.create();
        dialog.show();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = resetmail.getText().toString();
                if (TextUtils.isEmpty(mail)) {
                    resetmail.setError("Enter your registered mail");
                    return;
                }

                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Password Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error!! Reset Link is not Sent!!" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Home_LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}

