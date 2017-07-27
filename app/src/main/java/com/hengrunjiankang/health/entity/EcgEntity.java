package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/21.
 */

public class EcgEntity extends RecordEntity {
    private String Id;
    private String Stisnormal;//表示这段数据的ST段是否正常，string类型，值包括ST段正常， ST段抬高， ST段压低三个状态。
    private String  isarrhythmia;//表示心律是否失常，string类型，值包括没有发现心律失常，心律失常两个状态。
    private String  Waveform;//表示波形质量是否正常，string类型，值包括波形质量正常，波形质量过差两个状态。
    private String  Heartrate;//表示心率是否正常，string类型，值包括心率正常，心率过慢，心率稍快，心率过快，导连脱落五个状态。
    private String  Wholewave;//表示整体波形是否正常，string类型，值包括整体波形正常和整体波形异常两个状态。
    private String  Howmanyrwave;//这个数组表中有多少个R波被检测到，从1开始计数，int类型，表示32位有符号整数。
    private String  Howmanysuccess;//表示多少个R波形成功分析，从1开始计数，int类型，示32位有符号整数。
    private String  avr_heartrate;//此次心电记录的心率信息，Float类型，表示单精度浮点的数值。
    private String  avr_pr;//此次检测全局的pR间期，int类型，表示32位有符号整数。
    private String  avr_qt;//此次检测的平均QT间期，int类型，表示32位有符号整数。
    private String  avr_rvolt;//此次检测的平均R波电压，Float类型，表示单精度浮点的数值。
    private String  avr_pvolt;//此次检测的平均p波电压，Float类型，表示单精度浮点的数值。
    private String  avr_tvolt;//此次检测的平均t波 电压，Float类型，表示单精度浮点的数值。
    private String  avr_stvolt;//ST段平均电压，Float类型，表示单精度浮点的数值。
    private String  avr_rr;//此次检测全局rr间期，Float类型，表示单精度浮点的数值。
    private String  avr_qtc;//此次检测全局qtc间期，Float类型，表示单精度浮点的数值。

    public String getStisnormal() {
        return Stisnormal;
    }

    public void setStisnormal(String stisnormal) {
        Stisnormal = stisnormal;
    }

    public String getIsarrhythmia() {
        return isarrhythmia;
    }

    public void setIsarrhythmia(String isarrhythmia) {
        this.isarrhythmia = isarrhythmia;
    }

    public String getWaveform() {
        return Waveform;
    }

    public void setWaveform(String waveform) {
        Waveform = waveform;
    }

    public String getHeartrate() {
        return Heartrate;
    }

    public void setHeartrate(String heartrate) {
        Heartrate = heartrate;
    }

    public String getWholewave() {
        return Wholewave;
    }

    public void setWholewave(String wholewave) {
        Wholewave = wholewave;
    }

    public String getHowmanyrwave() {
        return Howmanyrwave;
    }

    public void setHowmanyrwave(String howmanyrwave) {
        Howmanyrwave = howmanyrwave;
    }

    public String getHowmanysuccess() {
        return Howmanysuccess;
    }

    public void setHowmanysuccess(String howmanysuccess) {
        Howmanysuccess = howmanysuccess;
    }

    public String getAvr_heartrate() {
        return avr_heartrate;
    }

    public void setAvr_heartrate(String avr_heartrate) {
        this.avr_heartrate = avr_heartrate;
    }

    public String getAvr_pr() {
        return avr_pr;
    }

    public void setAvr_pr(String avr_pr) {
        this.avr_pr = avr_pr;
    }

    public String getAvr_qt() {
        return avr_qt;
    }

    public void setAvr_qt(String avr_qt) {
        this.avr_qt = avr_qt;
    }

    public String getAvr_rvolt() {
        return avr_rvolt;
    }

    public void setAvr_rvolt(String avr_rvolt) {
        this.avr_rvolt = avr_rvolt;
    }

    public String getAvr_pvolt() {
        return avr_pvolt;
    }

    public void setAvr_pvolt(String avr_pvolt) {
        this.avr_pvolt = avr_pvolt;
    }

    public String getAvr_tvolt() {
        return avr_tvolt;
    }

    public void setAvr_tvolt(String avr_tvolt) {
        this.avr_tvolt = avr_tvolt;
    }

    public String getAvr_stvolt() {
        return avr_stvolt;
    }

    public void setAvr_stvolt(String avr_stvolt) {
        this.avr_stvolt = avr_stvolt;
    }

    public String getAvr_rr() {
        return avr_rr;
    }

    public void setAvr_rr(String avr_rr) {
        this.avr_rr = avr_rr;
    }

    public String getAvr_qtc() {
        return avr_qtc;
    }

    public void setAvr_qtc(String avr_qtc) {
        this.avr_qtc = avr_qtc;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}


