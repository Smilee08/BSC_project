package com.example.final_pro;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Password_Creation extends AppCompatActivity {

    EditText e1, e2;
    Button button1;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_creation);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Registration");

        }


        e1 = (EditText) findViewById(R.id.ep1);
        e2 = (EditText) findViewById(R.id.ep2);
        button1 = (Button) findViewById(R.id.b5);

        fAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Society User");


     //   if (fAuth.getCurrentUser() != null) {
        //    startActivity(new Intent(getApplicationContext(), MainActivity.class));
          //  overridePendingTransition(0, 0);
           // finish();
        //}

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e1.getText().toString().trim();
                String pass = e2.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    e1.setError("Email is Required!!!!");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    e2.setError("Enter Password!!!!");
                    return;
                }
                if (pass.length() < 6) {
                    e2.setError("Password must be >= 6 Characters");
                }
                databaseReference.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Password_Creation.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            overridePendingTransition(0, 0);
                                        } else {
                                            Toast.makeText(Password_Creation.this, "Error!!!!" + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Password_Creation.this, "You are not a society member", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
               /* fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Password_Creation.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            overridePendingTransition(0, 0);
                        }
                        else {
                            Toast.makeText(Password_Creation.this, "Error!!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}