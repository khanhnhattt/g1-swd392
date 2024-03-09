package com.shashi.beans;

import java.io.Serializable;

public class CategoryBean implements Serializable {
    public CategoryBean(){};

    private int cateId;
    private String cateName;

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public CategoryBean(int cateId, String cateName) {
        super();
        this.cateId = cateId;
        this.cateName = cateName;
    }
}
