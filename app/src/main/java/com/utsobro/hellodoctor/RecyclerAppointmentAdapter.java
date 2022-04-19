package com.utsobro.hellodoctor;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        holder.patientId = model.getPatientId();
        holder.rejected = model.getRejected();
        holder.visibility = model.getVisibility();
        Glide.with(holder.patientImage.getContext()).load(model.getPatientUrlImage()).into(holder.patientImage);

        /*
        String patientVisibility = FirebaseDatabase.getInstance().getReference().child("AppointmentPatient").child(holder.uid).child("visibility").get(S);
        Toast.makeText(holder.appointmentLayout.getContext(), "Hi" +patientVisibility,Toast.LENGTH_LONG).show();
        if(TextUtils.equals(patientVisibility,"visible")){
            holder.patientName.setText(model.getPatientName());
            holder.appointmentLayout.setVisibility(View.VISIBLE);
        }
        */

        if(TextUtils.equals(model.getRejected(),"not rejected") && TextUtils.equals(model.getVisibility(),"invisible")){
            holder.patientName.setText(model.getPatientName());
            holder.appointmentLayout.setVisibility(View.VISIBLE);
        }

        else if(TextUtils.equals(model.getRejected(),"rejected") && TextUtils.equals(model.getVisibility(),"invisible")){
            holder.patientName.setText(model.getPatientName());
            holder.acceptBtn.setVisibility(View.GONE);
            holder.rejectBtn.setVisibility(View.GONE);
            holder.showText.setText("You have rejected " +holder.patientName.getText().toString());
        }

        /*
        if (user.getUserEmail().equals(Utils.decodeEmail(userEmail))) {
            viewHolder.llMain.setVisibility(View.GONE);
            return;
        }
         */

        else if(TextUtils.equals(model.getRejected(),"accepted") && TextUtils.equals(model.getVisibility(),"visible")){
            holder.patientName.setText(model.getPatientName());
            holder.acceptBtn.setVisibility(View.GONE);
            holder.rejectBtn.setVisibility(View.GONE);
            holder.showText.setText("You have an appointment with " + holder.patientName.getText().toString());
        }
    }

    public class newViewHolder extends RecyclerView.ViewHolder{

        TextView patientName,showText;
        ImageView patientImage;
        String patientId,rejected,visibility;
        Button acceptBtn,rejectBtn;
        LinearLayout appointmentLayout;
        DatabaseReference rootRef,uidRef,patientRef;
        String uid;


        public newViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientName);
            showText = itemView.findViewById(R.id.showText);
            patientImage = itemView.findViewById(R.id.patientImage);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
            appointmentLayout = itemView.findViewById(R.id.appointmentLayout);

            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            rootRef = FirebaseDatabase.getInstance().getReference();

            /*
            if(rejected == "rejected" && visibility == "invisible"){
                appointmentLayout.setVisibility(itemView.GONE);
            }
            else if(rejected == "accepted" && visibility == "visible"){
                acceptBtn.setVisibility(itemView.GONE);
                rejectBtn.setVisibility(itemView.GONE);
                showText.setText("You have an appointment with " +patientName.getText().toString());
            }
            */

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String patientUid = getItem(getBindingAdapterPosition()).getPatientId();
                    uidRef = rootRef.child("AppointmentRequest").child(uid).child(patientUid);
                    patientRef = rootRef.child("AppointmentPatient").child(patientUid).child(uid); //patientUid = doctorUid
                    patientRef.child("rejected").setValue("accepted");
                    patientRef.child("visibility").setValue("visible");
                    uidRef.child("rejected").setValue("accepted");
                    uidRef.child("visibility").setValue("visible").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            acceptBtn.setVisibility(view.GONE);
                            rejectBtn.setVisibility(view.GONE);
                            showText.setText("You have an appointment with " +patientName.getText().toString());
                        }
                    });
                    //rejected = "accepted";
                    //visibility = "visible";
                    //showText.setText(rejected + visibility);
                }
            });

            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String patientUid = getItem(getBindingAdapterPosition()).getPatientId();
                    uidRef = rootRef.child("AppointmentRequest").child(uid).child(patientUid);
                    patientRef = rootRef.child("AppointmentPatient").child(patientUid).child(uid); //patientUid = doctorUid
                    patientRef.child("visibility").setValue("invisible");
                    uidRef.child("rejected").setValue("rejected");
                    uidRef.child("visibility").setValue("invisible");
                    patientRef.child("rejected").setValue("rejected")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            acceptBtn.setVisibility(view.GONE);
                            rejectBtn.setVisibility(view.GONE);
                            showText.setText("You have rejected " +patientName.getText().toString());
                        }
                    });
                    //showText.setText(rejected + visibility);


                    //rejected = "rejected";
                    //visibility = "invisible";
                }
            });

        }

    }


}
