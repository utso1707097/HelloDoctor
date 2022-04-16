package com.utsobro.hellodoctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        holder.rejected = model.getRejected();
        holder.visibility = model.getVisibility();
        Glide.with(holder.patientImage.getContext()).load(model.getPatientUrlImage()).into(holder.patientImage);
    }

    public class newViewHolder extends RecyclerView.ViewHolder{

        TextView patientName,showText;
        ImageView patientImage;
        String patientId,rejected,visibility;
        Button acceptBtn,rejectBtn;
        LinearLayout appointmentLayout;
        DatabaseReference rootRef,uidRef;


        public newViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientName);
            showText = itemView.findViewById(R.id.showText);
            patientImage = itemView.findViewById(R.id.patientImage);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
            appointmentLayout = itemView.findViewById(R.id.appointmentLayout);
            rootRef = FirebaseDatabase.getInstance().getReference();
            uidRef = rootRef.child("AppointmentRequest").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptBtn.setVisibility(view.GONE);
                    rejectBtn.setVisibility(view.GONE);
                    //showText.setText("You have an appointment with " +patientName.getText().toString());
                    showText.setText(rejected + visibility);
                }
            });

            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appointmentLayout.setVisibility(view.GONE);
                }
            });

        }

    }


}
