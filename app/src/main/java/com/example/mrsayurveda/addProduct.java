package com.example.mrsayurveda;

import com.google.firebase.database.PropertyName;

public class addProduct {

    private String imageUrl;
    private String price;
    private String ProductName;
    private String description;

    private String category;
    private String subCategory;

    public addProduct() {
    }

    public addProduct(String imageUrl, String ProductName, String price, String description) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.ProductName = ProductName;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @PropertyName("ProductName")
    public String getProductName() {
        return ProductName;
    }

    @PropertyName("ProductName")
    public void setProductName(String ProductName) {
        ProductName = ProductName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}