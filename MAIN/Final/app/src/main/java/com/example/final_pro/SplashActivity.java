package com.example.final_pro;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // FirebaseUser user= firebaseAuth.getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth!=null){
            FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        }

        Thread thread=new Thread(){
            @Override
            public void run() {
                //  super.run();
                try {
                    sleep(2000);

                }catch (Exception e){
                    e.printStackTrace();
                }finally{

                    FirebaseUser user=firebaseAuth.getCurrentUser();

                    if(user == null){
                    Intent intent=new Intent(SplashActivity.this,Home_LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                    }else{
                        Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }
                }

            }
        };thread.start();
    }
}