package com.utsobro.hellodoctor;

public class RequestHelperClass {
    String patientName,patientId,patientUrlImage,rejected,visibility;
    public RequestHelperClass(){

    }

    public RequestHelperClass(String patientName,String patientId,String patientUrlImage,String rejected,String visibility){
        this.patientName = patientName;
        this.patientId = patientId;
        this.patientUrlImage = patientUrlImage;
        this.rejected = rejected;
        this.visibility = visibility;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientUrlImage() {
        return patientUrlImage;
    }

    public void setPatientUrlImage(String patientUrlImage) {
        this.patientUrlImage = patientUrlImage;
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

