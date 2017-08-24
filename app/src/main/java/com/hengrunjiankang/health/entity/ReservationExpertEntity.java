package com.hengrunjiankang.health.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/22.
 */

public class ReservationExpertEntity {
    private String Id;
    private String UserId;
    private String ExpertId;
    private String HealthRecordFriendlyId;
    private String Birthday;
    private String ReservationTime;
    private String Sex;
    private String Remark;
    private String CreateTime;
    private String EvaluateValue;
    private String EvaluateContent;
    private String EvaluateTagCategory;
    private String EvaluateTag;
    private PatientUserEntity PatientUser;
    private ArrayList<ProfileEntity> HealthRecord;

    public class PatientUserEntity {
        private String FullName;
        private String MobileNumber;
        private String Nickname;
        private String UserDuties;

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getExpertId() {
        return ExpertId;
    }

    public void setExpertId(String expertId) {
        ExpertId = expertId;
    }

    public String getHealthRecordFriendlyId() {
        return HealthRecordFriendlyId;
    }

    public void setHealthRecordFriendlyId(String healthRecordFriendlyId) {
        HealthRecordFriendlyId = healthRecordFriendlyId;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getReservationTime() {
        return ReservationTime;
    }

    public void setReservationTime(String reservationTime) {
        ReservationTime = reservationTime;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getEvaluateValue() {
        return EvaluateValue;
    }

    public void setEvaluateValue(String evaluateValue) {
        EvaluateValue = evaluateValue;
    }

    public String getEvaluateContent() {
        return EvaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        EvaluateContent = evaluateContent;
    }

    public String getEvaluateTagCategory() {
        return EvaluateTagCategory;
    }

    public void setEvaluateTagCategory(String evaluateTagCategory) {
        EvaluateTagCategory = evaluateTagCategory;
    }

    public String getEvaluateTag() {
        return EvaluateTag;
    }

    public void setEvaluateTag(String evaluateTag) {
        EvaluateTag = evaluateTag;
    }

    public PatientUserEntity getPatientUser() {
        return PatientUser;
    }

    public void setPatientUser(PatientUserEntity patientUser) {
        PatientUser = patientUser;
    }

    public ArrayList<ProfileEntity> getHealthRecord() {
        return HealthRecord;
    }

    public void setHealthRecord(ArrayList<ProfileEntity> healthRecord) {
        HealthRecord = healthRecord;
    }
}