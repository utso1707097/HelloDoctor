package com.utsobro.hellodoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutFragment extends Fragment {

    private TextView showEmail;
    private FirebaseAuth firebaseAuth;
    private Button logoutButton;

    public LogoutFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_logout, container, false);
        showEmail = view.findViewById(R.id.showEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        logoutButton = view.findViewById(R.id.logoutButton);
        checkUser();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        return view;
    }
    void checkUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(getActivity(),LoginActivity.class));
            ((Activity) getActivity()).overridePendingTransition(0,0);
        }
        else{
            String email = firebaseUser.getEmail();
            showEmail.setText(email);
        }
    }
}