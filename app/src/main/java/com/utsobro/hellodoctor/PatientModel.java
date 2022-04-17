package com.utsobro.hellodoctor;

public class PatientModel {
    String doctorId,doctorName,doctorUrlImage,rejected,visibility;

    public PatientModel(){

    }

    public PatientModel(String doctorId,String doctorName,String doctorUrlImage,String rejected,String visibility) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorUrlImage = doctorUrlImage;
        this.rejected = rejected;
        this.visibility = visibility;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorUrlImage() {
        return doctorUrlImage;
    }

    public String getRejected() {
        return rejected;
    }

    public String getVisibility() {
        return visibility;
    }
}
