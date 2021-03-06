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
import android.widget.AutoCompleteTextView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.UUID;

import papaya.in.sendmail.SendMail;

public class RegisterActivity extends AppCompatActivity {
    private TextView goBackLogin;
    private EditText registerName,registerEmail,registerPassword,retypePassword,registerAge;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    //for profile picture
    private ImageView profilePic;
    private final int GALLERY_REQ_CODE=1000;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    //private UUID uuid;
    //Doctor registration
    private Switch switchDoctor;
    private AutoCompleteTextView hospitalName,expertIn;

    //Firebase Database
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerName);
        registerAge = findViewById(R.id.registerAge);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        retypePassword = findViewById(R.id.retypePassword);
        registerBtn = findViewById(R.id.registerBtn);
        //Doctor registration
        switchDoctor = findViewById(R.id.switchDoctor);

        hospitalName = findViewById(R.id.hospitalName);

        expertIn = findViewById(R.id.expertIn);

        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating your account");
        progressDialog.setCanceledOnTouchOutside(false);

        //profile pic selection
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



        //set up hospital auto complete
        String[] hospitalNames =getResources().getStringArray(R.array.hospital_names);
        ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item,hospitalNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        hospitalName.setAdapter(arrayAdapter);
        hospitalName.setThreshold(1);

        //set up expertIn auto complete

        String[] expertiseFields =getResources().getStringArray(R.array.expertise_fileds);
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item,expertiseFields);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        expertIn.setAdapter(arrayAdapter1);
        expertIn.setThreshold(1);

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
                    //Firebase database


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
                Toast.makeText(RegisterActivity.this,"Verify your email adress",Toast.LENGTH_SHORT).show();
                //copied image and other data in firebase
                rootNode = FirebaseDatabase.getInstance();
                //uuid = UUID.randomUUID();
                if(switchDoctor.isChecked()){
                    reference = rootNode.getReference("Doctors");
                    //get all the values need to store and pass it to helper class
                    String name = registerName.getText().toString();
                    String email1 = registerEmail.getText().toString();
                    String age = registerAge.getText().toString();
                    String expert = expertIn.getText().toString();
                    String hospital = hospitalName.getText().toString();
                    String userUid = firebaseUser.getUid();


                    DoctorHelperClass doctorHelperClass =new DoctorHelperClass(name,age,email1,expert,hospital,userUid);
                    reference.child(firebaseUser.getUid()).setValue(doctorHelperClass);
                }
                else{
                    reference = rootNode.getReference("Patients");
                    //get all the values need to store and pass it to helper class
                    String name = registerName.getText().toString();
                    String email1 = registerEmail.getText().toString();
                    String age = registerAge.getText().toString();
                    String userUid = firebaseUser.getUid();

                    PatientHelperClass patientHelperClass = new PatientHelperClass(name,age,email1,userUid);
                    reference.child(firebaseUser.getUid()).setValue(patientHelperClass);
                }
                if(imageUri != null){
                    StorageReference ref = storageRef.child("images/" + firebaseUser.getUid());
                    ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(RegisterActivity.this, "Image uploaded", Toast.LENGTH_SHORT) .show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //You will get donwload URL in uri
                                    //Adding that URL to Realtime database
                                    reference.child(firebaseUser.getUid()).child("imageUrl").setValue(uri.toString());
                                }
                            });
                        }
                    });
                }

                //OTP verification
                Random rand = new Random();
                int myOtp = rand.nextInt(10000);
                if(myOtp<1000) myOtp = myOtp + 1000;
                //open profile activity
                Intent otpIntent = new Intent(RegisterActivity.this,OtpVerification.class);
                otpIntent.putExtra("myOtp",String.valueOf(myOtp));
                otpIntent.putExtra("emailto",email);
                SendMail mail = new SendMail("meem222297@gmail.com", "utso222297",
                        email,
                        "Verify your email",
                        "Your OTP for Doctor Verification is " + myOtp);
                mail.execute();


                startActivity(otpIntent);
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
    //Profile Picture upload


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
    }


}