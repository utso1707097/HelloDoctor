package com.utsobro.hellodoctor;

public class DoctorHelperClass {
    String name,age,email,expert,medical,userUid;
    public DoctorHelperClass(){

    }

    public DoctorHelperClass(String name, String age, String email, String expert, String medical,String userUid) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.expert = expert;
        this.medical = medical;
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
