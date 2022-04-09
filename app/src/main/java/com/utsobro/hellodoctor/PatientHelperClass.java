package com.utsobro.hellodoctor;

public class PatientHelperClass {
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

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    String name,age,email,userUid;
    public PatientHelperClass(){

    }

    public PatientHelperClass(String name, String age, String email,String userUid) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.userUid = userUid;
    }


}
