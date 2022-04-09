package com.utsobro.hellodoctor;

public class DoctorModel {
    String name,expert,medical,imageUrl,age,email,userUid;

    public DoctorModel(){

    }


    public DoctorModel(String age,String email, String expert,String imageUrl, String medical,String name,String userUid) {
        this.name = name;
        this.expert = expert;
        this.medical = medical;
        this.age = age;
        this.email = email;
        this.imageUrl = imageUrl;
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public String getExpert() {
        return expert;
    }

    public String getMedical() {
        return medical;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getUserUid() {
        return userUid;
    }
}
