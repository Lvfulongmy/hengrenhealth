package com.hengrunjiankang.health.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/27.
 */

public class AccounEntity {

   private String  FullName;
    private String Sex;
    private String Birthday;
    private String IdcardNumber;
    private String MobileNumber;
    private String PhoneNumber;
    private String UserJob;
    private String AcademicQualification;
    private String WorkUnit;
    private String UserDuties;
    private ArrayList<Address> Addresses;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getIdcardNumber() {
        return IdcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        IdcardNumber = idcardNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserJob() {
        return UserJob;
    }

    public void setUserJob(String userJob) {
        UserJob = userJob;
    }

    public String getAcademicQualification() {
        return AcademicQualification;
    }

    public void setAcademicQualification(String academicQualification) {
        AcademicQualification = academicQualification;
    }

    public String getWorkUnit() {
        return WorkUnit;
    }

    public void setWorkUnit(String workUnit) {
        WorkUnit = workUnit;
    }

    public String getUserDuties() {
        return UserDuties;
    }

    public void setUserDuties(String userDuties) {
        UserDuties = userDuties;
    }

    public ArrayList<Address> getAddresses() {
        return Addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        Addresses = addresses;
    }
}
