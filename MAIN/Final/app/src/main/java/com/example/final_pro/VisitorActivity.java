package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_pro.databinding.ActivityVisitorBinding;
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

public class VisitorActivity extends DrawerBaseActivity {
ActivityVisitorBinding activityVisitorBinding;
    FloatingActionButton add;
    RecyclerView recyclerView1;
    LinearLayoutManager layoutManager;
    ArrayList<VisitorString> VisitorList;
    com.example.final_pro.VisitorAdapter visitorAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVisitorBinding= ActivityVisitorBinding.inflate(getLayoutInflater());;
        setContentView(activityVisitorBinding.getRoot());
        allocateActivityTitle("Visitors");

        Load_Settings();

        recyclerView1 = (RecyclerView) findViewById(R.id.view1);
        add = (FloatingActionButton) findViewById(R.id.fb1);

        databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        add.setOnClickListener(view -> {
            String uid = firebaseAuth.getCurrentUser().getEmail();
            DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isAdmin").equals("2")) {
                        Intent intent = new Intent(VisitorActivity.this, com.example.final_pro.VisitorDetails.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                    else {
                        add.hide();
                    }
                }
            });
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);

        VisitorList = new ArrayList<>();
        visitorAdapter = new com.example.final_pro.VisitorAdapter(VisitorActivity.this, VisitorList);
        recyclerView1.setAdapter(visitorAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VisitorString visitor1;
                    visitor1 = dataSnapshot.getValue(VisitorString.class);
                    VisitorList.add(visitor1);
                }
                visitorAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VisitorActivity.this, "Error in Retrieving Data", Toast.LENGTH_SHORT).show();
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
        Intent intent= new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}