package com.example.mrsayurveda;
public class OrderedProduct {
    private String productName;
    private String imageUrl;
    private String productPrice;

    public OrderedProduct() {
        // Default constructor required for Firebase
    }

    public OrderedProduct(String productName, String imageUrl, String productPrice) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.productPrice = productPrice;
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
}
