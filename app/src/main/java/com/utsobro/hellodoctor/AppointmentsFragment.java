package com.utsobro.hellodoctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

public class AppointmentsFragment extends Fragment {

    private RecyclerView recyclerAppointment;

    public AppointmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        recyclerAppointment = view.findViewById(R.id.recyclerAppointment);

        recyclerAppointment.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }
}