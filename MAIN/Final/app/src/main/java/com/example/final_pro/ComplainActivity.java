package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.final_pro.databinding.ActivityComplainBinding;
import com.example.final_pro.main.SectionsPagerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ComplainActivity extends DrawerBaseActivity {
ActivityComplainBinding activityComplainBinding;
    RecyclerView recview;
    complaintadapter adapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComplainBinding= ActivityComplainBinding.inflate(getLayoutInflater());
        setContentView(activityComplainBinding.getRoot());
        allocateActivityTitle("Complain");

        Load_Settings();
        activityComplainBinding = ActivityComplainBinding.inflate(getLayoutInflater());
        setContentView(activityComplainBinding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = activityComplainBinding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = activityComplainBinding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = activityComplainBinding.fab;

        setTitle("Search here..");

        recview=(RecyclerView)findViewById(R.id.complaintList);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<dataholder> options =
                new FirebaseRecyclerOptions.Builder<dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("complaint"), dataholder.class)
                        .build();

        adapter= new complaintadapter(options);
        recview.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            String uid = firebaseAuth.getCurrentUser().getEmail();
            DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isAdmin").equals("0")) {
                        Intent intent = new Intent(ComplainActivity.this,Complaint_FormActivity.class);
                        startActivity(intent);
                    }
                    else {
                        fab.hide();
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
    {   //String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
       // DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference(); //.child("Complaint");
        dataholder datahold= new dataholder();

       // FirebaseAuth auth= FirebaseAuth.getInstance();
       // String em=auth.getCurrentUser().getEmail();

       //datahold.setEmail(em);
       // databaseReference.setValue(datahold);


        TextView email=findViewById(R.id.email);
      /*databaseReference.child("complaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String em=snapshot.child("email").getValue().toString();
                email.setText(em);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ComplainActivity.this,"cannot retrive email",Toast.LENGTH_SHORT).show();
            }
        });*/
        FirebaseRecyclerOptions<dataholder> options =
                new FirebaseRecyclerOptions.Builder<dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("complaint").orderByChild("complaint_type").startAt(s).endAt(s+"\uf8ff"), dataholder.class)
                        .build();

        adapter=new complaintadapter(options);
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
    public void onBackPressed() {
        Intent intent= new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }


}