package com.ecommerce.app.model;

import java.io.Serializable;

public class CartItems implements Serializable {
    private String productName;
    private Long itemId;
    private Long itemDetailId;
    private Long quantity;
    private Long price;
    private String size;
    private String fromDate;
    private String toDate;
    private boolean checkQuantity;

    public CartItems(String productName, Long itemId, Long itemDetailId, Long quantity, Long price, String size, String fromDate, String toDate) {
        this.productName = productName;
        this.itemId = itemId;
        this.itemDetailId = itemDetailId;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getProductName() {
        return productName;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getItemDetailId() {
        return itemDetailId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

}
