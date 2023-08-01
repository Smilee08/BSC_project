package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import com.example.final_pro.databinding.ActivityBillPaymentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Bill_PaymentActivity extends DrawerBaseActivity {

    ActivityBillPaymentBinding activityBillPaymentBinding;
    CardView cardView,cardView2;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBillPaymentBinding=ActivityBillPaymentBinding.inflate(getLayoutInflater());
        setContentView(activityBillPaymentBinding.getRoot());
        allocateActivityTitle("Bill Payment");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        cardView=findViewById(R.id.b);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("isAdmin").equals("1")) {
                            Intent intent = new Intent(Bill_PaymentActivity.this, View_activity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        }
                        else {
                            Toast.makeText(Bill_PaymentActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        cardView2=findViewById(R.id.b2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("isAdmin").equals("1")) {
                            Intent intent = new Intent(Bill_PaymentActivity.this, View_activity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        }
                        else {
                            Toast.makeText(Bill_PaymentActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        Load_Settings();
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