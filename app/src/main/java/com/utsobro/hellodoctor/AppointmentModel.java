package com.utsobro.hellodoctor;

public class AppointmentModel {
    //fetching user data who have requested
    String name,imageUrl,age,email,userUid;

    public AppointmentModel(){
        //default constructor
    }

    public AppointmentModel(String age,String email,String imageUrl, String name,String userUid) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.imageUrl = imageUrl;
        this.userUid = userUid;
    }

    public String getName() {
        return name;
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
