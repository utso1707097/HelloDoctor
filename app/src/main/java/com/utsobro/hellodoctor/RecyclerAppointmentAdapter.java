package com.utsobro.hellodoctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecyclerAppointmentAdapter extends FirebaseRecyclerAdapter<AppointmentModel, RecyclerAppointmentAdapter.newViewHolder> {
    RecyclerAppointmentAdapter(FirebaseRecyclerOptions<AppointmentModel> options){
        super(options);
    }

    @NonNull
    @Override
    public newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row,parent,false);
        return new RecyclerAppointmentAdapter.newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAppointmentAdapter.newViewHolder holder, int position, @NonNull AppointmentModel model) {
        //holder.doctorImage.setImageResource(arrayDoctor.get(position).imageUrl);
        holder.patientName.setText(model.getPatientName());
        holder.patientId = model.getPatientId();
        Glide.with(holder.patientImage.getContext()).load(model.getPatientUrlImage()).into(holder.patientImage);
    }

    public class newViewHolder extends RecyclerView.ViewHolder{

        TextView patientName;
        ImageView patientImage;
        String patientId;

        FirebaseStorage storage;
        StorageReference storageRef,imageRef;


        public newViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientName);
            patientImage = itemView.findViewById(R.id.patientImage);

        }

    }


}
