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

import com.example.final_pro.databinding.ActivityStaffBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StaffActivity extends DrawerBaseActivity {
ActivityStaffBinding activityStaffBinding;
CardView staff,attendence;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStaffBinding= ActivityStaffBinding.inflate(getLayoutInflater());;
        setContentView(activityStaffBinding.getRoot());
        allocateActivityTitle("Staff");

        Load_Settings();

        staff=(CardView) findViewById(R.id.staf);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        staff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StaffActivity.this,Staffavailibilty.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        attendence=(CardView) findViewById(R.id.atta);
        attendence.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        if ((documentSnapshot.getString("isAdmin").equals("1"))|| (documentSnapshot.getString("isAdmin").equals("2"))) {
                            Intent intent = new Intent(StaffActivity.this, Attendence.class);
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
        Intent intent= new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        //super.onBackPressed();
    }
}