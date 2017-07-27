package com.hengrunjiankang.health.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/18.
 */

public class RecordEntity implements Serializable {
    protected String Collectdate;

    public String getCollectdate() {
        return Collectdate;
    }

    public void setCollectdate(String collectdate) {
        Collectdate = collectdate;
    }
}
