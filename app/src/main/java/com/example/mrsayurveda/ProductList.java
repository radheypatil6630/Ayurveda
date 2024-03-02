package com.example.mrsayurveda;

public class ProductList {
    private String imageUrl;
    private String price;
    private String ProductName;

    public ProductList() {
        // Required default constructor for Firebase
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice(){return price ;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }
}
