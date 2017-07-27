package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/7/20.
 */

public class URIndexEntity {
    private String name;
    private String index;
    private String scope;

    public URIndexEntity(String name, String index, String scope) {
        this.name = name;
        this.index = index;
        this.scope = scope;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
