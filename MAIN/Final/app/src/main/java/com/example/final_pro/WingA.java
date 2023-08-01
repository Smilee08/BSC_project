package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WingA extends AppCompatActivity {

    RecyclerView r1;
    LinearLayoutManager layoutManager;
    ArrayList<List_ClassA> userList;
    Adapter_A adapter;
    DatabaseReference database;
    ActionBar actionBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FloatingActionButton fbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winga);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Wing A");
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
                            Intent intent = new Intent(getApplicationContext(), AddMember.class);
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

   /*     holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.bottomsheet))
                        .setExpanded(true, 400)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText roomno1 = myview.findViewById(R.id.roomno);
                EditText name1 = myview.findViewById(R.id.name);
                Button update = myview.findViewById(R.id.update);

                roomno1.setText(user.getRoom());
                name1.setText(user.getName());

                dialogPlus.show();


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("room", roomno1.getText().toString());
                        map.put("name", name1.getText().toString());


                        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getName()).getRef();
                        df.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialogPlus.dismiss();
                                userList.remove(position);
                                Toast.makeText(context.getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
*/
        r1 = findViewById(R.id.view1);
        database = FirebaseDatabase.getInstance().getReference("WingA");
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        r1.setLayoutManager(layoutManager);

        userList = new ArrayList<>();
        adapter = new Adapter_A(WingA.this, userList);
        r1.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    List_ClassA user1;
                    user1 = dataSnapshot.getValue(List_ClassA.class);
                    userList.add(user1);
                }
                adapter.notifyDataSetChanged();
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