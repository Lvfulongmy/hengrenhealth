package com.hengrunjiankang.health.okhttp;

/**
 * Created by Administrator on 2017/7/12.
 */

public class UrlObject {
    public static final String BASEURL = "http://192.168.1.113:9000";
    public static final String LOGINURL = BASEURL + "/api/Account/Login";
    public static final String QUERYFATDATAURL = BASEURL + "/api/DeviceData/QueryFatDataV1?IdentityUserIdAssign=UserId";//体脂
    public static final String QUERYBGDATA = BASEURL + "/api/DeviceData/QueryBGDataV1?IdentityUserIdAssign=UserId";//血糖
    public static final String  QUERYOXYGENDATAURL=BASEURL+"/api/DeviceData/QuerySpo2DataV1?IdentityUserIdAssign=UserId";//血氧
    public static final String  QUERYWEIGHTDATAURL=BASEURL+"/api/DeviceData/QueryWeightDataV1?IdentityUserIdAssign=UserId";//体重
    public static final String QUERYBLOODPRESSURE=BASEURL+"/api/DeviceData/QueryBloodPressureV1?IdentityUserIdAssign=UserId";//血压
}
