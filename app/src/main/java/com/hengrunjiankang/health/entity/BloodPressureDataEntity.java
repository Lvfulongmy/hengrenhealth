package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/18.
 */

public class BloodPressureDataEntity extends RecordEntity {
    private int diastolicpressure;//舒张压
    private int systolicpressure;//收缩压

    public int getDiastolicpressure() {
        return diastolicpressure;
    }

    public void setDiastolicpressure(int diastolicpressure) {
        this.diastolicpressure = diastolicpressure;
    }

    public int getSystolicpressure() {
        return systolicpressure;
    }

    public void setSystolicpressure(int systolicpressure) {
        this.systolicpressure = systolicpressure;
    }
}
