package com.utsobro.hellodoctor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateProfile extends AppCompatActivity {


    private ImageView editPic;
    private EditText editName,editAge;
    private AutoCompleteTextView editHospital,editExpertise;
    private final int GALLERY_REQ_CODE=1000;
    private Uri imageUri;
    private Button btnConfirm;
    private FirebaseUser currentUser;
    private FirebaseStorage storage;
    private StorageReference storageRef,imageRef;
    private DatabaseReference databaseReference;
    private FirebaseDatabase rootNode;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editPic = findViewById(R.id.editPic);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editHospital = findViewById(R.id.editHospital);
        editExpertise = findViewById(R.id.editExpertise);
        btnConfirm = findViewById(R.id.btnConfirm);

        progressDialog = new ProgressDialog(UpdateProfile.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Updating Your Profile");
        progressDialog.setCanceledOnTouchOutside(false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intent intent = getIntent();


        editName.setHint(intent.getStringExtra("name"));
        editAge.setHint(intent.getStringExtra("age"));

        if(intent.getStringExtra("hospital").length()!= 0) {

            databaseReference = rootNode.getReference("Doctors");

            editHospital.setVisibility(View.VISIBLE);
            editExpertise.setVisibility(View.VISIBLE);

            editHospital.setHint(intent.getStringExtra("hospital"));
            editExpertise.setHint(intent.getStringExtra("expertise"));

            String[] hospitalNames =getResources().getStringArray(R.array.hospital_names);
            ArrayAdapter arrayAdapter = new ArrayAdapter(UpdateProfile.this, android.R.layout.simple_spinner_item,hospitalNames);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            editHospital.setAdapter(arrayAdapter);
            editHospital.setThreshold(1);

            String[] expertiseFields =getResources().getStringArray(R.array.expertise_fileds);
            ArrayAdapter arrayAdapter1 = new ArrayAdapter(UpdateProfile.this, android.R.layout.simple_spinner_item,expertiseFields);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
            editExpertise.setAdapter(arrayAdapter1);
            editExpertise.setThreshold(1);
        }
        else{
            databaseReference = rootNode.getReference("Patients");
        }

        //Image select
        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                //access internal storage to external storage
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String name = editName.getText().toString().trim();
                String age = editAge.getText().toString().trim();
                String hospital = editHospital.getText().toString().trim();
                String expert = editExpertise.getText().toString().trim();
                if(imageUri != null){
                    imageRef = storageRef.child("images/" + currentUser.getUid());
                    imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.cancel();
                                    Toast.makeText(UpdateProfile.this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    databaseReference.child(currentUser.getUid()).child("imageUrl").setValue(uri.toString());
                                }
                            });
                        }
                    });
                }
                if(imageUri == null){
                    progressDialog.cancel();
                    Toast.makeText(UpdateProfile.this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                }
                if(name.length()>0){
                    databaseReference.child(currentUser.getUid()).child("name").setValue(name);
                }
                if(age.length()>0){
                    databaseReference.child(currentUser.getUid()).child("age").setValue(age);
                }
                if(expert.length()>0){
                    databaseReference.child(currentUser.getUid()).child("expert").setValue(expert);
                }
                if(hospital.length()>0){
                    databaseReference.child(currentUser.getUid()).child("medical").setValue(hospital);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();
            editPic.setImageURI(imageUri);
        }
    }

}