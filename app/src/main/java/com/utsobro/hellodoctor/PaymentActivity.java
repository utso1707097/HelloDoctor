package com.utsobro.hellodoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {
    TextView payAmount;
    Button btnPay;
    String amountPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payAmount = findViewById(R.id.payAmount);
        btnPay = findViewById(R.id.btnPayAmount);
        amountPay = "300";
        //Toast.makeText(this,"pay "+amountPay,Toast.LENGTH_SHORT).show();
        payAmount.setText(amountPay);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String samount = amountPay;
                // rounding off the amount.
                int amount = Math.round(Float.parseFloat(samount) * 100);
                // initialize Razorpay account.
                Checkout checkout = new Checkout();
                // set your id as below
                checkout.setKeyID("rzp_test_5GaycFli33h1Xy");
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", "Doctor Appointment");

                    // put description
                    object.put("description", "pay appointment payment");
                    // to set theme color
                    object.put("theme.color", "");
                    // put the currency
                    object.put("currency", "BDT");
                    // put amount
                    object.put("amount", amount);
                    // put mobile number
                    object.put("prefill.contact", "01733178283");

                    // put email
                    object.put("prefill.email", "meem222297@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(PaymentActivity.this, object);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
    }

    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}