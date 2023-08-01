package com.example.final_pro;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.final_pro.databinding.ActivityHomeBinding;
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

public class HomeActivity extends DrawerBaseActivity implements OnMapReadyCallback {

    ActivityHomeBinding activityHomeBinding;
    //Map
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    //image slider
    ImageSlider imageSlider;
  //  FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
        allocateActivityTitle("Home");

        Load_Settings();

FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        //Map
        Places.initialize(getApplicationContext(),getString(R.string.map_key));
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
                Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show());



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