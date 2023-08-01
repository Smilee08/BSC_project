package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_pro.databinding.ActivityDeliveryBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeliveryActivity extends DrawerBaseActivity {

    ActivityDeliveryBinding activityDeliveryBinding;
    RecyclerView recview;
    Delivery_adapter adapter;
    FloatingActionButton fb;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDeliveryBinding= ActivityDeliveryBinding.inflate(getLayoutInflater());
        setContentView(activityDeliveryBinding.getRoot());
        allocateActivityTitle("Delivery");
        setTitle("Search here..");

        Load_Settings();

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Delivery_users> options =
                new FirebaseRecyclerOptions.Builder<Delivery_users>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Delivery"), Delivery_users.class)
                        .build();

        adapter=new Delivery_adapter(options);
        recview.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fb=(FloatingActionButton)findViewById(R.id.fab_del);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("isAdmin").equals("2")) {
                            startActivity(new Intent(getApplicationContext(),Delivery_FormActivity.class));
                            overridePendingTransition(0,0);
                        }
                        else {
                            fb.hide();
                        }
                    }
                });
            }
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
        FirebaseRecyclerOptions<Delivery_users> options =
                new FirebaseRecyclerOptions.Builder<Delivery_users>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Delivery").orderByChild("date").startAt(s).endAt(s+"\uf8ff"), Delivery_users.class)
                        .build();

        adapter=new Delivery_adapter(options);
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