package com.utsobro.hellodoctor;

public class RequestHelperClass {
    String patientName,patientId,patientUrlImage;
    public RequestHelperClass(){

    }

    public RequestHelperClass(String patientName,String patientId,String patientUrlImage){
        this.patientName = patientName;
        this.patientId = patientId;
        this.patientUrlImage = patientUrlImage;
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
}

