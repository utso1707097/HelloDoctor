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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private TextView goBackLogin;
    private EditText registerName,registerEmail,registerPassword,retypePassword;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        retypePassword = findViewById(R.id.retypePassword);
        registerBtn = findViewById(R.id.registerBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating your account");
        progressDialog.setCanceledOnTouchOutside(false);

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

        //firebase authentication
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = registerName.getText().toString().trim();
                String email1 = registerEmail.getText().toString().trim();
                String password1 = registerPassword.getText().toString().trim();
                String password2 = retypePassword.getText().toString().trim();
                if(TextUtils.isEmpty(name1)){
                    Toast.makeText(RegisterActivity.this,"Please enter the name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email1)){
                    Toast.makeText(RegisterActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password1)){
                    Toast.makeText(RegisterActivity.this,"Please enter the password",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password2)){
                    Toast.makeText(RegisterActivity.this,"Please confirm the password",Toast.LENGTH_SHORT).show();
                }
                else if(password1.length()<6){
                    Toast.makeText(RegisterActivity.this,"password is too short",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.equals(password1,password2)){
                    regis(email1,password1);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"password and confirm password don't match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void regis(String email,String password){
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //signup Success
                progressDialog.dismiss();
                //get user info
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String email = firebaseUser.getEmail();
                Toast.makeText(RegisterActivity.this,"account created \n"+email,Toast.LENGTH_SHORT).show();
                //open profile activity
                startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                finish();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        //signup failed
                        Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}