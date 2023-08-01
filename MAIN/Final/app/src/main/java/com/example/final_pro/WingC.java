package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WingC extends AppCompatActivity {

    RecyclerView r3;
    LinearLayoutManager layoutManager2;
    ArrayList<List_ClassA> userList;
    Adapter_A adapter2;
    ActionBar actionBar;
    DatabaseReference database;
    FloatingActionButton fbtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wingc);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Wing C");
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fbtn = (FloatingActionButton) findViewById(R.id.addbtn);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("isAdmin").equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), AddMemberC.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        }
                        else {
                            fbtn.hide();
                        }
                    }
                });
            }
        });

        r3 = findViewById(R.id.view3);
        database = FirebaseDatabase.getInstance().getReference("Wing C");
        layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        r3.setLayoutManager(layoutManager2);

        userList = new ArrayList<>();
        adapter2 = new Adapter_A(WingC.this, userList);
        r3.setAdapter(adapter2);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    List_ClassA user1;
                    user1 = dataSnapshot.getValue(List_ClassA.class);
                    userList.add(user1);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        Intent intent = new Intent(this, com.example.final_pro.Society_MembersActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}