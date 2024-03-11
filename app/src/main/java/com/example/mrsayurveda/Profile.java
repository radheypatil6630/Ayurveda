package com.example.mrsayurveda;

public class Profile {
    private String imageUrl;
    private String displayName; // Assuming this is the user's display name

    // Required default constructor for Firebase
    public Profile() {
    }

    public Profile(String imageUrl, String displayName) {
        this.imageUrl = imageUrl;
        this.displayName = displayName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
