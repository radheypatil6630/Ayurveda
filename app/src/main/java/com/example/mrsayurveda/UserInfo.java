//package com.example.mrsayurveda;
//
//import android.util.Log;
//
//public class UserInfo {
//    public String userId,firstName,lastName, phoneNumber, address;
//    public String gender="",city="",state="",nationality="",passportNo="",issuingCountry="",panCard="",selectedCountry="";
//    String panExpiry="",dob="";
//
//    public UserInfo() {
//        //Default Constructor
//    }
//    public UserInfo(String email, String firstName, String lastName, String phoneNumber, String userId , String selectedCountry) {
//        Log.d("UserInfo", "UserInfo: " + userId + " " + firstName + " " + lastName + " " + phoneNumber + " " + address);
//        this.address = address;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.userId = userId;
//        this.selectedCountry = selectedCountry;
//    }
//
//    public UserInfo(String userId, String firstName, String phoneNumber, String address,
//                    String gender, String city, String state, String nationality,
//                    String issuingCountry, String dob, String selectedCountry) {
//        // Your existing constructor code...
//        this.userId=userId;
//        this.firstName = firstName;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//        this.gender = gender;
//        this.city = city;
//        this.state = state;
//        this.nationality = nationality;
//
//        this.issuingCountry = issuingCountry;
//        this.dob=dob;
//        this.selectedCountry=selectedCountry;
//    }
//
//    public String getSelectedCountry(){
//        return selectedCountry;
//    }
//
//    public void setSelectedCountry(String selectedCountry) {
//        this.selectedCountry = selectedCountry;
//    }
//
//
//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getNationality() {
//        return nationality;
//    }
//
//    public void setNationality(String nationality) {
//        this.nationality = nationality;
//    }
//
//
//    public String getIssuingCountry() {
//        return issuingCountry;
//    }
//
//    public void setIssuingCountry(String issuingCountry) {
//        this.issuingCountry = issuingCountry;
//    }
//
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//}
