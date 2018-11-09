package com.example.andrey.testtask.model;

import java.io.Serializable;

public class User implements Serializable {
    private String mEmail;
    private String mName;
    private String mSurname;
    private String mSex;
    private int mAge;

    public User() {

    }

    public User(String pEmail, String pName, String pSurname, String pSex, int pAge) {
        mEmail = pEmail;
        mName = pName;
        mSurname = pSurname;
        mSex = pSex;
        mAge = pAge;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String pEmail) {
        mEmail = pEmail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String pSurname) {
        mSurname = pSurname;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String pSex) {
        mSex = pSex;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int pAge) {
        mAge = pAge;
    }

}
