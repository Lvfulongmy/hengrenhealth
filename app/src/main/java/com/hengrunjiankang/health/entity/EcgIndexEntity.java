package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/20.
 */

public class EcgIndexEntity {
    private String name;
    private String index;


    public EcgIndexEntity(String name, String index) {
        this.name = name;
        this.index = index;
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
}
