package com.ecommerce.app.model;

import java.io.Serializable;

public class Stores implements Serializable {

    private Long storeId;
    private Long userId;
    private String storeName;
    private String storeRegistrationNumber;
    private String storeContact;
    private String storeAddress;
    private String imageUri;

    public Stores() {
    }

    public Stores(Long storeId, Long userId, String storeName, String storeRegistrationNumber, String storeContact, String storeAddress, String imageUri) {
        this.storeId = storeId;
        this.userId = userId;
        this.storeName = storeName;
        this.storeRegistrationNumber = storeRegistrationNumber;
        this.storeContact = storeContact;
        this.storeAddress = storeAddress;
        this.imageUri = imageUri;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreRegistrationNumber() {
        return storeRegistrationNumber;
    }

    public void setStoreRegistrationNumber(String storeRegistrationNumber) {
        this.storeRegistrationNumber = storeRegistrationNumber;
    }

    public String getStoreContact() {
        return storeContact;
    }

    public void setStoreContact(String storeContact) {
        this.storeContact = storeContact;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
