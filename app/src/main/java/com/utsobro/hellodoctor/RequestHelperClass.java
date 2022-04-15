package com.utsobro.hellodoctor;

public class RequestHelperClass {
    String patientName,patientId;
    public RequestHelperClass(){

    }

    public RequestHelperClass(String patientName,String patientId){
        this.patientName = patientName;
        this.patientId = patientId;
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

}

