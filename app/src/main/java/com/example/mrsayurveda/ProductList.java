package com.example.mrsayurveda;

public class ProductList {
    private String imageUrl;
    private String price;
    private String ProductName;

    public ProductList() {
        // Required default constructor for Firebase
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice(){return price != null ? price : "";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return ProductName != null ? ProductName : "";
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }
}
