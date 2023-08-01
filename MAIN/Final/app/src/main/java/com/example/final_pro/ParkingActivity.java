package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import com.example.final_pro.databinding.ActivityParkingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ParkingActivity extends DrawerBaseActivity {
ActivityParkingBinding activityParkingBinding;
CardView perm,gus;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityParkingBinding= ActivityParkingBinding.inflate(getLayoutInflater());
        setContentView(activityParkingBinding.getRoot());
        allocateActivityTitle("Parking");

        Load_Settings();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        perm=(CardView) findViewById(R.id.perm);
        perm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ParkingActivity.this, Permanent_activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        gus=(CardView) findViewById(R.id.gues);
        gus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        if ((documentSnapshot.getString("isAdmin").equals("1"))|| (documentSnapshot.getString("isAdmin").equals("2"))) {
                            Intent intent=new Intent(ParkingActivity.this,Guest_activity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
        Intent intent= new Intent(ParkingActivity.this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }

}