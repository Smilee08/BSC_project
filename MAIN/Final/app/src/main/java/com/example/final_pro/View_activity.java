package com.example.final_pro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class View_activity extends AppCompatActivity implements PaymentResultListener {
    RecyclerView recview;
    com.example.final_pro.paymentadapter adapter;
    FloatingActionButton fb;
    private Object RecyclerViewInterface;

    //Initialise button
    Button pay;
    int amount;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Load_Settings();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Payment");
        }

        //Assign variable
        pay=findViewById(R.id.pay);

        Checkout.preload(getApplicationContext());



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialise amount
                String sAmount ="2500";

                //Convert and round off
                amount = Math.round(Float.parseFloat(sAmount) * 100);
                startPayment();
            }
        });


        setTitle("Search here..");

        recview=(RecyclerView)findViewById(R.id.recview1);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<paymentusers> options =
                new FirebaseRecyclerOptions.Builder<paymentusers>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Pay"), paymentusers.class)
                        .build();

        adapter=new com.example.final_pro.paymentadapter(options);
        recview.setAdapter(adapter);

        fb=(FloatingActionButton)findViewById(R.id.fadd1);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Form.class));
                overridePendingTransition(0,0);
            }
        });

    }

    private void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_oHEuKP7dtLtNDQ");
        /**
         * Reference to current activity
         */
        final Activity activity = this;
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Maintenance Bill Payment");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#378B97");
            options.put("currency", "INR");
            options.put("amount",amount);//pass amount in currency subunits
            options.put("prefill.email", "aasthasangle6@gmail.com");
            options.put("prefill.contact","9869330862");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pay");
        databaseReference.keepSynced(true);
        FirebaseRecyclerOptions<paymentusers> options =
                new FirebaseRecyclerOptions.Builder<paymentusers>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Pay").orderByChild("name").startAt(s).endAt(s+"\uf8ff"),paymentusers.class)
                        .build();

        adapter=new com.example.final_pro.paymentadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();

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
        Intent intent= new Intent(this,View_activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        //finish();
        super.onBackPressed();
    }
}
