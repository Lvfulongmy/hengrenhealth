package com.hengrunjiankang.health.entity;

/**
 * Created by Administrator on 2017/8/22.
 */

public class TagEntity {
    private String Id;
    private String ExpertId;
    private String Tag;
    private String TagCount;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getExpertId() {
        return ExpertId;
    }

    public void setExpertId(String expertId) {
        ExpertId = expertId;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getTagCount() {
        return TagCount;
    }

    public void setTagCount(String tagCount) {
        TagCount = tagCount;
    }
}
