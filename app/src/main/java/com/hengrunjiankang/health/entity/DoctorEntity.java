package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/8/11.
 */

public class DoctorEntity {
    private String Id;
    private String Name;
    private String SmallImageUrl;
    private String Profile;
    private String Expertise;
    private String UserId;
    private String Duties;
    private String Hospital;
    private String ConsultPrice;
    private String Evaluate;
    private String DutiesGrade;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSmallImageUrl() {
        return SmallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        SmallImageUrl = smallImageUrl;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public String getExpertise() {
        return Expertise;
    }

    public void setExpertise(String expertise) {
        Expertise = expertise;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDuties() {
        return Duties;
    }

    public void setDuties(String duties) {
        Duties = duties;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public String getConsultPrice() {
        return ConsultPrice;
    }

    public void setConsultPrice(String consultPrice) {
        ConsultPrice = consultPrice;
    }

    public String getEvaluate() {
        return Evaluate;
    }

    public void setEvaluate(String evaluate) {
        Evaluate = evaluate;
    }

    public String getDutiesGrade() {
        return DutiesGrade;
    }

    public void setDutiesGrade(String dutiesGrade) {
        DutiesGrade = dutiesGrade;
    }
}
