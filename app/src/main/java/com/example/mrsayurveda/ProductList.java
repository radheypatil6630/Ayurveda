package com.example.mrsayurveda;

public class ProductList {
    private String imageUrl;
    private String price;
    private String ProductName;
    private String description;
    private String productType;
  //  private String deliveryDate;

    // ... (existing getters and setters)


    public ProductList(String ProductName, String imageUrl, String price) {
        this.ProductName = ProductName;
        this.imageUrl = imageUrl;
        this.price = price;
    }
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

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getProductType() { return productType;    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


//    public String getDeliveryDate() {
//        return deliveryDate;
//    }
//
//    public void setDeliveryDate(String deliveryDate) {
//        this.deliveryDate = deliveryDate;
//    }
}
