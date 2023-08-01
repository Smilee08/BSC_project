package com.example.final_pro;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    ImageView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        view1 = findViewById(R.id.ig1);
        view1.setImageResource(getIntent().getIntExtra("imageUri", 1));
       // view1.setImageResource(getIntent().getIntExtra("imageUri", 1));

    }
}