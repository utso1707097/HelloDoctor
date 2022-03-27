package com.utsobro.hellodoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView registerButtonId;
    private EditText loginEmail,loginPassword;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    //check if user is already logged in
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Sign in a user
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword =findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        firebaseAuth =FirebaseAuth.getInstance();
        checkUser();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Logging In");
        progressDialog.setCanceledOnTouchOutside(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2 = loginEmail.getText().toString().trim();
                String password2 = loginPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email2)){
                Toast.makeText(LoginActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
            }
                else if(TextUtils.isEmpty(password2)){
                Toast.makeText(LoginActivity.this,"Please enter the password",Toast.LENGTH_SHORT).show();
            }
                else{
                    firebaseLogin(email2,password2);
                }


            }
        });

        //go to register
        registerButtonId = findViewById(R.id.registerButtonId);
        Intent goRegister;
        goRegister = new Intent(LoginActivity.this,RegisterActivity.class);
        registerButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goRegister);
            }
        });
    }
    void firebaseLogin(String email,String password){
        //show progress
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        progressDialog.dismiss();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(LoginActivity.this,"Logged in with \n"+email,Toast.LENGTH_SHORT).show();
                        //open profile activity
                        startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login failure
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Log in failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void checkUser(){
        //check user if already logged in
        //if already logged in open profile activity

        //get the current user
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            //User already logged in
            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
            finish();
        }
    }
}