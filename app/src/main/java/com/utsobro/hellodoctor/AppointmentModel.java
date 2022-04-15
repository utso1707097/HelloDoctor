package com.utsobro.hellodoctor;

public class AppointmentModel {
    //fetching user data who have requested
    String patientId,patientName,patientUrlImage;

    public AppointmentModel(){
        //default constructor
    }

    public AppointmentModel(String patientId,String patientName,String patientUrlImage) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientUrlImage = patientUrlImage;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientUrlImage() {
        return patientUrlImage;
    }
}
