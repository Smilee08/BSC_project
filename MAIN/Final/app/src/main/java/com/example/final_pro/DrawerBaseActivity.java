package com.example.final_pro;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        // super.setContentView(view);
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activitContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, com.example.final_pro.HomeActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_delivery:
             String uid = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df = firebaseFirestore.collection("Society_Members").document(uid);
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        if ((documentSnapshot.getString("isAdmin").equals("1"))|| (documentSnapshot.getString("isAdmin").equals("2"))) {
                            startActivity(new Intent(DrawerBaseActivity.this, DeliveryActivity.class));
                            finish();
                            overridePendingTransition(0, 0);
                       }
                        else {
                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.nav_parking:
                String uid1 = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference df1 = firebaseFirestore.collection("Society_Members").document(uid1);
                df1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        if ((documentSnapshot.getString("isAdmin").equals("1")) || (documentSnapshot.getString("isAdmin").equals("2"))) {
                            startActivity(new Intent(DrawerBaseActivity.this, ParkingActivity.class));
                            finish();
                            overridePendingTransition(0, 0);
                       }
                        else {
                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.nav_visitor:
                String uidv = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference v = firebaseFirestore.collection("Society_Members").document(uidv);
                v.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        if ((documentSnapshot.getString("isAdmin").equals("1")) || (documentSnapshot.getString("isAdmin").equals("2"))) {
                            startActivity(new Intent(DrawerBaseActivity.this, VisitorActivity.class));
                            finish();
                            overridePendingTransition(0, 0);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.nav_vendor:
                startActivity(new Intent(this, VendorsActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_circular:
                startActivity(new Intent(this, CircularActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_bill_payment:
                String uidb = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference b = firebaseFirestore.collection("Society_Members").document(uidb);
              //  b.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                //    @Override
                  //  public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        //if ((documentSnapshot.getString("isAdmin").equals("1")) || (documentSnapshot.getString("isAdmin").equals("0"))) {
                            startActivity(new Intent(DrawerBaseActivity.this, com.example.final_pro.Bill_PaymentActivity.class));
                          //  finish();
                            overridePendingTransition(0, 0);
                        //}
                      //  else {
                    //        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                  //      }
                //    }
              //  });

                break;
            case R.id.nav_complain:
                String uidc = firebaseAuth.getCurrentUser().getEmail();
                DocumentReference c = firebaseFirestore.collection("Society_Members").document(uidc);
                c.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG","onSuccess : "+ documentSnapshot.getData());
                        if ((documentSnapshot.getString("isAdmin").equals("1")) || (documentSnapshot.getString("isAdmin").equals("0"))) {
                            startActivity(new Intent(DrawerBaseActivity.this, com.example.final_pro.ComplainActivity.class));
                            overridePendingTransition(0, 0);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.nav_staff:
                startActivity(new Intent(this, StaffActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_emergency:
                startActivity(new Intent(this, EmergencyActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_intercom:
                startActivity(new Intent(DrawerBaseActivity.this,Society_ContactActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_society_members:
                startActivity(new Intent(this, Society_MembersActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.logout:
                //signout
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Home_LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting_id:
                startActivity(new Intent(this,SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //title of layout
    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}