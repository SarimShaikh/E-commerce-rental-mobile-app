package com.ecommerce.app.model;

import java.io.Serializable;

public class Categories implements Serializable {

    private Long categoryId;
    private String categoryType;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
