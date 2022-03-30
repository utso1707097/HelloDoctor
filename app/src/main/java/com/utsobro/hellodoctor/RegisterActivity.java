package com.utsobro.hellodoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private TextView goBackLogin;
    private EditText registerName,registerEmail,registerPassword,retypePassword;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    //for profile picture
    private ImageView profilePic;
    private final int GALLERY_REQ_CODE=1000;
    private Uri imageUri;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //Doctor registration
    private Switch switchDoctor;
    private Spinner hospitalName,expertIn;


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
        firebaseStorage=FirebaseStorage.getInstance();


        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating your account");
        progressDialog.setCanceledOnTouchOutside(false);

        //profile pic
        firebaseStorage = FirebaseStorage.getInstance();
        profilePic = findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                //access internal storage to external storage
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);

            }
        });
        //Doctor registration
        switchDoctor = findViewById(R.id.switchDoctor);
        hospitalName = findViewById(R.id.hospitalName);
        expertIn = findViewById(R.id.expertIn);

        //Set up the hospital spinner
        String[] hospitalNames =getResources().getStringArray(R.array.hospital_names);
        ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item,hospitalNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalName.setAdapter(arrayAdapter);

        //set up the expertIn spinner
        String[] expertiseFields =getResources().getStringArray(R.array.expertise_fileds);
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item,expertiseFields);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expertIn.setAdapter(arrayAdapter1);

        switchDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchDoctor.isChecked()){
                    hospitalName.setVisibility(View.VISIBLE);
                    expertIn.setVisibility(View.VISIBLE);
                }
                else{
                    hospitalName.setVisibility(View.GONE);
                    expertIn.setVisibility(View.GONE);
                }
            }
        });



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
                Toast.makeText(RegisterActivity.this,"account created with\n"+email,Toast.LENGTH_SHORT).show();
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
    //Profile Picture setup


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
    }
    

}