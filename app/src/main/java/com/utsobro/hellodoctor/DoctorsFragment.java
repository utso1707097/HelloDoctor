package com.utsobro.hellodoctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorsFragment extends Fragment {
    ArrayList<DoctorModel> arrayDoctor = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerDoctorAdapter adapter;
    DatabaseReference databaseReference ;

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        recyclerView = view.findViewById(R.id.recyclerDoctor);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<DoctorModel> options =
                new FirebaseRecyclerOptions.Builder<DoctorModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"),DoctorModel.class)
                .build();

        /*
        arrayDoctor.add(new DoctorModel("Utso","Psychologist","Dhaka Medical College",R.drawable.profileblank));
        arrayDoctor.add(new DoctorModel("Mithila","Dermatologist","Khulna Medical College",R.drawable.profileblank));
        arrayDoctor.add(new DoctorModel("Rahi","Medicine","Rajshahi Medical College",R.drawable.profileblank));
        */


        //RecyclerDoctorAdapter adapter = new RecyclerDoctorAdapter(getActivity(),arrayDoctor);
        //recyclerView.setAdapter(adapter);

        adapter =new RecyclerDoctorAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}