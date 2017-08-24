package com.hengrunjiankang.health.entity;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ProfileEntity extends ProfileListEntity {
    private int Sequence;
    private String UserId;
    private String RecordTime;
    private String Dalx_Yyjc;
    private String Dalx_Bl;
    private String Dalx_Yyfa;
    private boolean Jczz_SfFlkg;
    private boolean Jczz_SfDydssjdn;
    private boolean Jczz_SfXs;
    private boolean Jczz_SfFzxh;
    private boolean Jczz_SfZhdh;
    private boolean Wxh_SfSlmhxj;
    private boolean Wxh_SfSzflr;
    private boolean Wxh_SfTnbsb;
    private boolean Wxh_SfTnbz;
    private boolean Sjbb_SfSzmm;
    private boolean Sjbb_SfPfsr;
    private boolean Sjbb_SfBmfx;
    private String Remark;

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

    public String getDalx_Yyjc() {
        return Dalx_Yyjc;
    }

    public void setDalx_Yyjc(String dalx_Yyjc) {
        Dalx_Yyjc = dalx_Yyjc;
    }

    public String getDalx_Bl() {
        return Dalx_Bl;
    }

    public void setDalx_Bl(String dalx_Bl) {
        Dalx_Bl = dalx_Bl;
    }

    public String getDalx_Yyfa() {
        return Dalx_Yyfa;
    }

    public void setDalx_Yyfa(String dalx_Yyfa) {
        Dalx_Yyfa = dalx_Yyfa;
    }

    public boolean isJczz_SfFlkg() {
        return Jczz_SfFlkg;
    }

    public void setJczz_SfFlkg(boolean jczz_SfFlkg) {
        Jczz_SfFlkg = jczz_SfFlkg;
    }

    public boolean isJczz_SfDydssjdn() {
        return Jczz_SfDydssjdn;
    }

    public void setJczz_SfDydssjdn(boolean jczz_SfDydssjdn) {
        Jczz_SfDydssjdn = jczz_SfDydssjdn;
    }

    public boolean isJczz_SfXs() {
        return Jczz_SfXs;
    }

    public void setJczz_SfXs(boolean jczz_SfXs) {
        Jczz_SfXs = jczz_SfXs;
    }

    public boolean isJczz_SfFzxh() {
        return Jczz_SfFzxh;
    }

    public void setJczz_SfFzxh(boolean jczz_SfFzxh) {
        Jczz_SfFzxh = jczz_SfFzxh;
    }

    public boolean isJczz_SfZhdh() {
        return Jczz_SfZhdh;
    }

    public void setJczz_SfZhdh(boolean jczz_SfZhdh) {
        Jczz_SfZhdh = jczz_SfZhdh;
    }

    public boolean isWxh_SfSlmhxj() {
        return Wxh_SfSlmhxj;
    }

    public void setWxh_SfSlmhxj(boolean wxh_SfSlmhxj) {
        Wxh_SfSlmhxj = wxh_SfSlmhxj;
    }

    public boolean isWxh_SfSzflr() {
        return Wxh_SfSzflr;
    }

    public void setWxh_SfSzflr(boolean wxh_SfSzflr) {
        Wxh_SfSzflr = wxh_SfSzflr;
    }

    public boolean isWxh_SfTnbsb() {
        return Wxh_SfTnbsb;
    }

    public void setWxh_SfTnbsb(boolean wxh_SfTnbsb) {
        Wxh_SfTnbsb = wxh_SfTnbsb;
    }

    public boolean isWxh_SfTnbz() {
        return Wxh_SfTnbz;
    }

    public void setWxh_SfTnbz(boolean wxh_SfTnbz) {
        Wxh_SfTnbz = wxh_SfTnbz;
    }

    public boolean isSjbb_SfSzmm() {
        return Sjbb_SfSzmm;
    }

    public void setSjbb_SfSzmm(boolean sjbb_SfSzmm) {
        Sjbb_SfSzmm = sjbb_SfSzmm;
    }

    public boolean isSjbb_SfPfsr() {
        return Sjbb_SfPfsr;
    }

    public void setSjbb_SfPfsr(boolean sjbb_SfPfsr) {
        Sjbb_SfPfsr = sjbb_SfPfsr;
    }

    public boolean isSjbb_SfBmfx() {
        return Sjbb_SfBmfx;
    }

    public void setSjbb_SfBmfx(boolean sjbb_SfBmfx) {
        Sjbb_SfBmfx = sjbb_SfBmfx;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
    private HashMap<String,String > mapString;
    public ProfileEntity(){
        mapString=new HashMap<>();
        mapString.put("Jczz_SfFlkg", "乏力、口干（苦、异味）");
        mapString.put("Jczz_SfDydssjdn", "多饮、多食、善饥、多尿");
        mapString.put("Jczz_SfXs", "消瘦");
        mapString.put("Jczz_SfFzxh", "烦躁心慌");
        mapString.put("Jczz_SfZhdh", "自汗盗汗");
        mapString.put("Wxh_SfSlmhxj", "视力模糊、下降  （眼底、视网膜、白内障）");
        mapString.put("Wxh_SfSzflr", "手足发凉/热");
        mapString.put("Wxh_SfTnbsb", " 糖尿病肾病");
        mapString.put("Wxh_SfTnbz", "糖尿病足");
        mapString.put("Sjbb_SfSzmm", "手足麻木、疼痛（针刺感）无知觉、踩异物感、手套、袜套感");
        mapString.put("Sjbb_SfPfsr", "皮肤瘙痒、蚁爬感");
        mapString.put("Sjbb_SfBmfx", "便秘、腹泻、便秘腹泻交替");
    }
    
    public String getIllStr(){
        String strillness="";
        if(isJczz_SfFlkg()){
            strillness=strillness+mapString.get("Jczz_SfFlkg")+",";
        }
        if(isJczz_SfDydssjdn()){
            strillness=strillness+mapString.get("Jczz_SfDydssjdn")+",";
        }
        if(isJczz_SfXs()){
            strillness=strillness+mapString.get("Jczz_SfXs")+",";
        }
        if(isJczz_SfFzxh()){
            strillness=strillness+mapString.get("Jczz_SfFzxh")+",";
        }
        if(isJczz_SfZhdh()){
            strillness=strillness+mapString.get("Jczz_SfZhdh")+",";
        }

        if(isWxh_SfSlmhxj()){
            strillness=strillness+mapString.get("Wxh_SfSlmhxj")+",";
        }
        if(isWxh_SfSzflr()){
            strillness=strillness+mapString.get("Wxh_SfSzflr")+",";
        }
        if(isWxh_SfTnbsb()){
            strillness=strillness+mapString.get("Wxh_SfTnbsb")+",";
        }
        if(isWxh_SfTnbz()){
            strillness=strillness+mapString.get("Wxh_SfTnbz")+",";
        }

        if(isSjbb_SfSzmm()){
            strillness=strillness+mapString.get("Sjbb_SfSzmm")+",";
        }
        if(isSjbb_SfPfsr()){
            strillness=strillness+mapString.get("Sjbb_SfPfsr")+",";
        }
        if(isSjbb_SfBmfx()){
            strillness=strillness+mapString.get("Sjbb_SfBmfx")+",";
        }
        return strillness.length()>0? strillness.substring(0,strillness.length()-1):"";
    }
}