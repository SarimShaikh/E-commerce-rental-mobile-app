package com.ecommerce.app.model;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {

    private Long itemId;
    private Long storeId;
    private Long categoryId;
    private String itemName;
    private List<Images> imagesList;
    private List<ProductDetails> productDetailsList;

    public Products() {
    }

    public Products(Long itemId, Long storeId, Long categoryId, String itemName, List<Images> imagesList, List<ProductDetails> productDetailsList) {
        this.itemId = itemId;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.imagesList = imagesList;
        this.productDetailsList = productDetailsList;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    public List<ProductDetails> getProductDetailsList() {
        return productDetailsList;
    }

    public void setProductDetailsList(List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }
}
