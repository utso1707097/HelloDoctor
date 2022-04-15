package com.utsobro.hellodoctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentsFragment extends Fragment {

    private RecyclerView recyclerAppointment;
    private RecyclerAppointmentAdapter adapter;

    public AppointmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        recyclerAppointment = view.findViewById(R.id.recyclerAppointment);
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseRecyclerOptions<AppointmentModel> options =
                new FirebaseRecyclerOptions.Builder<AppointmentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AppointmentRequest").child(currentUser),AppointmentModel.class)
                        .build();

        recyclerAppointment.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter =new RecyclerAppointmentAdapter(options);
        recyclerAppointment.setAdapter(adapter);


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