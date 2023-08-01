package com.example.final_pro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaybuttonActivity extends AppCompatActivity implements PaymentResultListener {
    com.example.final_pro.paymentadapter adapter;
    ActionBar actionBar;
    Button btn;
    int amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybutton);

        Checkout.preload(getApplicationContext());




        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialise amount
                String sAmount ="2500";

                //Convert and round off
                amount = Math.round(Float.parseFloat(sAmount) * 100);
                startPayment();
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
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();

    }

}
