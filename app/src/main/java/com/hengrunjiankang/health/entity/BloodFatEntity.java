package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/19.
 */

public class BloodFatEntity extends RecordEntity {
    private String CHOL;//表示胆固醇含量，string类型，值为浮点数据，单位为mmol/L。
    private String  HDLCHOL;//表示高密度脂蛋白胆固醇含量，string类型，值为浮点数据，单位为mmol/L。
    private String  TRIG;//表示甘油三酯含量，string类型，值为浮点数据，单位为mmol/L。
    private String CALCLDL;//表示低密度脂蛋白胆固醇含量，string类型，值为浮点数据，单位为mmol/L。
    private String TC_HDL;//表示总胆固醇和高密度胆固醇的比值，string类型，值为浮点数据。

    public String getCHOL() {
        return CHOL;
    }

    public void setCHOL(String CHOL) {
        this.CHOL = CHOL;
    }

    public String getHDLCHOL() {
        return HDLCHOL;
    }

    public void setHDLCHOL(String HDLCHOL) {
        this.HDLCHOL = HDLCHOL;
    }

    public String getTRIG() {
        return TRIG;
    }

    public void setTRIG(String TRIG) {
        this.TRIG = TRIG;
    }

    public String getCALCLDL() {
        return CALCLDL;
    }

    public void setCALCLDL(String CALCLDL) {
        this.CALCLDL = CALCLDL;
    }

    public String getTC_HDL() {
        return TC_HDL;
    }

    public void setTC_HDL(String TC_HDL) {
        this.TC_HDL = TC_HDL;
    }
}
