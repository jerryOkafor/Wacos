package com.dipoletech.wacos.model;/**
 * Created by DABBY(3pleMinds) on 26-Jan-16.
 */

/**
 * DABBY(3pleMinds) 26-Jan-16 11:15 PM 2016 01
 * 26 23 15 Wacos
 **/
public class User {
    private String birthDay,displayName;
    private double joinedDate;
    private String name, email, suretyId, upLineSuretyId, phone, uid,profileImage;


    public  User()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDay() {
        return birthDay;
    }


    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(double joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getSuretyId() {
        return suretyId;
    }

    public void setSuretyId(String suretyId) {
        this.suretyId = suretyId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpLineSuretyId() {
        return upLineSuretyId;
    }

    public void setUpLineSuretyId(String upLineSuretyId) {
        this.upLineSuretyId = upLineSuretyId;
    }

    //overrides the to string and returns the correct user name for naming purpose


    @Override
    public String toString() {
        return super.toString();
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
