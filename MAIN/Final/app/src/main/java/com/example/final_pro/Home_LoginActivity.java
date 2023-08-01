package com.example.final_pro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.final_pro.databinding.ActivityHomeLoginBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home_LoginActivity extends AppCompatActivity implements OnMapReadyCallback {

   // ActivityHomeLoginBinding activityHomeLoginBinding;
    //Map
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    //image slider
    ImageSlider imageSlider;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // activityHomeLoginBinding = ActivityHomeLoginBinding.inflate(getLayoutInflater());
      //  setContentView(activityHomeLoginBinding.getRoot());
       setContentView(R.layout.activity_home_login);

        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        Load_Settings();
        checkConnection();
        FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();

        //Map
        Places.initialize(getApplicationContext(), getString(R.string.map_key));
        PlacesClient placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //ImageSlider
        imageSlider = findViewById(R.id.imageSlider);
        final List<SlideModel> image = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Images").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                    image.add(new SlideModel(queryDocumentSnapshot.getString("url"), ScaleTypes.FIT));
                    imageSlider.setImageList(image, ScaleTypes.FIT);
                }
            } else {
                Toast.makeText(Home_LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e -> Toast.makeText(Home_LoginActivity.this, "error", Toast.LENGTH_SHORT).show());


        //Button
        Button but = findViewById(R.id.login_id);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }



     public void checkConnection() {
    //Internet
      ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

      if (networkInfo != null) {

         if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
           //Toast.makeText(getApplicationContext(), "wifi network connected", Toast.LENGTH_SHORT).show();
      }
     if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
    //  Toast.makeText(getApplicationContext(), "mobile data network connected", Toast.LENGTH_SHORT).show();
      }

     } else {
      Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
    }


//    }
    //toast for internet
    // public void showSingleToast(){}
    //    try{if(toast.getView().isShown()){
    //  toast.show();
    // }catch(Exception exception){
    //   exception.printStackTrace();
    //Log.d(TAG,"Toast Exception is"+exception.getLocalizedMessage());
    //toast=toast.makeText(this.getActivity(),getContext().getString());
    //}
    //}
    //    toast.setText(st);}catch (Exception ignored){
    //    toast=Toast.makeText(theContext,st,toastDuration);
    // }
    //toast.show();

    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng location = new LatLng(15.521689, 73.957889);
        map.addMarker(new MarkerOptions().position(location).title("Govt College,Khandola "));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
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

    //exit from app popup toast
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", (dialogInterface, i) -> {
            //HomeActivity.super.onBackPressed();
            finish();
            finishAffinity();
            System.exit(0);
        }).setNegativeButton("No", null).show();
    }
}