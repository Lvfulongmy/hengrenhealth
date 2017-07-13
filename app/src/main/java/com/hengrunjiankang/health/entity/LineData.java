package com.hengrunjiankang.health.entity;

import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/12.
 */

public class LineData {
    private ArrayList<PointData> date;
    private int color;

    public ArrayList<PointData> getDate() {
        return date;
    }

    public void setDate(ArrayList<PointData> date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
