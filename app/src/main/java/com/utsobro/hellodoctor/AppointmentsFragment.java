package com.utsobro.hellodoctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
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
    private  RecyclerAppointmentPatientAdapter adapter1;

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

        FirebaseRecyclerOptions<PatientModel> options1 =
                new FirebaseRecyclerOptions.Builder<PatientModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AppointmentPatient").child(currentUser),PatientModel.class)
                        .build();


        recyclerAppointment.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter =new RecyclerAppointmentAdapter(options);
        adapter1 = new RecyclerAppointmentPatientAdapter(options1);

        ConcatAdapter concatAdapter = new ConcatAdapter(adapter,adapter1);
        recyclerAppointment.setAdapter(concatAdapter);


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter1.startListening();
        adapter.startListening();
        adapter1.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter1.stopListening();
        adapter.stopListening();
    }

}