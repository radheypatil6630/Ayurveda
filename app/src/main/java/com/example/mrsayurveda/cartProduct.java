package com.example.mrsayurveda;

public class cartProduct {
    private String orderId; // Add orderId field
    private String productName;
    private String imageUrl;
    private String productPrice;
    private String deliveryDate;
    private int quantityNum;

    private boolean checkbox_status;

    public cartProduct() {
        // Default constructor required for Firebase
    }



    public cartProduct(String productName, String imageUrl, String productPrice, String deliveryDate, String orderId) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.productPrice = productPrice;
        this.deliveryDate = deliveryDate; // Set delivery date
        this.orderId=orderId;

    }


    public cartProduct(String productName, String imageUrl, String price, String deliveryDate) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.productPrice = price;
        this.deliveryDate = deliveryDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQuantityNum() {
        return quantityNum;
    }

    public void setQuantityNum(int quantityNum) {
        this.quantityNum = quantityNum;
    }

    public boolean isCheckbox_status() {
        return checkbox_status;
    }

    public void setCheckbox_status(boolean checkbox_status) {
        this.checkbox_status = checkbox_status;
    }
}
