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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String patientName,patientUrlImage;
        FirebaseDatabase rootnode;
        DatabaseReference reference,uidReference,rootRef,uidRef;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctorName);
            doctorExpert = itemView.findViewById(R.id.doctorExpert);
            doctorHospital = itemView.findViewById(R.id.doctorHospital);
            doctorImage = itemView.findViewById(R.id.doctorImage);
            showUserUid = itemView.findViewById(R.id.showUserUid);

            appointmentBtn = itemView.findViewById(R.id.appointmentBtn);

            rootnode = FirebaseDatabase.getInstance();
            reference = rootnode.getReference("AppointmentRequest");

            rootRef = FirebaseDatabase.getInstance().getReference();
            uidRef = rootRef.child("Patients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        patientName = snapshot.child("name").getValue(String.class);
                        patientUrlImage = snapshot.child("imageUrl").getValue(String.class);
                    }
                    else {
                        uidRef = rootRef.child("Doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    patientName = snapshot.child("name").getValue(String.class);
                                    patientUrlImage = snapshot.child("imageUrl").getValue(String.class);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            appointmentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(itemView.getContext(),"requested to "+ userUid,Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String requestSenderUserUid = currentUser.getUid();
                    String requestedUserUid = getItem(getAbsoluteAdapterPosition()).userUid;
                    String requestedUserName = getItem(getAbsoluteAdapterPosition()).name;
                    String rejected = "not rejected";
                    String visibility = "invisible";

                    RequestHelperClass requestHelperClass = new RequestHelperClass(patientName,requestSenderUserUid,patientUrlImage,rejected,visibility);
                    reference.child(getItem(getAbsoluteAdapterPosition()).userUid).child(currentUser.getUid()).setValue(requestHelperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(itemView.getContext(),"You " + " requested " + requestedUserName + " to get an appointment", Toast.LENGTH_LONG).show();
                        }
                    });



                }
            });

        }
    }


}
