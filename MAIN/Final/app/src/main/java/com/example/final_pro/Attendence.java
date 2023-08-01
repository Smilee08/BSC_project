package com.example.final_pro;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Attendence extends AppCompatActivity {
    RecyclerView recview;
    Attendenceadapter adapter;
    FloatingActionButton fb;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Staff Attendance");
        }

        setTitle("Search here..");

        recview=(RecyclerView)findViewById(R.id.recview1);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<com.example.final_pro.Attendenceusers> options =
                new FirebaseRecyclerOptions.Builder<com.example.final_pro.Attendenceusers>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Staff"), com.example.final_pro.Attendenceusers.class)
                        .build();

        adapter= new Attendenceadapter(options);
        recview.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fb=(FloatingActionButton)findViewById(R.id.fadd1);
        fb.setOnClickListener(view -> {
            String uid = firebaseAuth.getCurrentUser().getEmail();
            DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isAdmin").equals("2")) {
                        startActivity(new Intent(getApplicationContext(),Attendenceform.class));
                        overridePendingTransition(0,0);
                    }
                    else {
                        fb.hide();
                    }
                }
            });
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<com.example.final_pro.Attendenceusers> options =
                new FirebaseRecyclerOptions.Builder<com.example.final_pro.Attendenceusers>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Staff").orderByChild("timein").startAt(s).endAt(s+"\uf8ff"), com.example.final_pro.Attendenceusers.class)
                        .build();

        adapter=new Attendenceadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

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
        Intent intent = new Intent(this, StaffActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }


}
