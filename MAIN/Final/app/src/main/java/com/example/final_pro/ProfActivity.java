package com.example.final_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_pro.databinding.ActivityParkingBinding;
import com.example.final_pro.databinding.ActivityProfBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfActivity extends DrawerBaseActivity{

    CardView Name,About,Email,Phone;
    TextView n,a,p,e;
    EditText About_et,Username,mobile,mail;
    ActivityProfBinding activityProBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProBinding= ActivityProfBinding.inflate(getLayoutInflater());
        setContentView(activityProBinding.getRoot());
        allocateActivityTitle("Profile");
        //setContentView(R.layout.activity_prof);
        Load_Settings();
        n=findViewById(R.id.firstname);
        a=findViewById(R.id.et_about);
        e=findViewById(R.id.ed_email);
        p=findViewById(R.id.ed_phone);

        String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile").child(userID);

        Profile_data prof=new Profile_data();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String em=snapshot.child("email").getValue().toString();
                e.setText(em);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfActivity.this,"can not retrive email",Toast.LENGTH_SHORT).show();

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               // if (snapshot.exists()) {
                    n.setText(snapshot.child("name").getValue().toString());
                    // name.setText(snapshot.child("name").getValue().toString());
                    a.setText(snapshot.child("about").getValue().toString());
                    p.setText(snapshot.child("phone").getValue().toString());
                   // e.setText(snapshot.child("email").getValue().toString());
                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfActivity.this, "failed to upload the profile details", Toast.LENGTH_SHORT).show();

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
    }
}