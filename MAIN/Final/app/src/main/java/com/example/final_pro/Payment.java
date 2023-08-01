package com.example.final_pro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    private EditText amountEdt;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button pay = findViewById(R.id.paybtn);
        amountEdt = findViewById(R.id.idEdtAmount);
        Checkout.preload(getApplicationContext());

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // amount that is entered by user.
                String samount= amountEdt.getText().toString();
                // rounding off the amount.
                amount = Math.round(Float.parseFloat(samount) * 100);
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
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Bill Payment");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
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
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}

