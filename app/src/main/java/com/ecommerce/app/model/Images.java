package com.ecommerce.app.model;

import java.io.Serializable;

public class Images implements Serializable {

    private Long imageId;
    private String imagePath;

    public Images() {
    }

    public Images(Long imageId, String imagePath) {
        this.imageId = imageId;
        this.imagePath = imagePath;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
