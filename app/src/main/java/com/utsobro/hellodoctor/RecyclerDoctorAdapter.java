package com.utsobro.hellodoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RecyclerDoctorAdapter extends FirebaseRecyclerAdapter<DoctorModel,RecyclerDoctorAdapter.viewHolder> {

    RecyclerDoctorAdapter(FirebaseRecyclerOptions<DoctorModel>options){
        super(options);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_row,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position,@NonNull DoctorModel model) {
        //holder.doctorImage.setImageResource(arrayDoctor.get(position).imageUrl);
        holder.showUserUid.setText(model.getUserUid());
        holder.doctorName.setText(model.getName());
        holder.doctorExpert.setText(model.getExpert());
        holder.doctorHospital.setText(model.getMedical());
        Glide.with(holder.doctorImage.getContext()).load(model.getImageUrl()).into(holder.doctorImage);

    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView doctorName,doctorExpert,doctorHospital,showUserUid;
        Button appointmentBtn;
        ImageView doctorImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctorName);
            doctorExpert = itemView.findViewById(R.id.doctorExpert);
            doctorHospital = itemView.findViewById(R.id.doctorHospital);
            doctorImage = itemView.findViewById(R.id.doctorImage);
            showUserUid = itemView.findViewById(R.id.showUserUid);

            appointmentBtn = itemView.findViewById(R.id.appointmentBtn);

            appointmentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(itemView.getContext(),"requested to "+ userUid,Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String requestSenderUserUid = currentUser.getUid();
                    String requestedUserUid = getItem(getAbsoluteAdapterPosition()).userUid;
                    Toast.makeText(itemView.getContext(),requestSenderUserUid + " requested " + requestedUserUid, Toast.LENGTH_LONG).show();
                }
            });

        }
    }


}