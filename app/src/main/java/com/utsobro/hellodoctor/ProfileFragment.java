package com.utsobro.hellodoctor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private TextView showName,showAge,showEmail,showExpertise,showHospital;
    private ImageView showPic;
    private FirebaseStorage storage;
    private StorageReference storageRef,imageRef;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showName =view.findViewById(R.id.showName);
        showAge =view.findViewById(R.id.showAge);
        showEmail =view.findViewById(R.id.showEmail);
        showExpertise =view.findViewById(R.id.showExpertise);
        showHospital =view.findViewById(R.id.showHospital);
        showPic = view.findViewById(R.id.showPic);

        //get data from firebase
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Doctors").child(uid);
        //Storage reference collect image
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        imageRef = storageRef.child("images/"+uid);
        //downloadDataViaUrl();
        //downloadInMemory();

        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //the user is doctor and fetch its data
                    String name = snapshot.child("name").getValue(String.class);
                    String age = snapshot.child("age").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String expert = snapshot.child("expert").getValue(String.class);
                    String hospital = snapshot.child("medical").getValue(String.class);
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                    Glide.with(showPic.getContext()).load(imageUrl).into(showPic);
                    showName.setText(name);
                    showAge.setText(age +" Years old");
                    showEmail.setText(email);
                    showHospital.setVisibility(View.VISIBLE);
                    showExpertise.setVisibility(View.VISIBLE);
                    showExpertise.setText(expert);
                    showHospital.setText(hospital);

                 }
                else{
                    //the user is not a doctor he is a patient
                    showPatient();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

    void showPatient(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Patients").child(uid);
        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                     String name = snapshot.child("name").getValue(String.class);
                     String age = snapshot.child("age").getValue(String.class);
                     String email = snapshot.child("email").getValue(String.class);
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                    Glide.with(showPic.getContext()).load(imageUrl).into(showPic);
                    showName.setText(name);
                    showAge.setText( age +" Years old");
                    showEmail.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

/*
    void downloadDataViaUrl(){
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                showPic.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Failed to fetch image from uri",Toast.LENGTH_SHORT).show();
            }
        });
    }
 */

    /*
    private void downloadInMemory() {
        //long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                showPic.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(),"Failed to fetch image from memory",Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
}