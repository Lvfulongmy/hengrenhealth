package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/19.
 */

public class BloodFatEntity extends RecordEntity {
    private double CHOL;//表示胆固醇含量，string类型，值为浮点数据，单位为mmol/L。
    private double  HDLCHOL;//表示高密度脂蛋白胆固醇含量，string类型，值为浮点数据，单位为mmol/L。
    private double  TRIG;//表示甘油三酯含量，string类型，值为浮点数据，单位为mmol/L。
    private double CALCLDL;//表示低密度脂蛋白胆固醇含量，string类型，值为浮点数据，单位为mmol/L。
    private double TC_HDL;//表示总胆固醇和高密度胆固醇的比值，string类型，值为浮点数据。

    public double getCHOL() {
        return CHOL;
    }

    public void setCHOL(double CHOL) {
        this.CHOL = CHOL;
    }

    public double getHDLCHOL() {
        return HDLCHOL;
    }

    public void setHDLCHOL(double HDLCHOL) {
        this.HDLCHOL = HDLCHOL;
    }

    public double getTRIG() {
        return TRIG;
    }

    public void setTRIG(double TRIG) {
        this.TRIG = TRIG;
    }

    public double getCALCLDL() {
        return CALCLDL;
    }

    public void setCALCLDL(double CALCLDL) {
        this.CALCLDL = CALCLDL;
    }

    public double getTC_HDL() {
        return TC_HDL;
    }

    public void setTC_HDL(double TC_HDL) {
        this.TC_HDL = TC_HDL;
    }
}
