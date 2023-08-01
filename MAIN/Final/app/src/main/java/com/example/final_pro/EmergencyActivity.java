package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_pro.databinding.ActivityEmergencyBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmergencyActivity extends com.example.final_pro.DrawerBaseActivity implements com.example.final_pro.EmergencyAdapter.OnNoteListener {

ActivityEmergencyBinding activityEmergencyBinding;
FloatingActionButton cont_fab;
RecyclerView recyclerViewContact;
ArrayList<com.example.final_pro.Emergency_ContactModel> modelArrayList;
SqliteManager db;
SQLiteDatabase Sq;
com.example.final_pro.EmergencyAdapter emergencyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmergencyBinding= ActivityEmergencyBinding.inflate(getLayoutInflater());
        setContentView(activityEmergencyBinding.getRoot());
        allocateActivityTitle("Emergency Contact");

        Load_Settings();

        modelArrayList=new ArrayList<>();
        db=new SqliteManager(this);

        recyclerViewContact=findViewById(R.id.recycle);

        modelArrayList=displayData();
        emergencyAdapter=new com.example.final_pro.EmergencyAdapter(modelArrayList,EmergencyActivity.this,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(EmergencyActivity.this,RecyclerView.VERTICAL,false);
        recyclerViewContact.setLayoutManager(linearLayoutManager);
        recyclerViewContact.setAdapter(emergencyAdapter);


SearchView searchView=findViewById(com.android.car.ui.R.id.search);
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterList(newText);
        return true;
    }
});


        //floating button
        cont_fab=findViewById(R.id.float_contact);
        cont_fab.setOnClickListener(view -> {
            //join float to new page
            Intent intent = new Intent(this, com.example.final_pro.ContactActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

    }

    @NonNull
    private ArrayList<com.example.final_pro.Emergency_ContactModel> displayData(){
        Sq=db.getReadableDatabase();
        Cursor cursor= Sq.rawQuery("Select * from tbl_Emergency_cont order by name ",null);
        ArrayList<com.example.final_pro.Emergency_ContactModel> modelArrayList=new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                modelArrayList.add(new com.example.final_pro.Emergency_ContactModel(cursor.getString(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return modelArrayList;
    }

    @Override
    public void onNoteClick(int position) {
        modelArrayList.get(position);
        Intent intent=new Intent(getApplicationContext(), com.example.final_pro.Contact_ViewActivity.class);
        //intent.getExtras();
        intent.putExtra("name",modelArrayList.get(position).getName());
       intent.putExtra("contact",modelArrayList.get(position).getContact());
        startActivity(intent);
        overridePendingTransition(0,0);
    }


    private void filterList(String text){
        ArrayList<com.example.final_pro.Emergency_ContactModel> filteredList =new ArrayList<>();
        for (com.example.final_pro.Emergency_ContactModel model:modelArrayList){
            if(model.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(model);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
        }else{
            emergencyAdapter.setFilteredList(filteredList);
        }
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
        Intent intent= new Intent(this, com.example.final_pro.HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        //finish();
        super.onBackPressed();
    }
}