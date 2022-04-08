package com.utsobro.hellodoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import papaya.in.sendmail.SendMail;

public class OtpVerification extends AppCompatActivity {
    TextView resendOtp;
    EditText otpBlock1,otpBlock2,otpBlock3,otpBlock4;
    Button btnVerify;
    String newOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otpBlock1 = findViewById(R.id.otpBlock1);
        otpBlock2 = findViewById(R.id.otpBlock2);
        otpBlock3 = findViewById(R.id.otpBlock3);
        otpBlock4 = findViewById(R.id.otpBlock4);


        btnVerify = findViewById(R.id.btnVerify);
        resendOtp = findViewById(R.id.resendOtp);

        Intent otpIntent = getIntent();
        String email = otpIntent.getStringExtra("emailto");
        String myOtp = otpIntent.getStringExtra("myOtp");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp1 = otpBlock1.getText().toString().trim();
                String otp2 = otpBlock2.getText().toString().trim();
                String otp3 = otpBlock3.getText().toString().trim();
                String otp4 = otpBlock4.getText().toString().trim();
                String confirmOtp = otp1 + otp2 + otp3 + otp4;
                if(TextUtils.equals(myOtp,confirmOtp)||TextUtils.equals(newOtp,confirmOtp)){
                    Toast.makeText(OtpVerification.this,"Email Verification successful.account created with\n"+email,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpVerification.this,ProfileActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(OtpVerification.this,"Otp Verification failed ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int otp = rand.nextInt(10000);
                if(otp<1000)otp = otp + 1000;
                newOtp = String.valueOf(otp);
                SendMail mail = new SendMail("meem222297@gmail.com", "utso222297",
                        email,
                        "Verify your email",
                        "Your OTP for Doctor Verification is " + newOtp);
                mail.execute();
                Toast.makeText(OtpVerification.this,"OTP sent to " + email,Toast.LENGTH_SHORT).show();
            }
        });

    }
}