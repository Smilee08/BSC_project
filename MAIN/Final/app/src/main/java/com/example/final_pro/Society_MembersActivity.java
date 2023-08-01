package com.example.final_pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import com.example.final_pro.databinding.ActivitySocietyMembersBinding;

public class Society_MembersActivity extends DrawerBaseActivity {
    ActivitySocietyMembersBinding activitySocietyMembersBinding;
    public CardView card1;
    public CardView card2;
    public CardView card3;
    public CardView card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySocietyMembersBinding = ActivitySocietyMembersBinding.inflate(getLayoutInflater());
        setContentView(activitySocietyMembersBinding.getRoot());
        allocateActivityTitle("Society Members");

        Load_Settings();

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);

        card1.setOnClickListener(view -> {
            Intent intent = new Intent(Society_MembersActivity.this, WingA.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });
        card2.setOnClickListener(view -> {
            Intent intent = new Intent(Society_MembersActivity.this, WingB.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });
        card3.setOnClickListener(view -> {
            Intent intent = new Intent(Society_MembersActivity.this, WingC.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });
        card4.setOnClickListener(view -> {
            Intent intent = new Intent(Society_MembersActivity.this, WingD.class);
            startActivity(intent);
            overridePendingTransition(0,0);
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
        public void onBackPressed () {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
            //super.onBackPressed();
        }
}