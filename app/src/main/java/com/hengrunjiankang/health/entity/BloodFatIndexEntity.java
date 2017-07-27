package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/20.
 */

public class BloodFatIndexEntity {
    private String name;
    private String index;
    private String index_green;
    private String index_orange;
    private String index_red;

    public BloodFatIndexEntity(String name, String index, String index_green, String index_orange, String index_red) {
        this.name = name;
        this.index = index;
        this.index_green = index_green;
        this.index_orange = index_orange;
        this.index_red = index_red;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex_green() {
        return index_green;
    }

    public void setIndex_green(String index_green) {
        this.index_green = index_green;
    }

    public String getIndex_orange() {
        return index_orange;
    }

    public void setIndex_orange(String index_orange) {
        this.index_orange = index_orange;
    }

    public String getIndex_red() {
        return index_red;
    }

    public void setIndex_red(String index_red) {
        this.index_red = index_red;
    }
}
