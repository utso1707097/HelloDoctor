package com.utsobro.hellodoctor;

import android.text.TextUtils;
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


public class RecyclerAppointmentPatientAdapter extends FirebaseRecyclerAdapter<PatientModel, RecyclerAppointmentPatientAdapter.patientViewHolder> {
    RecyclerAppointmentPatientAdapter(FirebaseRecyclerOptions<PatientModel> options){
        super(options);
    }
    @NonNull
    @Override
    public RecyclerAppointmentPatientAdapter.patientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_patient_row,parent,false);
        return new RecyclerAppointmentPatientAdapter.patientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAppointmentPatientAdapter.patientViewHolder holder, int position, @NonNull PatientModel model) {
        //holder.doctorImage.setImageResource(arrayDoctor.get(position).imageUrl);
        holder.doctorId = model.getDoctorId();
        holder.rejected = model.getRejected();
        holder.visibility = model.getVisibility();
        Glide.with(holder.doctorImage.getContext()).load(model.getDoctorUrlImage()).into(holder.doctorImage);

        /*
        String patientVisibility = FirebaseDatabase.getInstance().getReference().child("AppointmentPatient").child(holder.uid).child("visibility").get(S);
        Toast.makeText(holder.appointmentLayout.getContext(), "Hi" +patientVisibility,Toast.LENGTH_LONG).show();
        if(TextUtils.equals(patientVisibility,"visible")){
            holder.patientName.setText(model.getPatientName());
            holder.appointmentLayout.setVisibility(View.VISIBLE);
        }
        */


        if(TextUtils.equals(model.getRejected(),"not rejected") && TextUtils.equals(model.getVisibility(),"invisible")){
            holder.doctorName.setText(model.getDoctorName());
            holder.appointmentLayout.setVisibility(View.VISIBLE);
            holder.showText.setText("Your reqeust is pending");
        }

        else if(TextUtils.equals(model.getRejected(),"rejected") && TextUtils.equals(model.getVisibility(),"invisible")){
            holder.doctorName.setText(model.getDoctorName());
            holder.appointmentLayout.setVisibility(View.GONE);

        }

        /*
        if (user.getUserEmail().equals(Utils.decodeEmail(userEmail))) {
            viewHolder.llMain.setVisibility(View.GONE);
            return;
        }
         */

        else if(TextUtils.equals(model.getRejected(),"accepted") && TextUtils.equals(model.getVisibility(),"visible")){
            holder.doctorName.setText(model.getDoctorName());
            holder.showText.setText( holder.doctorName.getText().toString() + "has accepted your appointment request");
        }
    }

    public class patientViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName,showText;
        ImageView doctorImage;
        String doctorId,rejected,visibility;
        LinearLayout appointmentLayout;
        /*
        DatabaseReference rootRef,uidRef,patientRef;
        String uid;
         */

        public patientViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.docName);
            showText = itemView.findViewById(R.id.showMyText);
            doctorImage = itemView.findViewById(R.id.docImage);
            appointmentLayout = itemView.findViewById(R.id.appointmentPatientLayout);

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

        }

    }


}
