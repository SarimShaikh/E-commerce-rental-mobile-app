package com.ecommerce.app.model;

import java.io.Serializable;

public class ProductDetails implements Serializable {
    private Long itemId;
    private Long itemDetailId;
    private String itemSize;
    private Long itemPrice;
    private Long fineAmount;
    private Long rentalDays;

    ProductDetails() {
    }

    public ProductDetails(Long itemId, Long itemDetailId, String itemSize, Long itemPrice, Long fineAmount, Long rentalDays) {
        this.itemId = itemId;
        this.itemDetailId = itemDetailId;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.fineAmount = fineAmount;
        this.rentalDays = rentalDays;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getItemDetailId() {
        return itemDetailId;
    }

    public void setItemDetailId(Long itemDetailId) {
        this.itemDetailId = itemDetailId;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Long fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Long rentalDays) {
        this.rentalDays = rentalDays;
    }
}
