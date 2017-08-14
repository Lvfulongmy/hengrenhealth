package com.hengrunjiankang.health.okhttp;

/**
 * Created by Administrator on 2017/7/12.
 */

public class UrlObject {
   public static final String BASEURL = "http://192.168.1.113:9003";
//    public static final String BASEURL = "http://www.hengzhankj.com";
    public static final String LOGINURL = BASEURL + "/api/Account/Login";
    public static final String QUERYFATDATAURL = BASEURL + "/api/DeviceData/QueryFatDataV1?IdentityUserIdAssign=UserId";//体脂
    public static final String QUERYBGDATA = BASEURL + "/api/DeviceData/QueryBGDataV1?IdentityUserIdAssign=UserId";//血糖
    public static final String  QUERYOXYGENDATAURL=BASEURL+"/api/DeviceData/QuerySpo2DataV1?IdentityUserIdAssign=UserId";//血氧
    public static final String  QUERYWEIGHTDATAURL=BASEURL+"/api/DeviceData/QueryWeightDataV1?IdentityUserIdAssign=UserId";//体重
    public static final String QUERYBLOODPRESSURE=BASEURL+"/api/DeviceData/QueryBloodPressureV1?IdentityUserIdAssign=UserId";//血压
    public static final String QUERYUR=BASEURL+ "/api/DeviceData/QueryURDataV1?IdentityUserIdAssign=UserId";//尿常规
    public static final String QUERYBLOODFAT=BASEURL+"/api/DeviceData/QueryBD_FATDataV1?IdentityUserIdAssign=UserId";//血脂
    public static final String QUREYECG=BASEURL+"/api/DeviceData/QueryEcgStructV1?IdentityUserIdAssign=UserId";//心电
    public static final String QUREYECGPIC=BASEURL+"/api/DeviceData/QueryEcgPictureV1?IdentityUserIdAssign=UserId&IsGetOne=true&SpecifyProperty=Picturedata";//心电图片
    public static final String BEGVCODE=BASEURL+"/api/Account/SendMobileVerifyCode";
    public static final String FINDPWD=BASEURL+"/api/Account/ResetPassword";
    public static final String REGISTERURL=BASEURL+"/api/Account/Register";
    public static final String ACCOUNTINFO=BASEURL+"/api/Account/QueryUser?IdentityUserIdAssign=Id&IsGetOne=true";
    public static final String SAVAUSERINFO=BASEURL+"/api/Account/SaveUser";
    public static final String SHAREURL=BASEURL+"/content/mobile/download.html";
    public static final String PROTOCLURL=BASEURL+"/content/mobile/useragreement.html";
    public static final String USERHELPURL=BASEURL+"/content/mobile/helper.html";
    public static final String FEEDBACKURL=BASEURL+"/api/Account/SaveOpinion";
    public static final String QUITURL=BASEURL+"/api/Account/LogOff";
    public static final String QUERYDOCTORINFO=BASEURL+"/api/Health/QueryExpert";
}
