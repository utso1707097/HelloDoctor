package com.utsobro.hellodoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private TextView goBackLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //go back to login
        goBackLogin = findViewById(R.id.goBackLogin);
        Intent goRegister;
        goRegister = new Intent(RegisterActivity.this,LoginActivity.class);
        goBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goRegister);
            }
        });

    }
}