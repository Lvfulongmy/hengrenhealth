package com.hengrunjiankang.health.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/14.
 */

public class YTextData {
    private ArrayList<String> ytextlist;
    private int ystep;
    private int yTextStep;

    public int getyTextStep() {
        return yTextStep;
    }

    public void setyTextStep(int yTextStep) {
        this.yTextStep = yTextStep;
    }

    public int getYstep() {
        return ystep;
    }

    public void setYstep(int ystep) {
        this.ystep = ystep;
    }

    public ArrayList<String> getYtextlist() {
        return ytextlist;
    }

    public void setYtextlist(ArrayList<String> ytextlist) {
        this.ytextlist = ytextlist;
    }
}
