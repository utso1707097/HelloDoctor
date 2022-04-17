package com.utsobro.hellodoctor;

public class RequestedDoctorHelperClass {
    String doctorName,doctorId,doctorUrlImage,rejected,visibility;

    public RequestedDoctorHelperClass(){

    }

    public RequestedDoctorHelperClass(String doctorName,String doctorId,String doctorUrlImage,String rejected,String visibility){
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.doctorUrlImage = doctorUrlImage;
        this.rejected = rejected;
        this.visibility = visibility;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorUrlImage() {
        return doctorUrlImage;
    }

    public void setDoctorUrlImage(String doctorUrlImage) {
        this.doctorUrlImage = doctorUrlImage;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
