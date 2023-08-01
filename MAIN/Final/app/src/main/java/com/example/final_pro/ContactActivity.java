package com.example.final_pro;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

public class ContactActivity extends AppCompatActivity {

    ActionBar actionBar;
    EditText nametxt,numtxt;
    Button button;
    SqliteManager db;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Load_Settings();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New Contact");
        }
        db=new SqliteManager(this);

        nametxt=findViewById(R.id.c_name);
        numtxt=findViewById(R.id.c_number);


        button=findViewById(R.id.C_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String namet=nametxt.getText().toString();
              String contt=numtxt.getText().toString();
                if (TextUtils.isEmpty(namet)){
                    nametxt.setError("enter name");
                    return;
                }
                if(TextUtils.isEmpty(contt)){
                    numtxt.setError("enter contact number");
                    return;
                }
                SQLiteDatabase Sq=db.getWritableDatabase();
                ContentValues cv=new ContentValues();
                cv.put("name",namet);
                cv.put("contact",contt);

                float res= Sq.insert("tbl_Emergency_cont",null,cv);

                if(res==-1) {
                    Toast.makeText(ContactActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                   // return "Failed";
                    cleardata();
                }else{
                    //return "Successfully inserted";
                    Toast.makeText(ContactActivity.this,"Successfully saved",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),EmergencyActivity.class);
                   startActivity(intent);
                   overridePendingTransition(0,0);
                }

            }
        });

    }
   private void cleardata(){
        nametxt.setText("");
        numtxt.setText("");
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
        Intent intent= new Intent(this,EmergencyActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        //finish();
        super.onBackPressed();
    }
}