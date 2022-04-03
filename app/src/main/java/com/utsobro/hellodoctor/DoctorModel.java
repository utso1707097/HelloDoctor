package com.utsobro.hellodoctor;

public class DoctorModel {
    int image;
    String name,expert,hospital;

    public DoctorModel(String name, String expert, String hospital, int image) {
        this.name = name;
        this.expert = expert;
        this.hospital = hospital;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getExpert() {
        return expert;
    }

    public String getHospital() {
        return hospital;
    }

    public int getImage() {
        return image;
    }
}
