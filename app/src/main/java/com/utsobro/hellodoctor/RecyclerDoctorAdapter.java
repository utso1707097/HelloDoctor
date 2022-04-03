package com.utsobro.hellodoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerDoctorAdapter extends RecyclerView.Adapter<RecyclerDoctorAdapter.ViewHolder> {
    Context context;
    ArrayList<DoctorModel> arrayDoctor;


    RecyclerDoctorAdapter(Context context, ArrayList<DoctorModel>arrayDoctor){
        this.context = context;
        this.arrayDoctor =arrayDoctor;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.doctorImage.setImageResource(arrayDoctor.get(position).image);
        holder.doctorName.setText(arrayDoctor.get(position).name);
        holder.doctorExpert.setText(arrayDoctor.get(position).expert);
        holder.doctorHospital.setText(arrayDoctor.get(position).hospital);
    }

    @Override
    public int getItemCount() {
        return arrayDoctor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView doctorName,doctorExpert,doctorHospital;
        ImageView doctorImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctorName);
            doctorExpert = itemView.findViewById(R.id.doctorExpert);
            doctorHospital = itemView.findViewById(R.id.doctorHospital);
            doctorImage = itemView.findViewById(R.id.doctorImage);

        }
    }


}
