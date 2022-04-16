package com.utsobro.hellodoctor;

public class AppointmentModel {
    //fetching user data who have requested
    String patientId,patientName,patientUrlImage,rejected,visibility;

    public AppointmentModel(){
        //default constructor
    }

    public AppointmentModel(String patientId,String patientName,String patientUrlImage,String rejected,String visibility) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientUrlImage = patientUrlImage;
        this.rejected = rejected;
        this.visibility = visibility;
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

    public String getRejected() {
        return rejected;
    }

    public String getVisibility() {
        return visibility;
    }
}
